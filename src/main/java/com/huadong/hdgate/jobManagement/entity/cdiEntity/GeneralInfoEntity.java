package com.huadong.hdgate.jobManagement.entity.cdiEntity;

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
	@JSONField(name="LaneCode")
	private String laneCode;
	/**
	 * RFID
	 */
	@JSONField(name="RFID")
	private String rfid;
	/**
	 * 总重量
	 */
	@JSONField(name="WEIGHT")
	private String weight;
	/**
	 * 集装箱数量
	 */
	@JSONField(name="CntrSize")
	private String cntrSize;

}
