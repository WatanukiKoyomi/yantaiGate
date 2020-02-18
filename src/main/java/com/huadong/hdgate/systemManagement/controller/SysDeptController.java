package com.huadong.hdgate.systemManagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.huadong.hdgate.systemManagement.entity.SysDeptEntity;
import com.huadong.hdgate.systemManagement.service.SysDeptService;
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
 * 部门controller
 */
@RestController
@RequestMapping(value = "/deptController")
@ApiIgnore
public class SysDeptController {

	private static final Logger logger = LoggerFactory.getLogger(SysDeptController.class);

	@Resource
	private SysDeptService sysDeptService;

	@RequestMapping(value = "/queryDeptsByAccount",method = RequestMethod.GET)
	public List<SysDeptEntity> queryDeptsByAccount(HttpServletRequest request) {
		String username = request.getParameter("username");
		return sysDeptService.queryDeptsByAccount(username);
	}

	@RequestMapping(value = "/addOrUpdateDept",method = RequestMethod.GET)
	public void addOrUpdateDept(HttpServletRequest request) {
		String flag = request.getParameter("flag");
		String deptStr = request.getParameter("deptForm");
		String parentDeptStr = request.getParameter("parentDept");
		System.out.println("-----"+parentDeptStr+","+flag);
		SysDeptEntity dept = JSONObject.parseObject(deptStr,SysDeptEntity.class);
		SysDeptEntity parentDept = JSONObject.parseObject(parentDeptStr,SysDeptEntity.class);
		if ("add".equals(flag)){
			dept.setParentDeptId(parentDept.getDeptId()); // 设置部门的父id,避免新增时候为空
			sysDeptService.addOrUpdateDept(dept);
			if(parentDept.getHasChild()==null||"0".equals(parentDept.getHasChild())){
				parentDept.setHasChild("1");
				sysDeptService.addOrUpdateDept(parentDept);
			}
		}else{ // 修改
			SysDeptEntity oldDept = sysDeptService.queryDeptBypDeptId(dept.getDeptId());
			dept.setChildren(oldDept.getChildren());
			sysDeptService.addOrUpdateDept(dept);
		}
	}

	@RequestMapping(value = "/deleteDept",method = RequestMethod.GET)
	public void deleteDept(HttpServletRequest request) {
		String parentDeptStr = request.getParameter("parentDept");
		System.out.println("====="+parentDeptStr);
		SysDeptEntity parentDept = JSONObject.parseObject(parentDeptStr,SysDeptEntity.class);
		SysDeptEntity oldParentDept = sysDeptService.queryDeptBypDeptId(parentDept.getDeptId());
		sysDeptService.deleteDept(oldParentDept);
	}
}
