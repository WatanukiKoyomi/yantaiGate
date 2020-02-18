package com.huadong.hdgate.systemManagement.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huadong.hdgate.systemManagement.entity.SysRoleEntity;
import com.huadong.hdgate.systemManagement.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysRoleService")
public class SysRoleService extends ServiceImpl<SysRoleMapper,SysRoleEntity> {

	/**
	 * 查询所有角色信息
	 * @return 角色list
	 */
	public List<SysRoleEntity> queryAllRoles(){
		Wrapper<SysRoleEntity> wrapper = new EntityWrapper<>();
		return  super.selectList(wrapper);
	}

	/**
	 * 添加或修改角色信息
	 * @param roleEntity 角色信息
	 */
	public void addOrUpdateRole(SysRoleEntity roleEntity){
		super.insertOrUpdate(roleEntity);
	}

	/**
	 * 删除角色信息
	 * @param id 角色id
	 */
	public void deleteRole(String id){
		Wrapper<SysRoleEntity> wrapper = new EntityWrapper<>();
		wrapper.eq(SysRoleEntity.ROLE_ID,id);
		super.delete(wrapper);
	}

}
