package com.huadong.hdgate.common.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huadong.hdgate.common.utils.CommonUtils;
import com.huadong.hdgate.common.utils.RedisUtils;
import com.huadong.hdgate.jobManagement.entity.cdiEntity.*;
import com.huadong.hdgate.jobManagement.service.BusinessService;
import com.huadong.hdgate.laneManagement.entity.EquipmentStatusEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * TODO
 * 轮询各通道redis的定时任务
 * @author chuyz
 * @date 2020/2/19 16:19
 */
@Component
@Slf4j
@EnableAsync
public class ScheduledTask {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private BusinessService businessService;

    /**
     * 接收识别数据 1号道
     */
    @Async
    @Scheduled(fixedDelayString = "2000")
    public void getOcrTask1() {
        String autoGateBusiness = redisUtils.rpopQueue("ocr",1);
        if(autoGateBusiness!=null && !autoGateBusiness.equals("null") && !autoGateBusiness.equals("")){
            Map<String,Object> maps = (Map)JSON.parse(autoGateBusiness);
            log.info("laneMonitorApi接收到数据：{}，转发到redis的hd_gate_business_data_db频道中",autoGateBusiness);

            BusinessEntity businessEntity = packageOcrData(maps);

            autoGateBusiness = CommonUtils.cdiEntity2ShowEntityStr(businessEntity);
            System.out.println("++++++++++++++++++"+autoGateBusiness);
            stringRedisTemplate.convertAndSend("hd_gate_business_data_db",autoGateBusiness);// redis频道
            businessService.sendData2Html(businessEntity.getGeneralInfo().getLaneCode(),autoGateBusiness); // 推送数据到页面

        }else{
            log.info("pop为空");
        }
    }

    @Async
    @Scheduled(fixedDelayString = "2000")
    public void getDeviceTask(){
        String statusEntity = redisUtils.rpopQueue("device",1);
        if(statusEntity !=null && !statusEntity.equals("null") && !statusEntity.equals("")){
            JSONArray json = JSONArray.parseArray(statusEntity);
            log.info("device:"+json.toString());
        }else{
            log.info("device error");
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

    public EquipmentStatusEntity packageDeviceData(JSONArray array){
        return null;
    }

}
