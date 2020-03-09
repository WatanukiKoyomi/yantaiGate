package com.huadong.hdgate.systemManagement.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统使用用户
 */
@Data
@TableName("GATE_USER")
public class SysUserEntity implements Serializable {

	public static final String ACCOUNT = "ACCOUNT";
	public static final String ORGN_ID = "ORGN_ID";
	public static final String USER_ID = "USER_ID";

	/**
	 * id
	 */
	@TableId(value = "USER_ID",type = IdType.ID_WORKER_STR)
	private String userId;
	/**
	 * 作业公司
	 */
	@TableField(value = "ORGN_ID")
	private String orgnId;
	/**
	 * 账号
	 */
	@TableField(value = "ACCOUNT")
	private String account;
	/**
	 * 账号过期日期
	 */
	@TableField(value = "ACCOUNT_EXPIRATION_DATE")
	private Timestamp accountExpirationDate;
	/**
	 * 用户名称
	 */
	@TableField(value = "USER_NAME")
	private String userName;
	/**
	 * 密码
	 */
	@TableField(value = "USER_PWD")
	private String userPwd;
	/**
	 * 密码永不过期
	 */
	@TableField(value = "PWD_NEVER_EXPIRES")
	private String pwdNeverExpires;
	/**
	 * 密码有效天数
	 */
	@TableField(value = "PWD_VALID_DAYS")
	private int pwdValidDays;
	/**
	 * 描述
	 */
	@TableField(value = "USER_DESCRIPTION")
	private String userDescription;
	/**
	 * 语言(暂时保留)
	 */
	@TableField(value = "LANGUAGE")
	private String language;
	/**
	 * 2019.1.9用户选择的显示的车道
	 */
	@TableField(value = "SHOW_LANE")
	private String checkedShowLane;
	/**
	 * 用户当前状态
	 */
	@TableField(value = "USER_STATE")
	private String userState;
	/**
	 * 角色ids
	 */
	@TableField(exist = false)
	private List<String> roleIdLs;
	/**
	 * 是否管理员
	 */
	@TableField(exist = false)
	private boolean isAdmin;

	@TableField(exist = false)
	private List<SysResourceEntity> btnsList = new ArrayList<>();

}
