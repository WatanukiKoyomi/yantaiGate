package com.huadong.hdgate.systemManagement.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * 角色相关信息
 */
@TableName("SYS_ROLE")
public class SysRoleEntity implements Serializable {
	public static final String ROLE_ID = "ROLE_ID";
	public static final String RESOURCE_ID = "RESOURCE_ID";
	/**
	 * id
	 */
	@TableId(value = "ROLE_ID",type = IdType.ID_WORKER_STR)
	private String roleId;
	/**
	 * roleName
	 */
	@TableField(value = "ROLE_NAME")
	private String roleName;
	/**
	 * 父id
	 */
	@TableField(value = "PARENT_ROLE_ID")
	private String parentRoleId;
	/**
	 * 备注，描述
	 */
	@TableField(value = "ROLE_DESCRIPTION")
	private String description;
	/**
	 * 是否管理员角色
	 */
	@TableField(value = "IS_ADMIN")
	private String isAdmin;
	/**
	 * 作业公司
	 */
	@TableField(value = "ORGN_ID")
	private String orgnId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getParentRoleId() {
		return parentRoleId;
	}

	public void setParentRoleId(String parentRoleId) {
		this.parentRoleId = parentRoleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getOrgnId() {
		return orgnId;
	}

	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
}
