package com.huadong.hdgate.systemManagement.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色资源中间表实体类
 */
@TableName("SYS_USER_ROLE")
@Data
public class SysUserRoleEntity implements Serializable {


	@TableField(value = "USER_ID")
	private String userId;
	@TableField(value = "ROLE_ID")
	private String roleId;

}
