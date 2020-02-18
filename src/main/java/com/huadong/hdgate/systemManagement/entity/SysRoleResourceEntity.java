package com.huadong.hdgate.systemManagement.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色资源中间表实体类
 */
@TableName("SYS_ROLE_RESOURCE")
@Data
public class SysRoleResourceEntity implements Serializable {

	public static final String RESOURCE_TYPE = "RESOURCE_TYPE";

	@TableField(value = "ROLE_ID")
	private String roleId;
	@TableField(value = "RESOURCE_ID")
	private String resourceId;

	@TableField(value = "RESOURCE_TYPE")
	private String resourceType;

}
