package com.huadong.hdgate.systemManagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.huadong.hdgate.systemManagement.entity.SysResourceEntity;
import com.huadong.hdgate.systemManagement.service.SysService;
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
@RequestMapping(value = "/resourceController")
public class SysResourceController {

	private static final Logger logger = LoggerFactory.getLogger(SysResourceController.class);

	@Resource
	private SysService sysService;

	@RequestMapping(value = "/queryAllResources",method = RequestMethod.GET)
	public List<SysResourceEntity> queryAllResources() {
		return sysService.findAllResources();
	}

	@RequestMapping(value = "/addOrUpdateResource",method = RequestMethod.GET)
	public void addOrUpdateResource(HttpServletRequest request) {
		String resourceForm = request.getParameter("resourceForm");
		String state = request.getParameter("state");
		SysResourceEntity resourceEntity = JSONObject.parseObject(resourceForm,SysResourceEntity.class);
		if("true".equals(state)){
			resourceEntity.setState("open");
		}else{
			resourceEntity.setState("close");
		}
		sysService.addOrUpdateResource(resourceEntity);
	}


	/**
	 * 根据用户id查询用户的按钮的权限
	 * @param request 获取参数
	 * @return 返回值
	 */
	@RequestMapping(value = "/queryBtnsByUserId",method = RequestMethod.GET)
	public List<SysResourceEntity> queryBtnsByUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		return sysService.findUsersBtnsByUserId(userId);
	}
}
