package com.huadong.hdgate.systemManagement.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huadong.hdgate.systemManagement.entity.SysRoleEntity;
import com.huadong.hdgate.systemManagement.entity.SysRoleResourceEntity;
import com.huadong.hdgate.systemManagement.mapper.SysRoleResourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("sysRoleResourceService")
public class SysRoleResourceService extends ServiceImpl<SysRoleResourceMapper,SysRoleResourceEntity> {

	private static final Logger logger = LoggerFactory.getLogger(SysRoleResourceService.class);

	/**
	 * 更改角色资源中间表内容
	 * @param roleId id
	 * @param flag m:表示菜单，b:表示按钮
	 */
	public void deleteRoleResource(String roleId,String flag){
		Wrapper<SysRoleResourceEntity> deleteWrapper = new EntityWrapper<>();
		deleteWrapper.eq(SysRoleEntity.ROLE_ID,roleId);
		deleteWrapper.eq(SysRoleResourceEntity.RESOURCE_TYPE,flag);
		super.delete(deleteWrapper);// 删除原本的菜单权限
	}

	/**
	 * 删除按钮权限
	 * @param roleId id
	 * @param btnId id
	 * @param flag m:表示菜单，b:表示按钮
	 */
	public void deleteRoleResource(String roleId,String btnId,String flag){
		Wrapper<SysRoleResourceEntity> deleteWrapper = new EntityWrapper<>();
		deleteWrapper.eq(SysRoleEntity.ROLE_ID,roleId);
		deleteWrapper.eq(SysRoleEntity.RESOURCE_ID,btnId);
		deleteWrapper.eq(SysRoleResourceEntity.RESOURCE_TYPE,flag);
		super.delete(deleteWrapper);// 删除原本的菜单权限
	}

	/**
	 * 插入角色资源表
	 * @param roleId 角色id
	 * @param resourceIdsStr 资源id
	 * @param flag 标志菜单或按钮
	 */
	public void addRoleResouece(String roleId,String resourceIdsStr,String flag){
		String[] resourceIds = resourceIdsStr.split(",");
		List<SysRoleResourceEntity> list = new ArrayList<>();
		for (String resourceId:resourceIds){
			SysRoleResourceEntity entity = new SysRoleResourceEntity();
			entity.setRoleId(roleId);
			entity.setResourceId(resourceId);
			entity.setResourceType(flag);
			list.add(entity);
		}
		super.insertBatch(list);
	}
}
