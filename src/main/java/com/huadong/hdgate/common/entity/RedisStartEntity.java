package com.huadong.hdgate.common.entity;

import com.alibaba.fastjson.JSONArray;
import com.huadong.hdgate.common.utils.RedisUtils;
import com.huadong.hdgate.common.utils.webapi.HttpsUtils;
import com.huadong.hdgate.laneManagement.entity.GateLane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * redis
 */
@Component
@Slf4j
public class RedisStartEntity implements CommandLineRunner {
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("--------------RedisStartEntity----------------");
		String queryShowGateUrl = "http://localhost:8085/hdGate/laneManagement/queryShowGateLanes";
		String retGateListMsg = HttpsUtils.doGet(queryShowGateUrl,"utf-8"); // 查询所有启用车道
		List<GateLane> gateList = JSONArray.parseArray(retGateListMsg, GateLane.class);
		for (GateLane lane:gateList){
			// logger.info("上次时间与本次时间间隔："+subTime);
			redisUtils.set("receiveMsgTime"+lane.getLaneCode(), new Timestamp(System.currentTimeMillis()).toString());
			redisUtils.set("receiveCountsY"+lane.getLaneCode(), "0"); // 工控机正常计数
			redisUtils.set("receiveCountsN"+lane.getLaneCode(), "0"); // 工控机链接失败计数
		}
	}
}
