package com.huadong.hdgate.systemManagement.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.huadong.hdgate.systemManagement.entity.SysResourceEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysResourceMapper extends BaseMapper<SysResourceEntity> {

	@Select("findUsersMenu")
	public List<SysResourceEntity> findUsersMenu(String userId);

	@Select("findUsersBtns")
	public List<SysResourceEntity> findUsersBtns(String userId);

	@Select("queryMenusByRoleId")
	public List<SysResourceEntity> queryMenusByRoleId(String roleId);

	@Select("findBtnsByRoleAndMenuId")
	public List<SysResourceEntity> findBtnsByRoleAndMenuId(@Param("roleId") String roleId, @Param("menuId") String menuId);
}
