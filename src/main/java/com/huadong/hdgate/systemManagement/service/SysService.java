package com.huadong.hdgate.systemManagement.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huadong.hdgate.systemManagement.entity.SysResourceEntity;
import com.huadong.hdgate.systemManagement.entity.SysRoleEntity;
import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import com.huadong.hdgate.systemManagement.mapper.SysResourceMapper;
import com.huadong.hdgate.systemManagement.mapper.SysRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("sysService")
public class SysService extends ServiceImpl<SysResourceMapper,SysResourceEntity> {

	private static final Logger logger = LoggerFactory.getLogger(SysService.class);

	@Resource
	private SysResourceMapper sysResourceMapper;
	@Resource
	private SysRoleMapper sysRoleMapper;
	@Resource
	private SysRoleResourceService sysRoleResourceService;

	/**
	 * 根据用户信息查询菜单信息
	 * @param user 用户信息
	 * @return 菜单信息
	 */
	public List<SysResourceEntity> findUsersMenu(SysUserEntity user) {
		List<SysResourceEntity> usersMenuList = sysResourceMapper.findUsersMenu(user.getUserId());
		return getMennus2List(usersMenuList);
	}

	/**
	 * 根据用户信息查询按钮信息
	 * @param userId 用户id
	 * @return 菜单信息
	 */
	public List<SysResourceEntity> findUsersBtnsByUserId(String userId) {
		List<SysResourceEntity> userBtnList = sysResourceMapper.findUsersBtns(userId);
		System.out.println("userBtnList:"+userBtnList);
		return userBtnList;
	}

	/**
	 * 查询数据库所有的菜单信息
	 * @return 菜单信息
	 */
	public List<SysResourceEntity> findAllMenus() {
		Wrapper<SysResourceEntity> wrapper = new EntityWrapper<>();
		wrapper.eq(SysResourceEntity.RESOURCE_TYPE,"m");//m表示菜单
		return  super.selectList(wrapper);
	}

	/**
	 * 查询数据库所有的资源信息（包括菜单，按钮）
	 * @return 资源信息
	 */
	public List<SysResourceEntity> findAllResources() {
		Wrapper<SysResourceEntity> wrapper = new EntityWrapper<>();
		return  super.selectList(wrapper);
	}

	/**
	 * 根据用户查询用户所在的角色id列表
	 * @param userId 用户id
	 * @return 角色id列表
	 */
	public List<String> findRoleIdsByUserId(String userId){
		return sysRoleMapper.findRoleIdsByUserId(userId);
	}

	/**
	 * 根据用户查询用户所在的角色相关信息
	 * @param userId 用户id
	 * @return 角色相关数据list
	 */
	public List<SysRoleEntity> queryRolesByUserId(String userId){
		return sysRoleMapper.queryRolesByUserId(userId);
	}

	/**
	 * 判断用户是否管理员用户
	 * @param userId userid
	 * @return boolean
	 */
	public boolean isAdmin(String userId){
			List<String> list = sysRoleMapper.findAdminByUserId(userId);
			if(list.size()>0){
				return true;
			}
			return false;
		}

	/**
	 * 将数据库查询数据转换成树形结构数据
	 * @param list 数据库
	 * @return 树形结构
	 */
	public List<SysResourceEntity> getMennus2List(List<SysResourceEntity> list) {
		//存放根菜单
		List<SysResourceEntity> rootMenuList = new ArrayList<>();
		//存放子菜单
		List<SysResourceEntity> childMenuList = new ArrayList<>();
		List<SysResourceEntity> nodeMenuList = new ArrayList<>();

		for (SysResourceEntity menuEntity : list) {
			String parentMenuId = menuEntity.getPid();
			if(parentMenuId==null||"0".equals(parentMenuId)){
				rootMenuList.add(menuEntity);//是根菜单加入list中
			}else{
				nodeMenuList.add(menuEntity);
				childMenuList.add(menuEntity);
			}
		}
		//递归添加菜单
		for (SysResourceEntity menuTreeEntity : rootMenuList) {
			menuTreeEntity.setSubmenu(getChild(menuTreeEntity.getId()+"",nodeMenuList,"node"));
			menuTreeEntity.setChildren(getChild(menuTreeEntity.getId()+"",childMenuList,"child"));
		}
		return rootMenuList;
	}

