package com.huadong.hdgate.jobManagement.entity;

import lombok.Data;

/**
 * 统计集卡号识别率，箱号识别率表格实体类
 */
@Data
public class StatisticsRationEntity {
	/**
	 * 表格展示最近几天的数据
	 */
	public static final int DAYS = 7;

	/**
	 * 日期
	 */
	private String name;
	/**
	 * 集卡号
	 */
	private int truckCount;
	/**
	 * 集卡号识别率
	 */
	private double truckRation;

	/**
	 * 箱号
	 */
	private int cntrCount;
	/**
	 * 箱号识别率
	 */
	private double cntrRation;

}
