package com.huadong.hdgate.systemManagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.huadong.hdgate.systemManagement.entity.SysRoleEntity;
import com.huadong.hdgate.systemManagement.service.SysRoleService;
import com.huadong.hdgate.systemManagement.service.SysService;
import com.huadong.hdgate.systemManagement.service.SysUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 角色controller
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/roleController")
public class SysRoleController {

	private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysService sysService;
	@Resource
	private SysUserRoleService sysUserRoleService;

	@RequestMapping(value = "/queryAllRoles",method = RequestMethod.GET)
	public List<SysRoleEntity> queryAllRoles() {
		return sysRoleService.queryAllRoles();
	}

	@RequestMapping(value = "/addOrUpdateRole",method = RequestMethod.GET)
	public void addOrUpdateRole(HttpServletRequest request) {
		String roleForm = request.getParameter("roleForm");
		SysRoleEntity role = JSONObject.parseObject(roleForm,SysRoleEntity.class);
		sysRoleService.addOrUpdateRole(role);
	}

	@RequestMapping(value = "/deleteRole",method = RequestMethod.GET)
	public void deleteRole(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		sysRoleService.deleteRole(roleId);
	}

	/**
	 * 根据角色id查询菜单
	 * @param request 获取参数
	 * @return 返回值
	 */
	@RequestMapping(value = "/queryMenusByRoleId",method = RequestMethod.GET)
	public List<String> queryMenusByRoleId(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		System.out.println("RoleController-->queryPermsByRoleId :"+ roleId);
		List<String> list = sysService.queryMenusByRoleId(roleId);
		System.out.println("queryMenusByRoleId:"+list);
		return list;
	}

	/**
	 * 根据用户id查询用户拥有的角色权限
	 * @param request 获取参数
	 * @return 返回值
	 */
	@RequestMapping(value = "/queryRolesByUser",method = RequestMethod.GET)
	public List<SysRoleEntity> queryRolesByUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		return sysService.queryRolesByUserId(userId);
	}

	@RequestMapping(value = "/addOrDeleteUserRole",method = RequestMethod.GET)
	public void addOrDeleteUserRole(HttpServletRequest request) {
		String flag = request.getParameter("flag");//true新增，false删除
		String userId = request.getParameter("userId");
		String roleId = request.getParameter("roleId");
		sysUserRoleService.insertOrDeleteUserRole(roleId,userId,flag);
	}
}