	/**
	 * 递归查找子菜单
	 * @param id 当前菜单id
	 * @param menuList 要查找的列表
	 * @return 权限列表
	 */
	private List<SysResourceEntity> getChild(String id, List<SysResourceEntity> menuList,String flag) {
		// 子菜单
		List<SysResourceEntity> childList = new ArrayList<SysResourceEntity>();
		for (SysResourceEntity menu : menuList) {
			String menuId = menu.getPid();//该子菜单对应的父菜单id
			if(id.equals(menuId)){
				childList.add(menu);
			}
		}
		menuList.removeAll(childList);//去掉子菜单中属于父一级的菜单
		if (childList.size() == 0) {
			return null;
		}
		// 把子菜单的子菜单再循环一遍
		for (SysResourceEntity menu : childList) {
			String hasChild = menu.getHasChild();//值不为空或空字符串说明存在下一级菜单,
			if("1".equals(hasChild)){
				if("child".equals(flag)){
					menu.setChildren(getChild(menu.getId()+"", menuList,"child"));
				}else{
					menu.setSubmenu(getChild(menu.getId()+"", menuList,"node"));
				}

			}
		}
		return childList;
	}

	/**
	 * 根据角色id查询
	 * @param roleId 角色id
	 * @return list
	 */
	public List<String> queryMenusByRoleId(String roleId) {
		List<SysResourceEntity> list = sysResourceMapper.queryMenusByRoleId(roleId);
		List<String> finalList = new ArrayList<String>();
		for (SysResourceEntity entity:list){
			if("1".equals(entity.getHasChild())){
				//有子菜单,不需要加入list中
			}else{
				finalList.add(entity.getId());
			}
		}
		System.out.println(finalList);
		return finalList;
	}

	/**
	 * 修改角色的菜单权限
	 * @param roleId 角色id
	 * @param menuIdsStr 菜单id
	 */
	public void updateRoleMenus(String roleId, String menuIdsStr){
		String flag = "m"; // m表示菜单
		// 先把原本角色下的全部权限删除，然后重新赋值权限
		sysRoleResourceService.deleteRoleResource(roleId,flag);
		sysRoleResourceService.addRoleResouece(roleId,menuIdsStr,flag);
	}

	/**
	 * 添加或修改资源信息
	 * @param resourceEntity 资源
	 */
	public void addOrUpdateResource(SysResourceEntity resourceEntity){
		super.insertOrUpdate(resourceEntity);
	}

	/**
	 * 根据角色，页面，查询按钮
	 * @param roleId 角色id
	 * @param menuId 页面id
	 * @return 按钮信息
	 */
	public List<SysResourceEntity> findBtnsByRoleAndMenuId(String roleId,String menuId) {
		// 根据角色id，菜单id查询按钮
		return sysResourceMapper.findBtnsByRoleAndMenuId(roleId,menuId);
	}

	/**
	 * 直接查询页面所有按钮
	 * @param menuId 页面id
	 * @return 按钮信息
	 */
	public List<SysResourceEntity> findAllBtnsByMenuId(String menuId) {
		Wrapper<SysResourceEntity> wrapper = new EntityWrapper<>();
		wrapper.eq(SysResourceEntity.RESOURCE_TYPE,"b");//b表示按钮
		wrapper.eq(SysResourceEntity.PARENT_RESOURCE_ID,menuId);
		return  super.selectList(wrapper);
	}

	/**
	 * 添加角色按钮权限
	 * @param roleId 角色id
	 * @param btnId 菜单id
	 */
	public void addRoleBtn(String roleId, String btnId){
		String flag = "b"; // m表示菜单,b表示按钮
		sysRoleResourceService.addRoleResouece(roleId,btnId,flag);
	}

	/**
	 * 删除角色按钮权限
	 * @param roleId 角色id
	 * @param btnId 菜单id
	 */
	public void deleteRoleBtn(String roleId, String btnId){
		String flag = "b"; // m表示菜单,b表示按钮
		sysRoleResourceService.deleteRoleResource(roleId,btnId,flag);
	}
}
