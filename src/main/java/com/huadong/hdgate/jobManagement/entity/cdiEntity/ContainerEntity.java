package com.huadong.hdgate.jobManagement.entity.cdiEntity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 集装箱相关信息（前箱后箱数据格式相同：箱号，箱型等）
 */
@Data
public class ContainerEntity {
	/**
	 * 箱号
	 */
	@JSONField(name="OCRContainerNo")
	private String ocrContainerNo;
	/**
	 * 箱号可信度
	 */
	@JSONField(name="OCRContainerConf")
	private String ocrContainerConf;
	/**
	 * 箱门朝向
	 */
	@JSONField(name="OCRContainerDirection")
	private String ocrContainerDirection;
	/**
	 * 箱型
	 */
	@JSONField(name="OCRContainerISO")
	private String ocrContainerISO;
	/**
	 * 箱型可信度
	 */
	@JSONField(name="OCRISOConf")
	private String ocrIsoConf;
	/**
	 * 箱体残损内容
	 */
	@JSONField(name="OCRDamage")
	private String ocrDamage;
	/**
	 * 空箱E or 重箱F
	 */
	@JSONField(name="EFID")
	private String efid;
	/**
	 * 铅封状态
	 */
	@JSONField(name="leadsealstate")
	private String leadSealState;
	/**
	 * 铅封号
	 */
	@JSONField(name="leadsealno")
	private String leadSealNo;
	/**
	 * 箱属
	 */
	@JSONField(name="property")
	private String property;

	/**
	 * 是否自备箱
	 */
	private String isProvideForOneself;

}
