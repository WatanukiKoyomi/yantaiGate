package com.huadong.hdgate.common.entity;

import java.sql.Timestamp;

public class SysLogEntity {
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * ip地址
	 */
	private String ip;
	/**
	 * 日志时间
	 */
	private Timestamp logTime;
	/**
	 * 日志类型
	 */
	private String logType;
	/**
	 * 日志内容
	 */
	private String logMsg;
	/**
	 * 前台展示的样式
	 */
	private String showType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getLogTime() {
		return logTime;
	}

	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}
}
