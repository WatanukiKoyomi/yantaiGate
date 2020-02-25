package com.huadong.hdgate.common.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huadong.hdgate.common.utils.CommonUtils;
import com.huadong.hdgate.common.utils.LaneDBUtils;
import com.huadong.hdgate.common.utils.RedisUtils;
import com.huadong.hdgate.common.utils.webapi.HttpsUtils;
import com.huadong.hdgate.jobManagement.entity.cdiEntity.*;
import com.huadong.hdgate.jobManagement.entity.xijingEntity.DeviceEntity;
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
public class ScheduledTask {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    RedisUtils redisUtils;
    @Resource(name="template")
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private BusinessService businessService;
    @Resource
    private LaneDBUtils laneDBUtils;

    /**
     * 接收识别数据 1号道
     */
    @Scheduled(fixedDelayString = "2000")
    public void getOcrTask() {
        for(int i:laneDBUtils.getAllLaneDB()){
            String autoGateBusiness = redisUtils.rpopQueue("web_data",i);
            if(autoGateBusiness!=null && !autoGateBusiness.equals("null") && !autoGateBusiness.equals("")){
                Map<String,Object> maps = (Map)JSON.parse(autoGateBusiness);
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

//    @Scheduled(fixedDelayString = "3000")
//    public void sendData(){
//        String data = "[{\"uuid\": \"\",\"lanecode\": \"YT-1\",\"time\": \"2020-02-24 09:43:56\",\"devno\": \"truckNoCamera\",\"devname\": \"车牌相机\",\"detail\": \"异常\",\"ip\": \"127.0.0.1\"},{\"uuid\": \"\",\"lanecode\": \"YT-1\",\"time\": \"2020-02-24 09:43:56\",\"devno\": \"leftCdiCamera\",\"devname\": \"左侧验残相机\",\"detail\": \"异常\",\"ip\": \"127.0.0.1\"},{\"uuid\": \"\",\"lanecode\": \"YT-1\",\"time\": \"2020-02-24 09:43:56\",\"devno\": \"rightCdiCamera\",\"devname\": \"右侧验残相机\",\"detail\": \"异常\",\"ip\": \"127.0.0.1\"},{\"uuid\": \"\",\"lanecode\": \"YT-1\",\"time\": \"2020-02-24 09:43:56\",\"devno\": \"topCdiCamera\",\"devname\": \"顶部验残相机\",\"detail\": \"异常\",\"ip\": \"127.0.0.1\"},{\"uuid\": \"\",\"lanecode\": \"YT-1\",\"time\": \"2020-02-24 09:43:56\",\"devno\": \"frontLeftOcrCamera\",\"devname\": \"前方左侧箱号相机\",\"detail\": \"异常\",\"ip\": \"127.0.0.1\"},{\"uuid\": \"\",\"lanecode\": \"YT-1\",\"time\": \"2020-02-24 09:43:56\",\"devno\": \"afterLeftOcrCamera\",\"devname\": \"后方左侧箱号相机\",\"detail\": \"异常\",\"ip\": \"127.0.0.1\"},{\"uuid\": \"\",\"lanecode\": \"YT-1\",\"time\": \"2020-02-24 09:43:56\",\"devno\": \"frontRightOcrCamera\",\"devname\": \"前方右侧箱号相机\",\"detail\": \"异常\",\"ip\": \"127.0.0.1\"},{\"uuid\": \"\",\"lanecode\": \"YT-1\",\"time\": \"2020-02-24 09:43:56\",\"devno\": \"afterRightOcrCamera\",\"devname\": \"后方右侧箱号相机\",\"detail\": \"异常\",\"ip\": \"127.0.0.1\"},{\"uuid\": \"\",\"lanecode\": \"YT-1\",\"time\": \"2020-02-24 09:43:56\",\"devno\": \"backCamera\",\"devname\": \"后相机\",\"detail\": \"异常\",\"ip\": \"127.0.0.1\"},{\"uuid\": \"\",\"lanecode\": \"YT-1\",\"time\": \"2020-02-24 09:43:56\",\"devno\": \"truckScales\",\"devname\": \"地磅\",\"detail\": \"异常\",\"ip\": \"127.0.0.1\"},{\"uuid\": \"\",\"lanecode\": \"YT-1\",\"time\": \"2020-02-24 09:43:56\",\"devno\": \"plc\",\"devname\": \"plc\",\"detail\": \"异常\",\"ip\": \"127.0.0.1\"}]";
//        redisUtils.lpushQueue("device_data",data,1);
//
//    }

    @Scheduled(fixedDelayString = "2000")
    public void getDeviceTask(){
        for(int i : laneDBUtils.getAllLaneDB()) {
            String statusEntity = redisUtils.rpopQueue("device_data", i);
            if (statusEntity != null && !statusEntity.equals("null") && !statusEntity.equals("")) {
                List<DeviceEntity> list = JSONArray.parseArray(statusEntity,DeviceEntity.class);
                log.info("device:" + list.toString());
                EquipmentStatusEntity equipmentStatusEntity = new EquipmentStatusEntity();
                equipmentStatusEntity.setLaneCode(laneDBUtils.getLaneCode(i));
                for(DeviceEntity de:list){
                    if(de.getDevname().equals("工控机")){
                        equipmentStatusEntity.setPc(de.checkDetail());
                    }
                    if(de.getDevname().equals("车牌相机")){
                        equipmentStatusEntity.setTruckNoCamera(de.checkDetail());
                    }
                    if(de.getDevname().equals("左侧验残相机")){
                        equipmentStatusEntity.setLeftCdiCamera(de.checkDetail());
                    }
                    if(de.getDevname().equals("右侧验残相机")){
                        equipmentStatusEntity.setRightCdiCamera(de.checkDetail());
                    }
                    if(de.getDevname().equals("顶部验残相机")){
                        equipmentStatusEntity.setTopCdiCamera(de.checkDetail());
                    }
                    if(de.getDevname().equals("前方左侧箱号相机")){
                        equipmentStatusEntity.setFrontLeftOcrCamera(de.checkDetail());
                    }
                    if(de.getDevname().equals("后方左侧箱号相机")){
                        equipmentStatusEntity.setAfterLeftOcrCamera(de.checkDetail());
                    }
                    if(de.getDevname().equals("前方右侧箱号相机")){
                        equipmentStatusEntity.setFrontRightOcrCamera(de.checkDetail());
                    }
                    if(de.getDevname().equals("后方右侧箱号相机")){
                        equipmentStatusEntity.setAfterRightOcrCamera(de.checkDetail());
                    }
                    if(de.getDevname().equals("后相机")){
                        equipmentStatusEntity.setBackCamera(de.checkDetail());
                    }
                    if(de.getDevname().equals("地磅")){
                        equipmentStatusEntity.setTruckScales(de.checkDetail());
                    }
                    if(de.getDevname().equals("plc")){
                        equipmentStatusEntity.setPlc(de.checkDetail());
                    }
                    if(de.getDevname().equals("打印机")){
                        equipmentStatusEntity.setPrint(de.checkDetail());
                    }
                }
                String url = "http://localhost:8085/hdGate/monitor/laneEquipmentStatus";
                System.out.println("equipmentStatusEntity:"+JSONObject.toJSONString(equipmentStatusEntity));
                String retMsg = HttpsUtils.doPost(url,JSONObject.toJSONString(equipmentStatusEntity),"utf-8");

            } else {
                //log.info("lane{}:device为空",i);
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

    public EquipmentStatusEntity packageDeviceData(JSONArray array){
        return null;
    }

}
