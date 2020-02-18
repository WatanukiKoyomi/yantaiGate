package com.huadong.hdgate.common.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * 分页
 */
public class BaseEntityWithPage implements Serializable {
	@JSONField(serialize = false)
	private Integer id;
	@JSONField(serialize = false)
	private List rows;//存放行列数据，用的bootstrap必须命名rows
	@JSONField(serialize = false)
	private Integer page = 1;
	@JSONField(serialize = false)
	private Integer total = 10;//存放一共多少数据，用的bootstrap必须命名total

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
