package com.huadong.hdgate.jobManagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.huadong.hdgate.common.utils.CommonUtils;
import com.huadong.hdgate.common.utils.RedisUtils;
import com.huadong.hdgate.common.utils.webapi.HttpsUtils;
import com.huadong.hdgate.jobManagement.entity.XijingParamsEntity;
import com.huadong.hdgate.jobManagement.entity.cdiEntity.BusinessEntity;
import com.huadong.hdgate.jobManagement.service.BusinessService;
import com.huadong.hdgate.laneManagement.entity.EquipmentStatusEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * 车道监控页面相关controller
 */
@Api(value="车道监控",tags={"车道监控页面"})
@RestController
@RequestMapping(value = "/monitor")
@Slf4j
public class LaneMonitorController {

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private BusinessService businessService;
	@Resource
	private RedisUtils redisUtils;

	@ApiOperation(value = "redis测试", notes = "redis")
	@RequestMapping(value = "/testRedis", method = RequestMethod.POST)
	public String testRedis(@RequestBody String message){
		redisUtils.lpushQueue("test", message,2);
		return "ok";
	}

	@ApiOperation(value = "车道监控api", notes = "车道监控api"  )
	@RequestMapping(value = "/laneMonitorApi" , method = RequestMethod.POST)
	public String laneMonitorApi(@RequestBody String autoGateBusiness) {
		log.info("laneMonitorApi接收到数据：{}，转发到redis的hd_gate_business_data_db频道中",autoGateBusiness);
		BusinessEntity businessEntity = JSONObject.parseObject(autoGateBusiness,BusinessEntity.class);
		autoGateBusiness = CommonUtils.cdiEntity2ShowEntityStr(businessEntity);
		System.out.println("++++++++++++++++++"+autoGateBusiness);
		stringRedisTemplate.convertAndSend("hd_gate_business_data_db",autoGateBusiness);// redis频道
		businessService.sendData2Html(businessEntity.getGeneralInfo().getLaneCode(),autoGateBusiness); // 推送数据到页面

		return "success";
	}

	@ApiIgnore
	@ApiOperation(value = "查询车道最新数据", notes = "传入车道编号，查询该车道最新业务数据"  )
	@RequestMapping(value = "/queryLaneLatestData" , method = RequestMethod.POST)
	public BusinessEntity queryLaneLatestData(@RequestBody String laneCode) {
		return businessService.queryBusinessDataByLaneCode(laneCode);
	}

	@ApiIgnore
	@RequestMapping(value = "/queryLatestDataByLane" , method = RequestMethod.GET)
	public BusinessEntity queryLatestDataByLane(HttpServletRequest request){
		String laneCode = request.getParameter("laneCode");
		// 根据车道查询该车道最新业务数据
		return businessService.queryBusinessDataByLaneCode(laneCode);
	}

	/**
	 * 修改作业数据
	 */
	@ApiIgnore
	@RequestMapping(value = "/updateBusinessData" , method = RequestMethod.GET)
	public Boolean updateBusinessData(HttpServletRequest request){
		String laneCode = request.getParameter("laneCode");
		String account = request.getParameter("account");
		String todoUpdateData = request.getParameter("todoUpdateData");
		log.info("updateBusinessData,参数laneCode：{},account：{},todoUpateData：{}",laneCode,account,todoUpdateData);
		String finalBusinessData = businessService.updateBusinessData(laneCode,account,todoUpdateData);
		stringRedisTemplate.convertAndSend("hd_gate_business_data_db",finalBusinessData);// redis频道
		return true;
	}

	@Autowired
	private XijingParamsEntity xijingParamsEntity;
	/**
	 * 打印小票
	 */
	@RequestMapping(value = "/receiptClick",method = RequestMethod.GET)
	public void receiptClick(HttpServletRequest request) {
		String laneCode = request.getParameter("laneCode");
		BusinessEntity entity = businessService.queryBusinessDataByLaneCode(laneCode);
		StringBuffer sb = new StringBuffer();
		sb.append("{\"laneCode:\":\"").append(laneCode).append("\"}");
		String url = "http://"+xijingParamsEntity.getIp()+":"+xijingParamsEntity.getPort()+"/xijing/monitor/rod";
		log.info("车道{}，打印小票--数据：{}，\n调用地址:{},参数:{}",laneCode,entity,url,sb.toString());
		HttpsUtils.doPost(url,sb.toString(),"urf-8");
	}
	/**
	 * 抬杆
	 */
	@RequestMapping(value = "/rodClick",method = RequestMethod.GET)
	public void rodClick(HttpServletRequest request) {
		String laneCode = request.getParameter("laneCode");
		StringBuffer sb = new StringBuffer();
		sb.append("{\"laneCode:\":\"").append(laneCode).append("\"}");
		String url = "http://"+xijingParamsEntity.getIp()+":"+xijingParamsEntity.getPort()+"/xijing/monitor/rod";
		log.info("车道{}，抬杆操作，\n调用地址:{},参数:{}",laneCode,url,sb.toString());
		HttpsUtils.doPost(url,sb.toString(),"urf-8");
	}

	/**
	 * 接收设备状态，将设备状态存入redis
	 */
	@RequestMapping(value = "/laneEquipmentStatus",method = RequestMethod.POST)
	public String laneEquipmentStatus(@RequestBody String equipmentStatus) {
		EquipmentStatusEntity statusEntity = JSONObject.parseObject(equipmentStatus,EquipmentStatusEntity.class);
		String laneCode = statusEntity.getLaneCode();
		long oldTime = Timestamp.valueOf(redisUtils.get("receiveMsgTime"+laneCode)).getTime();
		long curTime = System.currentTimeMillis();
		String curTimeStr = new Timestamp(curTime).toString();
		long subTime = curTime - oldTime;
		// logger.info("上次时间与本次时间间隔："+subTime);
		redisUtils.set("receiveMsgTime"+laneCode, curTimeStr);

		String receiveStatus = redisUtils.get("receiveStatus"+laneCode);
		EquipmentStatusEntity oldEntity = JSONObject.parseObject(receiveStatus,EquipmentStatusEntity.class);

		if (!statusEntity.equals(oldEntity)){
			redisUtils.set("receiveStatus"+laneCode,equipmentStatus); // 与存储状态不一致，重复赋值后台存储状态，将该次数据推送前台
			String url = "http://localhost:8085/hdGate/sys/showErrorMsg";
			String retMsg = HttpsUtils.doPost(url,equipmentStatus,"utf-8");
			log.info("调用api："+url+"，返回值："+retMsg);
		}

		return "success";
	}

	/**
	 * 获取车道设备状态
	 */
	@RequestMapping(value = "/queryLaneEquipmentStatus",method = RequestMethod.GET)
	public EquipmentStatusEntity queryLaneEquipmentStatus(HttpServletRequest request) {
		String laneCode = request.getParameter("laneCode");
		String receiveStatus = redisUtils.get("receiveStatus"+laneCode);
		EquipmentStatusEntity oldEntity = JSONObject.parseObject(receiveStatus,EquipmentStatusEntity.class);
		return oldEntity;
	}

}
