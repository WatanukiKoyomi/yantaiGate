package com.huadong.hdgate.systemManagement.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 资源实体类
 */
@Data
@TableName("GATE_RESOURCE")
public class SysResourceEntity implements Serializable {
	public static final String RESOURCE_TYPE = "RESOURCE_TYPE";
	public static final String PARENT_RESOURCE_ID = "PARENT_RESOURCE_ID";

	@TableId(value = "RESOURCE_ID",type = IdType.ID_WORKER_STR)
	private String id;
	/**
	 * 资源的中文名称
	 */
	@TableField(value = "RESOURCE_NAME")
	private String label;
	/**
	 * 资源的英文名称
	 */
	@TableField(value = "RESOURCE_EN_NAME")
	private String resourceEnName;
	/**
	 * 菜单时的图标
	 */
	@TableField(value = "ICON")
	private String icon;
	/**
	 * 菜单对应页面名称
	 */
	@TableField(value = "MENU_NAME")
	private String name;
	/**
	 * 父级菜单id
	 */
	@TableField(value = "PARENT_RESOURCE_ID")
	private String pid;
	/**
	 * 描述
	 */
	@TableField(value = "DESCRIBE")
	private String describe;
	/**
	 * 排序
	 */
	@TableField(value = "SORT_NO")
	private String sortNo;
	/**
	 * 状态
	 */
	@TableField(value = "STATE")
	private String state;
	/**
	 * 是否有子菜单
	 */
	@TableField(value = "HASCHILD")
	private String hasChild;
	/**
	 * 是菜单还是按钮资源
	 */
	@TableField(value = "RESOURCE_TYPE")
	private String resourceType;
	/**
	 * 菜单时的子级菜单(v-menu)
	 */
	@TableField(exist = false)
	private List<SysResourceEntity> submenu;

	/**
	 * 权限中可以选择的菜单(el-tree)
	 */
	@TableField(exist = false)
	private List<SysResourceEntity> children;

}
