package com.huadong.hdgate.jobManagement.entity.cdiEntity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 车牌相关信息（车牌号，车牌颜色）
 */
@Data
public class CarPlateEntity {
	/**
	 * 车牌号
	 */
	@JSONField(name="OcrPlate")
	private String ocrPlate;
	/**
	 * 车牌颜色
	 */
	@JSONField(name="PlateColor")
	private String plateColor;

}
