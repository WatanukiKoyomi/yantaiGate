package com.huadong.hdgate.jobManagement.entity.showEntity;

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
	private String ocrContainerNo;
	/**
	 * 箱号可信度
	 */
	private String ocrContainerConf;
	/**
	 * 箱门朝向
	 */
	private String ocrContainerDirection;
	/**
	 * 箱型
	 */
	private String ocrContainerISO;
	/**
	 * 箱型可信度
	 */
	private String ocrIsoConf;
	/**
	 * 箱体残损内容
	 */
	private String ocrDamage;
	/**
	 * 空箱E or 重箱F
	 */
	private String efid;

	/**
	 * 是否自备箱
	 */
	private String isProvideForOneself;

	/**
	 * 铅封状态
	 */
	private String leadSealState;

	/**
	 * 铅封号
	 */
	private String leadSealNo;

	/**
	 * 箱属
	 */
	private String property;

	public ContainerEntity() {
	}

	public ContainerEntity(
			String ocrContainerNo,
			String ocrContainerConf,
			String ocrContainerDirection,
			String ocrContainerISO,
			String ocrIsoConf,
			String ocrDamage,
			String efid,
			String isProvideForOneself,
			String leadSealState,
			String leadSealNo,
			String property
			) {
		this.ocrContainerNo = ocrContainerNo;
		this.ocrContainerConf = ocrContainerConf;
		this.ocrContainerDirection = ocrContainerDirection;
		this.ocrContainerISO = ocrContainerISO;
		this.ocrIsoConf = ocrIsoConf;
		this.ocrDamage = ocrDamage;
		this.efid = efid;
		this.isProvideForOneself = isProvideForOneself;
		this.leadSealNo = leadSealNo;
		this.leadSealState = leadSealState;
		this.property = property;
	}
}
