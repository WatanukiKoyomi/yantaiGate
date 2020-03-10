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

	/**
	 * 每3秒一次心跳检测设备
	 */
	//@Async
	//@Scheduled(cron = "*/3 * * * * ?") //每天凌晨1点 0 0 1 * * ? 执行一次，处理缓存 // 每5秒一次 */5 * * * * ?
//	public void heartbeat(){
//		String queryShowGateUrl = "http://localhost:8085/hdGate/laneManagement/queryShowGateLanes";
//		String retGateListMsg = HttpsUtils.doGet(queryShowGateUrl,"utf-8"); // 查询所有启用车道
//		//log.info("retMsg:{}",retGateListMsg);
//		List<GateLane> gateList = JSONArray.parseArray(retGateListMsg, GateLane.class);
//		for (GateLane lane:gateList){
//			String laneCode = lane.getLaneCode();
//			long oldTime = Timestamp.valueOf(redisUtils.get("receiveMsgTime"+laneCode)).getTime();
//			long curTime = System.currentTimeMillis();
//			long subTime = curTime - oldTime;
//			if (subTime > 3*1000){ // 大于10秒默认是工控机链接断开 // 自己增加定时器，来进行时间判断//
//				log.info("上次时间与本次时间间隔："+subTime+"已经超时");
//				int counts = Integer.parseInt(redisUtils.get("receiveCountsN"+laneCode));
//				if (counts == 0){
//					log.info(laneCode+"上次时间与本次时间间隔："+subTime+"已经超时，第一次发送数据");
//					redisUtils.set("receiveCountsY"+laneCode,"1"); // 发生异常设置为1，再次回复正常时候判断该值是1，推送前端
//					String retData = "与车道"+laneCode+"工控机链接超时，确认后如果长时间没有弹框提示网络恢复，请联系相关人员请查看网络情况或工控机情况";
//					String url = "http://localhost:8085/hdGate/sys/showErrorMsg";
//					String retMsg = HttpsUtils.doPost(url,retData,"utf-8");
//					log.info("调用api："+url+"，返回值："+retMsg);
//					// 断开链接默认岸桥数据异常 TODO 设置设备状态
//					// 设置pc为0，异常
//					EquipmentStatusEntity entity = new EquipmentStatusEntity();
//
//					redisUtils.set("receiveStatus"+laneCode, JSONObject.toJSONString(entity));
//				}else{
//					log.info("已经发送过数据");
//				}
//				counts = counts - 1;
//				redisUtils.set("receiveCountsN"+laneCode,counts+"");
//			} else {
//				// log.info("工控机运行正常");
//				int counts = Integer.parseInt(redisUtils.get("receiveCountsY"+laneCode));
//				if (counts == 1){
//					log.info("恢复正常，第一次发送数据");
//					String retData = "车道"+laneCode+"网络或工控机恢复正常";
//					String url = "http://localhost:8085/hdGate/sys/showErrorMsg";
//					String retMsg = HttpsUtils.doPost(url,retData,"utf-8");
//					log.info("调用api："+url+"，返回值："+retMsg);
//					redisUtils.set("receiveCountsY"+laneCode,"0");
//					redisUtils.set("receiveCountsN"+laneCode,"0"); // 恢复正常，初始化原本receiveCountsN数据为0
//					// 恢复正常后，默认相应设备全部为正常 TODO 设置设备状态
//					redisUtils.set("receiveStatus"+laneCode, "");
//				}else{
//					// log.info("已经发送过数据或工控机原本运行正常");
//				}
//			}
//		}
//	}

	//@Async
	//@Scheduled(cron = "*/3 * * * * ?")
	@Scheduled()
	public void deviceTask(){
		for(int i : laneDBUtils.getAllLaneDB()) {
			String statusEntity = redisUtils.lpopStack("device_data", i);
			String laneCode = laneDBUtils.getLaneCode(i);
			if (statusEntity != null && !statusEntity.equals("null") && !statusEntity.equals("")) {
				List<DeviceEntity> list = JSONArray.parseArray(statusEntity,DeviceEntity.class);
				log.info("device:" + list.toString());
				EquipmentStatusEntity equipmentStatusEntity = new EquipmentStatusEntity(list, laneCode);
				String receiveStatus = redisUtils.get("receiveStatus" + laneCode);
				EquipmentStatusEntity oldEntity = JSONObject.parseObject(receiveStatus, EquipmentStatusEntity.class);
				String equipmentStatus = JSONObject.toJSONString(equipmentStatusEntity);
				if(!equipmentStatusEntity.equals(oldEntity) && equipmentStatus.contains("设备不在线")){
					redisUtils.set("receiveStatus" + laneCode, equipmentStatus);
					String url = "http://localhost:8085/hdGate/sys/showErrorMsg";
					//String retMsg = HttpsUtils.doPost(url, equipmentStatus, "utf-8");
					String retMsg = HttpsUtils.doPost(url, laneCode+"有设备不在线，请及时处理", "utf-8");
					log.info("调用api：" + url + "，返回值：" + retMsg);
				}
			} else {
				//设备异常
				EquipmentStatusEntity entity = new EquipmentStatusEntity();
				redisUtils.set("receiveStatus"+laneCode, JSONObject.toJSONString(entity));
				String retData = "与车道"+laneCode+"工控机链接超时，确认后如果长时间没有弹框提示网络恢复，请联系相关人员请查看网络情况或工控机情况";
				String url = "http://localhost:8085/hdGate/sys/showErrorMsg";
				String retMsg = HttpsUtils.doPost(url,retData,"utf-8");
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
		businessEntity.setMsg(maps.get("message").toString());

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
		businessEntity.setOcrFrontContainer(ocrFrontContainer);

		ContainerEntity ocrAfterContainer = new ContainerEntity();
		ocrAfterContainer.setOcrContainerNo(((Map) maps.get("containerbehind")).get("number").toString());
		ocrAfterContainer.setOcrContainerISO(((Map) maps.get("containerbehind")).get("iso").toString());
		ocrAfterContainer.setOcrContainerDirection(((Map) maps.get("containerbehind")).get("direction").toString());
		ocrAfterContainer.setLeadSealNo(((Map) maps.get("containerbehind")).get("leadsealno").toString());
		ocrAfterContainer.setProperty(((Map) maps.get("containerbehind")).get("property").toString());
		ocrAfterContainer.setLeadSealState(((Map) maps.get("containerbehind")).get("leadsealstate").toString());
		ocrFrontContainer.setEfid(((Map) maps.get("containerbehind")).get("efid").toString());
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

	/**
	 * 接收识别数据
	 */
	@Async
	@Scheduled(cron = "*/3 * * * * ?")
	public void getOcrTask() {
		for(int i:laneDBUtils.getAllLaneDB()){
			String autoGateBusiness = redisUtils.rpopQueue("web_data",i);
			if(autoGateBusiness!=null && !autoGateBusiness.equals("null") && !autoGateBusiness.equals("")){
				Map<String,Object> maps = (Map) JSON.parse(autoGateBusiness);
				log.info("laneMonitorApi接收到数据：{}，转发到redis的hd_gate_business_data_db频道中",autoGateBusiness);

				BusinessEntity businessEntity = packageOcrData(maps);

				autoGateBusiness = CommonUtils.cdiEntity2ShowEntityStr(businessEntity);
				System.out.println("++++++++++++++++++"+autoGateBusiness);
				stringRedisTemplate.convertAndSend("hd_gate_business_data_db",autoGateBusiness);// redis频道
				businessService.sendData2Html(businessEntity.getGeneralInfo().getLaneCode(),autoGateBusiness); // 推送数据到页面

			}else{
				//log.info("lane{}:ocr为空",i);
			}
		}
	}
//模拟中间层信息发送
//	@Scheduled(cron = "*/3 * * * * ?")
//	public void getTest(){
//		String data = "[\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:20:56\",\n" +
//				"    \"devno\": \"frontCamera\",\n" +
//				"    \"devname\": \"前相机\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.171\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:20:57\",\n" +
//				"    \"devno\": \"backCamera\",\n" +
//				"    \"devname\": \"后相机\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.172\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:20:57\",\n" +
//				"    \"devno\": \"leftCamera\",\n" +
//				"    \"devname\": \"左相机\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.173\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:20:58\",\n" +
//				"    \"devno\": \"rightCamera\",\n" +
//				"    \"devname\": \"右相机\",\n" +
//				"    \"detail\": \""+A.getM()+"\",\n" +
//				"    \"ip\": \"10.23.3.174\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:20:58\",\n" +
//				"    \"devno\": \"leftCdiCamera\",\n" +
//				"    \"devname\": \"左验残相机\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.175\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:20:59\",\n" +
//				"    \"devno\": \"rightCdiCamera\",\n" +
//				"    \"devname\": \"右验残相机\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.176\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:20:59\",\n" +
//				"    \"devno\": \"topCdiCamera\",\n" +
//				"    \"devname\": \"上验残相机\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.177\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:21:00\",\n" +
//				"    \"devno\": \"truckNoCamera\",\n" +
//				"    \"devname\": \"车牌相机\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.178\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:21:00\",\n" +
//				"    \"devno\": \"truckCamera\",\n" +
//				"    \"devname\": \"车架相机\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.179\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:21:01\",\n" +
//				"    \"devno\": \"led\",\n" +
//				"    \"devname\": \"led\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.180\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:21:01\",\n" +
//				"    \"devno\": \"plc\",\n" +
//				"    \"devname\": \"plc\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.181\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:21:02\",\n" +
//				"    \"devno\": \"printer\",\n" +
//				"    \"devname\": \"打印机\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.182\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:21:02\",\n" +
//				"    \"devno\": \"intercom\",\n" +
//				"    \"devname\": \"对讲终端\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.183\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:21:03\",\n" +
//				"    \"devno\": \"comServer\",\n" +
//				"    \"devname\": \"串口服务器\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.184\"\n" +
//				"  },\n" +
//				"  {\n" +
//				"    \"uuid\": \"\",\n" +
//				"    \"lanecode\": \"01\",\n" +
//				"    \"time\": \"2020-03-02 08:21:03\",\n" +
//				"    \"devno\": \"workstation\",\n" +
//				"    \"devname\": \"识别工作站\",\n" +
//				"    \"detail\": \"正常\",\n" +
//				"    \"ip\": \"10.23.3.185\"\n" +
//				"  }\n" +
//				"]";
//
//			redisUtils.lpushQueue("device_data", data, 1);
//			redisUtils.lpushQueue("device_data", data, 0);
//			redisUtils.lpushQueue("device_data", data, 2);
//	}
}
