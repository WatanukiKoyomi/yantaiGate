package com.huadong.hdgate.common.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huadong.hdgate.common.utils.*;
import com.huadong.hdgate.common.utils.webapi.HttpsUtils;
import com.huadong.hdgate.jobManagement.entity.cdiEntity.*;
import com.huadong.hdgate.jobManagement.entity.xijingEntity.DeviceEntity;
import com.huadong.hdgate.jobManagement.service.BusinessService;
import com.huadong.hdgate.laneManagement.entity.EquipmentStatusEntity;
import com.huadong.hdgate.laneManagement.entity.GateLane;
import com.huadong.hdgate.laneManagement.service.GateLaneService;
import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时器
 */
@EnableScheduling //开启定时器功能
@Order(value = 3)
@Component
@Slf4j
@EnableAsync
public class TimeScheduleEntity {
	@Autowired
	private RedisUtils redisUtils;
	@Resource
	private LaneDBUtils laneDBUtils;
	@Resource(name="template")
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private BusinessService businessService;
	@Resource
	private GateLaneService laneService;

	@Scheduled(cron = "0 0 1 * * ?") //每天凌晨1点 0 0 1 * * ? 执行一次，处理缓存 // 每5秒一次 */5 * * * * ?
	public void sendMessage(){
		Object userObj = MyCacheUtils.get("users");
		Map<String,SysUserEntity> finalMap = new HashMap<>();
		if(userObj!=null){
			Map<String,SysUserEntity> userMap = (Map) userObj;
			for (String key : userMap.keySet()) {
				SysUserEntity value = userMap.get(key);
				long oldTimeLong = value.getAccountExpirationDate().getTime();
				long curTimeLong = new Date().getTime();
				long days = (curTimeLong - oldTimeLong)/ (1000 * 60 * 60 * 24);
				if(days < 3){ // 没有超过3天,重新添加到map中，循环完之后，覆盖原有缓存数据
					finalMap.put(key,value);
				}
			}
			log.info("定时缓存处理：users:"+finalMap.toString());
			MyCacheUtils.put("users",finalMap,0);// 覆盖原有缓存数据
		}
	}

	@Scheduled(fixedDelayString="1000")
	public void deviceTask(){
		for(int i : laneDBUtils.getAllLaneDB()) {
			String statusEntity = redisUtils.rpopQueue("device_data", i);
			String laneCode = laneDBUtils.getLaneCode(i);
			if (statusEntity != null && !statusEntity.equals("null") && !statusEntity.equals("")) {
				List<DeviceEntity> list = JSONArray.parseArray(statusEntity,DeviceEntity.class);
				EquipmentStatusEntity equipmentStatusEntity = new EquipmentStatusEntity(list, laneCode);
				String equipmentStatus = JSONObject.toJSONString(equipmentStatusEntity);
				redisUtils.set("receiveStatus"+laneCode,equipmentStatus);
				laneService.sendData2Html(laneCode,equipmentStatus);
				if(equipmentStatus.contains("0")){
					String url = "http://localhost:8085/hdGate/sys/showErrorMsg";
					String retMsg = HttpsUtils.doPost(url, laneCode+"有设备异常，请及时处理", "utf-8");
					log.info("调用api：" + url + "，返回值：" + retMsg);
				}
			}
		}
	}

	/**
	 * 接收识别数据
	 */
	@Async
	@Scheduled(cron = "*/2 * * * * ?")
	public void getOcrTask() {
		for(int i:laneDBUtils.getAllLaneDB()){
			String autoGateBusiness = redisUtils.rpopQueue("web_data",i);
			if(autoGateBusiness!=null && !autoGateBusiness.equals("null") && !autoGateBusiness.equals("")){
				Map<String,Object> maps = (Map) JSON.parse(autoGateBusiness);
				if(maps.get("station").toString().equals("web")){
					businessService.updateMessage(maps.get("uuid").toString(),maps.get("message").toString().replaceAll("\\r\\n",","));
					String str = "{\"update\": \"1\",\"laneCode\": \"" + maps.get("lanecode").toString() + "\",\"message\": \"" + maps.get("message").toString().replaceAll("\\r\\n",",") + "\"}";
					businessService.sendData2Html(maps.get("lanecode").toString(),str);
					continue;
				}
				BusinessEntity businessEntity = packageOcrData(maps);
				log.info("businessEntity:"+JSONObject.toJSONString(businessEntity));
				autoGateBusiness = CommonUtils.cdiEntity2ShowEntityStr(businessEntity);
				log.info("autoGate:"+autoGateBusiness);
				stringRedisTemplate.convertAndSend("hd_gate_business_data_db",autoGateBusiness);// redis频道
				businessService.sendData2Html(businessEntity.getGeneralInfo().getLaneCode(),autoGateBusiness); // 推送数据到页面
			}
		}
	}

