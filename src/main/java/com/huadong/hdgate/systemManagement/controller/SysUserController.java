package com.huadong.hdgate.systemManagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import com.huadong.hdgate.systemManagement.service.SysUserService;
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
 * 用户controller
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/userController")
public class SysUserController {

	private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

	@Resource
	private SysUserService sysUserService;

	@RequestMapping(value = "/queryAllUsers",method = RequestMethod.GET)
	public List<SysUserEntity> queryAllUsers() {
		return sysUserService.queryAllUser();
	}

	@ApiIgnore
	@RequestMapping(value = "/queryUsersByDeptId",method = RequestMethod.GET)
	public List<SysUserEntity> queryUsersByDeptId(HttpServletRequest request) {
		String deptId = request.getParameter("deptId");
		return sysUserService.queryUsersByDeptId(deptId);
	}

	@ApiIgnore
	@RequestMapping(value = "/deleteUser",method = RequestMethod.GET)
	public void deleteUser(HttpServletRequest request) {
		String flag = request.getParameter("flag");
		String parentDeptId = request.getParameter("parentDeptId");
		//SysDeptEntity parentDept = JSONObject.parseObject(parentDeptStr,SysDeptEntity.class);
		logger.info("deleteUser---参数："+flag+","+parentDeptId);
		if("dept".equals(flag)){// 等于flag根据部门删除
			sysUserService.deleteUserByDept(parentDeptId);
		}else {// 根据角色删除，删除单个角色
			String account = request.getParameter("account");
			//SysUserEntity user = JSONObject.parseObject(userRow,SysUserEntity.class);
			sysUserService.deleteUserByAccount(account);
		}
	}

	@ApiIgnore
	@RequestMapping(value = "/addOrUpdateUser",method = RequestMethod.GET)
	public void addOrUpdateUser(HttpServletRequest request) {
		String flag = request.getParameter("flag");
		String userForm = request.getParameter("userForm");
		String parentDeptId = request.getParameter("parentDeptId");
		SysUserEntity user = JSONObject.parseObject(userForm,SysUserEntity.class);
		//SysDeptEntity parentDept = JSONObject.parseObject(parentDeptStr,SysDeptEntity.class);
		user.setOrgnId(parentDeptId);//设置用户所在部门

		sysUserService.addOrUpdateUser(user,flag);
	}

	@ApiIgnore
	@RequestMapping(value = "/updateUserSelectedMachines",method = RequestMethod.GET)
	public String updateUserSelectedMachines(HttpServletRequest request) {
		String account = request.getParameter("account");
		String machines = request.getParameter("machines");
		logger.info("设置用户："+account+"的查看大机号："+machines);
		sysUserService.updateUserSelectedMachines(account,machines);
		return machines;
	}

}
