package com.huadong.hdgate.common.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huadong.hdgate.common.utils.MyCacheUtils;
import com.huadong.hdgate.common.utils.RedisUtils;
import com.huadong.hdgate.common.utils.webapi.HttpsUtils;
import com.huadong.hdgate.laneManagement.entity.EquipmentStatusEntity;
import com.huadong.hdgate.laneManagement.entity.GateLane;
import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
public class TimeScheduleEntity {
	@Autowired
	private RedisUtils redisUtils;

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
	 * 2秒一次心跳检测是否收到中控发送来的数据,之后新增岸桥，加入新的定时器即可 XXX
	 */
	@Scheduled(cron = "*/3 * * * * ?") //每天凌晨1点 0 0 1 * * ? 执行一次，处理缓存 // 每5秒一次 */5 * * * * ?
	public void heartbeat(){
		String queryShowGateUrl = "http://localhost:8085/hdGate/laneManagement/queryShowGateLanes";
		String retGateListMsg = HttpsUtils.doGet(queryShowGateUrl,"utf-8"); // 查询所有启用车道
		//log.info("retMsg:{}",retGateListMsg);
		List<GateLane> gateList = JSONArray.parseArray(retGateListMsg, GateLane.class);
		for (GateLane lane:gateList){
			String laneCode = lane.getLaneCode();
			long oldTime = Timestamp.valueOf(redisUtils.get("receiveMsgTime"+laneCode)).getTime();
			long curTime = System.currentTimeMillis();
			long subTime = curTime - oldTime;
			if (subTime > 10*1000){ // 大于10秒默认是工控机链接断开 // 自己增加定时器，来进行时间判断
				// log.info("上次时间与本次时间间隔："+subTime+"已经超时");
				int counts = Integer.parseInt(redisUtils.get("receiveCountsN"+laneCode));
				if (counts == 0){
					log.info(laneCode+"上次时间与本次时间间隔："+subTime+"已经超时，第一次发送数据");
					redisUtils.set("receiveCountsY"+laneCode,"1"); // 发生异常设置为1，再次回复正常时候判断该值是1，推送前端
					String retData = "与车道"+laneCode+"工控机链接超时，确认后如果长时间没有弹框提示网络恢复，请联系相关人员请查看网络情况或工控机情况";
					String url = "http://localhost:8085/hdGate/sys/showErrorMsg";
					String retMsg = HttpsUtils.doPost(url,retData,"utf-8");
					log.info("调用api："+url+"，返回值："+retMsg);
					// 断开链接默认岸桥数据异常 TODO 设置设备状态
					// 设置pc为0，异常
					EquipmentStatusEntity entity = new EquipmentStatusEntity();
					entity.setPc("0");
					redisUtils.set("receiveStatus"+laneCode, JSONObject.toJSONString(entity));
				}else{
					// log.info("已经发送过数据");
				}
				counts = counts - 1;
				redisUtils.set("receiveCountsN"+laneCode,counts+"");
			} else {
				// log.info("工控机运行正常");
				int counts = Integer.parseInt(redisUtils.get("receiveCountsY"+laneCode));
				if (counts == 1){
					log.info("恢复正常，第一次发送数据");
					String retData = "车道"+laneCode+"网络或工控机恢复正常";
					String url = "http://localhost:8085/hdGate/sys/showErrorMsg";
					String retMsg = HttpsUtils.doPost(url,retData,"utf-8");
					log.info("调用api："+url+"，返回值："+retMsg);
					redisUtils.set("receiveCountsY"+laneCode,"0");
					redisUtils.set("receiveCountsN"+laneCode,"0"); // 恢复正常，初始化原本receiveCountsN数据为0
					// 恢复正常后，默认相应设备全部为正常 TODO 设置设备状态
					redisUtils.set("receiveStatus"+laneCode, "");
				}else{
					// log.info("已经发送过数据或工控机原本运行正常");
				}
			}
		}
	}
}
