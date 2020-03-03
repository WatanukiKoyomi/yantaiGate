package com.huadong.hdgate.laneManagement.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.huadong.hdgate.jobManagement.entity.xijingEntity.DeviceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang.builder.EqualsBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 工控机相关设备状态实体类
 */
@Data
@EqualsAndHashCode
@ToString
public class EquipmentStatusEntity implements Serializable {
	/**
	 * 通道号
	 */
	@JSONField(name="laneCode")
	private String laneCode;
	/**
	 * 前相机
	 */
	@JSONField(name="frontCamera")
	private String frontCamera;
	/**
	 * 后相机
	 */
	@JSONField(name="backCamera")
	private String backCamera;
	/**
	 * 左相机
	 */
	@JSONField(name="leftCamera")
	private String leftCamera;
	/**
	 * 右相机
	 */
	@JSONField(name="rightCamera")
	private String rightCamera;

	/**
	 * 左验残相机
	 */
	@JSONField(name="leftCdiCamera")
	private String leftCdiCamera;
	/**
	 * 右验残相机
	 */
	@JSONField(name="rightCdiCamera")
	private String rightCdiCamera;
	/**
	 * 上验残相机
	 */
	@JSONField(name="topCdiCamera")
	private String topCdiCamera;
	/**
	 * 车牌相机
	 */
	@JSONField(name="truckNoCamera")
	private String truckNoCamera;
	/**
	 * 车架相机
	 */
	@JSONField(name="truckCamera")
	private String truckCamera;
	/**
	 * led
	 */
	@JSONField(name="led")
	private String led;
	/**
	 * plc
	 */
	@JSONField(name="plc")
	private String plc;
	/**
	 * 打印机
	 */
	@JSONField(name="printer")
	private String printer;
	/**
	 * 对讲终端
	 */
	@JSONField(name="intercom")
	private String intercom;
	/**
	 * 串口服务器
	 */
	@JSONField(name="comServer")
	private String comServer;
	/**
	 * 识别工作站
	 */
	@JSONField(name="workstation")
	private String workstation;

	public EquipmentStatusEntity(){
		this.laneCode = "0";
		this.frontCamera = "0";
		this.backCamera = "0";
		this.leftCamera = "0";
		this.rightCamera = "0";
		this.leftCdiCamera = "0";
		this.rightCdiCamera = "0";
		this.topCdiCamera = "0";
		this.truckNoCamera = "0";
		this.truckCamera = "0";
		this.led = "0";
		this.plc = "0";
		this.printer = "0";
		this.intercom = "0";
		this.comServer = "0";
		this.workstation = "0";
	}

	public EquipmentStatusEntity(List<DeviceEntity> list, String landCode){
		this.laneCode = landCode;
		for(DeviceEntity de:list){
			if(de.getDevno().equals("frontCamera")){
				this.frontCamera = de.checkDetail();
			}
			if(de.getDevno().equals("backCamera")){
				this.backCamera = de.checkDetail();
			}
			if(de.getDevno().equals("leftCamera")){
				this.leftCamera = de.checkDetail();
			}
			if(de.getDevno().equals("rightCamera")){
				this.rightCamera = de.checkDetail();
			}
			if(de.getDevno().equals("leftCdiCamera")){
				this.leftCdiCamera = de.checkDetail();
			}
			if(de.getDevno().equals("rightCdiCamera")){
				this.rightCdiCamera = de.checkDetail();
			}
			if(de.getDevno().equals("topCdiCamera")){
				this.topCdiCamera = de.checkDetail();
			}
			if(de.getDevno().equals("truckNoCamera")){
				this.truckNoCamera = de.checkDetail();
			}
			if(de.getDevno().equals("truckCamera")){
				this.truckCamera = de.checkDetail();
			}
			if(de.getDevno().equals("led")){
				this.led = de.checkDetail();
			}
			if(de.getDevno().equals("plc")){
				this.plc = de.checkDetail();
			}
			if(de.getDevno().equals("printer")){
				this.printer = de.checkDetail();
			}
			if(de.getDevno().equals("intercom")){
				this.intercom = de.checkDetail();
			}
			if(de.getDevno().equals("comServer")){
				this.comServer = de.checkDetail();
			}
			if(de.getDevno().equals("workstation")){
				this.workstation = de.checkDetail();
			}
		}
	}
}