	/**
	 * 识别数据对应封装
	 * @param maps
	 * @return
	 */
	public BusinessEntity packageOcrData(Map<String,Object> maps){
		BusinessEntity businessEntity = new BusinessEntity();
		businessEntity.setVisitGuid(maps.get("uuid").toString());
		businessEntity.setStation(maps.get("station").toString());
		businessEntity.setArriveTime(maps.get("starttime").toString());
		businessEntity.setEnterTime(maps.get("endtime").toString());
		businessEntity.setMsg(maps.get("message").toString().replaceAll("\\r\\n",","));

		GeneralInfoEntity generalInfoEntity = new GeneralInfoEntity();
		generalInfoEntity.setWeight(maps.get("weight").toString());
		generalInfoEntity.setLaneCode(maps.get("lanecode").toString());//车道号与redis和定时任务对应
		generalInfoEntity.setCntrSize(maps.get("containersize").toString());
		businessEntity.setGeneralInfo(generalInfoEntity);

		CarPlateEntity carPlateEntity = new CarPlateEntity();
		carPlateEntity.setOcrPlate(((Map) maps.get("lorry")).get("plate").toString());
		carPlateEntity.setPlateColor(((Map) maps.get("lorry")).get("color").toString());
		businessEntity.setOcrCarPlate(carPlateEntity);

		ContainerEntity ocrFrontContainer = new ContainerEntity();
		ocrFrontContainer.setOcrContainerNo(((Map) maps.get("containerahead")).get("number").toString());
		ocrFrontContainer.setOcrContainerISO(((Map) maps.get("containerahead")).get("iso").toString());
		ocrFrontContainer.setOcrContainerDirection(((Map) maps.get("containerahead")).get("direction").toString());
		ocrFrontContainer.setLeadSealNo(((Map) maps.get("containerahead")).get("leadsealno").toString());
		ocrFrontContainer.setProperty(((Map) maps.get("containerahead")).get("property").toString());
		ocrFrontContainer.setLeadSealState(((Map) maps.get("containerahead")).get("leadsealstate").toString());
		ocrFrontContainer.setEfid(((Map) maps.get("containerahead")).get("efid").toString());
		ocrFrontContainer.setOcrDamage(((Map) maps.get("containerahead")).get("damage_code").toString());
		businessEntity.setOcrFrontContainer(ocrFrontContainer);

		ContainerEntity ocrAfterContainer = new ContainerEntity();
		ocrAfterContainer.setOcrContainerNo(((Map) maps.get("containerbehind")).get("number").toString());
		ocrAfterContainer.setOcrContainerISO(((Map) maps.get("containerbehind")).get("iso").toString());
		ocrAfterContainer.setOcrContainerDirection(((Map) maps.get("containerbehind")).get("direction").toString());
		ocrAfterContainer.setLeadSealNo(((Map) maps.get("containerbehind")).get("leadsealno").toString());
		ocrAfterContainer.setProperty(((Map) maps.get("containerbehind")).get("property").toString());
		ocrAfterContainer.setLeadSealState(((Map) maps.get("containerbehind")).get("leadsealstate").toString());
		ocrAfterContainer.setEfid(((Map) maps.get("containerbehind")).get("efid").toString());
		ocrAfterContainer.setOcrDamage(((Map) maps.get("containerbehind")).get("damage_code").toString());
		businessEntity.setOcrAfterContainer(ocrAfterContainer);

		FtpImagesEntity ftpImagesEntity = new FtpImagesEntity();
		StringBuffer images = new StringBuffer();
		Map<String, Object> imageList =(Map) maps.get("images");
		for(String key: imageList.keySet()){
			images.append(imageList.get(key)).append(",");
		}
		images.deleteCharAt(images.length()-1);
		ftpImagesEntity.setImageName(images.toString());
		businessEntity.setFtpImages(ftpImagesEntity);
		return businessEntity;
	}

}
