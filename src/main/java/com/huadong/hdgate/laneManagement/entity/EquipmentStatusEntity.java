package com.huadong.hdgate.laneManagement.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.builder.EqualsBuilder;

import java.io.Serializable;

/**
 * 工控机相关设备状态实体类
 */
@Data
@EqualsAndHashCode
public class EquipmentStatusEntity implements Serializable {
	@JSONField(name="laneCode")
	private String laneCode;
	/**
	 * 工控机
	 */
	@JSONField(name="pc")
	private String pc;
	/**
	 * 车牌相机
	 */
	@JSONField(name="truckNoCamera")
	private String truckNoCamera;
	/**
	 * 左侧验残相机
	 */
	@JSONField(name="leftCdiCamera")
	private String leftCdiCamera;
	/**
	 * 右侧验残相机
	 */
	@JSONField(name="rightCdiCamera")
	private String rightCdiCamera;
	/**
	 * 顶部验残相机
	 */
	@JSONField(name="topCdiCamera")
	private String topCdiCamera;
	/**
	 * 前方左侧箱号相机
	 */
	@JSONField(name="frontLeftOcrCamera")
	private String frontLeftOcrCamera;
	/**
	 * 后方左侧箱号相机
	 */
	@JSONField(name="afterLeftOcrCamera")
	private String afterLeftOcrCamera;
	/**
	 * 前方右侧箱号相机
	 */
	@JSONField(name="frontRightOcrCamera")
	private String frontRightOcrCamera;
	/**
	 * 后方右侧箱号相机
	 */
	@JSONField(name="afterRightOcrCamera")
	private String afterRightOcrCamera;
	/**
	 * 后相机
	 */
	@JSONField(name="backCamera")
	private String backCamera;
	/**
	 * 地磅
	 */
	@JSONField(name="truckScales")
	private String truckScales;
	/**
	 * PLC
	 */
	@JSONField(name="plc")
	private String plc;
	/**
	 * 打印机
	 */
	@JSONField(name="print")
	private String print;

}
