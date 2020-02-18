package com.huadong.hdgate.systemManagement.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门
 */
@TableName("SYS_DEPT")
@Data
public class SysDeptEntity implements Serializable {

	public static final String DEPT_ID = "DEPT_ID";
	public static final String PARENT_DEPT_ID = "PARENT_DEPT_ID";
	/**
	 * id
	 */
	@TableId(value = "DEPT_ID",type = IdType.ID_WORKER_STR)
	private String deptId;
	/**
	 * 编码
	 */
	@TableField(value = "DEPT_CODE")
	private String deptCode;
	/**
	 * 类型
	 */
	@TableField(value = "DEPT_TYPE")
	private String deptType;
	/**
	 * 父id
	 */
	@TableField(value = "PARENT_DEPT_ID")
	private String parentDeptId;
	/**
	 * 名称(便于前端树形结构显示命名label)
	 */
	@TableField(value = "DEPT_NAME")
	private String label;
	/**
	 * 简称
	 */
	@TableField(value = "DEPT_SHORT_NAM")
	private String deptShortName;
	/**
	 * 英文名称
	 */
	@TableField(value = "DEPT_EN_NAME")
	private String deptEnName;
	/**
	 * 备注
	 */
	@TableField(value = "COMMENTS")
	private String comments;
	/**
	 * 状态
	 */
	@TableField(value = "STATE")
	private String state;
	/**
	 * 用来排序
	 */
	@TableField(value = "SORT_NO")
	private int sortNo;
	/**
	 * 是否有子菜单
	 */
	@TableField(value = "HASCHILD")
	private String hasChild;

	@TableField(exist = false)
	private List<SysDeptEntity> children = new ArrayList<>();

}
