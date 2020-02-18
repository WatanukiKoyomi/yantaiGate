package com.huadong.hdgate.jobManagement.entity.showEntity;

import lombok.Data;

/**
 * 车牌相关信息（车牌号，车牌颜色）
 */
@Data
public class CarPlateEntity {
	/**
	 * 车牌号
	 */
	private String ocrPlate;
	/**
	 * 车牌颜色
	 */
	private String plateColor;

	public CarPlateEntity() {
	}

	public CarPlateEntity(String ocrPlate, String plateColor) {
		this.ocrPlate = ocrPlate;
		this.plateColor = plateColor;
	}
}
