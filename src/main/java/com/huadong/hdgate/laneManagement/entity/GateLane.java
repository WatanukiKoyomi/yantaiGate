package com.huadong.hdgate.laneManagement.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 车道
 */
@TableName("GATE_LANE")
@Data
public class GateLane {
	public static String SHOW_FLAG = "SHOW_FLAG";
	public static String LANE_CODE = "LANE_CODE";
	/**
	 * id
	 */
	@TableId(value = "LANE_CODE")
	private String laneCode;
	/**
	 * 名称
	 */
	@TableField(value = "LANE_NAME")
	private String laneName;

	/**
	 * 方向
	 */
	@TableField(value = "LANE_DIRECTION")
	private String laneDirection;
	/**
	 * 创建时间
	 */
	@TableField(value = "CREATE_TIME")
	private String createTime;
	/**
	 * 更新时间
	 */
	@TableField(value = "UPDATE_TIME")
	private String updateTime;
	/**
	 * 车道性质（散杂货，行政。。。）
	 */
	@TableField(value = "LANE_PROPERTY")
	private String laneProperty;
	/**
	 * ip
	 */
	@TableField(value = "LANE_IP")
	private String laneIp;
	/**
	 * 车道端口
	 */
	@TableField(value = "LANE_PORT")
	private String lanePort;
	/**
	 * 作业方式（AUTO/MANUAL)
	 */
	@TableField(value = "OPERATE_STYLE")
	private String operateStyle;
	/**
	 * 禁用启用标志
	 */
	@TableField(value = "SHOW_FLAG")
	private String showFlag;

	@TableField(value = "LANE_DB")
	private Integer laneDB;

}
