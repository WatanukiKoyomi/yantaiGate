package com.huadong.hdgate.jobManagement.entity.showEntity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 基础相关数据（车道编码，RFID，贝位编码，总重量，集装箱数量）
 */
@Data
public class GeneralInfoEntity {
	/**
	 * 车道编码
	 */
	private String laneCode;
	/**
	 * RFID
	 */
	private String rfid;
	/**
	 * 总重量
	 */
	private String weight;
	/**
	 * 集装箱数量
	 */
	private String cntrSize;

	public GeneralInfoEntity(String laneCode, String rfid, String weight, String cntrSize) {
		this.laneCode = laneCode;
		this.rfid = rfid;
		this.weight = weight;
		this.cntrSize = cntrSize;
	}

	public GeneralInfoEntity() {
	}
}
