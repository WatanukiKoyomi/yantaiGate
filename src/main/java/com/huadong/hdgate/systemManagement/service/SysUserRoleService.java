package com.huadong.hdgate.systemManagement.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huadong.hdgate.systemManagement.entity.SysRoleEntity;
import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import com.huadong.hdgate.systemManagement.entity.SysUserRoleEntity;
import com.huadong.hdgate.systemManagement.mapper.SysUserRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("sysUserRoleService")
public class SysUserRoleService extends ServiceImpl<SysUserRoleMapper,SysUserRoleEntity> {

	private static final Logger logger = LoggerFactory.getLogger(SysUserRoleService.class);

	public void insertOrDeleteUserRole(String roleId,String userId,String flag){
		if("true".equals(flag)){
			//新增
			addUserRole(roleId,userId);
		}else{
			// 删除
			deleteUserRole(roleId,userId);
		}
	}

	private void deleteUserRole(String roleId,String userId){
		Wrapper<SysUserRoleEntity> deleteWrapper = new EntityWrapper<>();
		deleteWrapper.eq(SysRoleEntity.ROLE_ID,roleId);
		deleteWrapper.eq(SysUserEntity.USER_ID,userId);
		super.delete(deleteWrapper);// 删除原本的菜单权限
	}

	private void addUserRole(String roleId,String userId){
		SysUserRoleEntity entity = new SysUserRoleEntity();
		entity.setRoleId(roleId);
		entity.setUserId(userId);
		super.insert(entity);
	}

}
