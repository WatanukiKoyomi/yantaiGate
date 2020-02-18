package com.huadong.hdgate.systemManagement.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.huadong.hdgate.systemManagement.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRoleEntity>{

	/**
	 * 根据用户id查询所在的角色id列表
	 * @param userId 用户id
	 * @return 角色id列表
	 */
	public List<String> findRoleIdsByUserId(@Param("userId") String userId);

	/**
	 * 判断用户是否管理员用户
	 * @param userId 用户id
	 * @return 管理员角色id列表
	 */
	public List<String> findAdminByUserId(@Param("userId") String userId);

	/**
	 * 根据用户id查询角色
	 * @param userId id
	 * @return 角色
	 */
	public List<SysRoleEntity> queryRolesByUserId(String userId);
}
