package com.huadong.hdgate.laneManagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.huadong.hdgate.common.entity.CommonsEntity;
import com.huadong.hdgate.common.utils.LaneDBUtils;
import com.huadong.hdgate.common.utils.RedisUtils;
import com.huadong.hdgate.laneManagement.entity.ControlEntity;
import com.huadong.hdgate.laneManagement.entity.GateLane;
import com.huadong.hdgate.laneManagement.service.GateLaneService;
import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import com.huadong.hdgate.systemManagement.service.SysUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 车道管理controller
 */
@Slf4j
@RestController
@RequestMapping(value = "/laneManagement")
@Api(value="车道管理",tags={"车道信息，设备监控"})
public class LaneController {

	@Resource
	private GateLaneService gateLaneService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private RedisUtils redisUtils;
	@Resource
	private LaneDBUtils laneDBUtils;

	/**
	 * 获取所有车道信息
	 * @return 数据
	 */
	@ApiIgnore
	@RequestMapping(value = "/queryAllGateLanes",method = RequestMethod.GET)
	public List<GateLane> queryAllGateLanes() {
		// 查询所有车道信息
		List<GateLane> list = gateLaneService.queryAllGateLanes();
		//log.info("queryAllGateLanes:"+list);
		return list;
	}

	/**
	 * 查询用户选择的监控页面显示的车道
	 */
	@ApiIgnore
	@RequestMapping(value = "/queryGateLaneByUser",method = RequestMethod.GET)
	public String[] queryUserSelectedShowLane(HttpServletRequest request) {
		String account = request.getParameter("user");
		SysUserEntity user = sysUserService.queryUserByAccount(account);
		String checkedShowLaneStr = user.getCheckedShowLane();
		if(checkedShowLaneStr == null || "".equals(checkedShowLaneStr)){
			return null;
		}
		if(checkedShowLaneStr.contains(",")){
			return checkedShowLaneStr.split(",");
		}else {
			return new String[]{checkedShowLaneStr};
		}
	}

	/**
	 * 获取所有车道信息
	 * @return 数据
	 */
	@ApiIgnore
	@RequestMapping(value = "/queryShowGateLanes",method = RequestMethod.GET)
	public List<GateLane> queryShowGateLanes() {
		// 查询启用的车道（监控页面显示的车道）
		List<GateLane> list = gateLaneService.queryShowGateLanes(CommonsEntity.Y);
		//log.info("queryShowGateLanes:"+list);
		return list;
	}

	/**
	 * 更改车道禁用启用状态
	 * @return 成功或失败标志
	 */
	@ApiIgnore
	@RequestMapping(value = "/changeGateLaneShowFlag",method = RequestMethod.GET)
	public boolean changeGateLaneShowFlag(HttpServletRequest request) {
		String laneCode = request.getParameter("laneCode");
		String showFlag = request.getParameter("showFlag");
		boolean boo = gateLaneService.changeGateLaneShowFlag(laneCode,showFlag);
		log.info("queryShowGateLanes:"+boo);
		return boo;
	}

	/**
	 * 修改用户选择查看的车道
	 */
	@ApiIgnore
	@RequestMapping(value = "/changeUserCheckedShowLane",method = RequestMethod.GET)
	public void updateUserCheckedShowLane(HttpServletRequest request) {
		String account = request.getParameter("user");
		String laneCheckedList = request.getParameter("laneCheckedList");
		System.out.println("updateUserCheckedShowLane:"+account);
		System.out.println("laneCheckedList:"+laneCheckedList);
		SysUserEntity userEntity = sysUserService.queryUserByAccount(account);
		userEntity.setCheckedShowLane(laneCheckedList);
		sysUserService.insertOrUpdate(userEntity);
	}

	/**
	 * 更改车道禁用启用状态
	 * @return 成功或失败标志
	 */
	@ApiIgnore
	@RequestMapping(value = "/insertOrUpdateLane",method = RequestMethod.GET)
	public boolean insertOrUpdateLane(HttpServletRequest request) {
		String laneFormStr = request.getParameter("laneForm");
		return gateLaneService.insertOrUpdateLane(laneFormStr);
	}

	/**
	 * 更改车道禁用启用状态
	 * @return 成功或失败标志
	 */
	@ApiIgnore
	@RequestMapping(value = "/deleteLane",method = RequestMethod.GET)
	public boolean deleteLane(HttpServletRequest request) {
		String laneFormStr = request.getParameter("laneForm");
		return gateLaneService.deleteLane(laneFormStr);
	}

	@ApiIgnore
	@RequestMapping(value = "/lightOn",method = RequestMethod.GET)
	public boolean lightOn(HttpServletRequest request) {
		String laneCode = request.getParameter("laneCode");
		ControlEntity controlEntity = new ControlEntity();
		controlEntity.setLanecode(laneCode);
		controlEntity.setStation("light");
		controlEntity.setDetail("1");
		Long result = redisUtils.lpushQueue("control_data", JSONObject.toJSONString(controlEntity), laneDBUtils.getLaneDB(laneCode));
		if(result == null){
			return false;
		}
		return true;
	}

	@ApiIgnore
	@RequestMapping(value = "/lightOff",method = RequestMethod.GET)
	public boolean lightOff(HttpServletRequest request) {
		String laneCode = request.getParameter("laneCode");
		ControlEntity controlEntity = new ControlEntity();
		controlEntity.setLanecode(laneCode);
		controlEntity.setStation("light");
		controlEntity.setDetail("0");
		Long result = redisUtils.lpushQueue("control_data", JSONObject.toJSONString(controlEntity), laneDBUtils.getLaneDB(laneCode));
		if(result == null){
			return false;
		}
		return true;
	}

	@ApiIgnore
	@RequestMapping(value = "/liftRod",method = RequestMethod.GET)
	public boolean liftRod(HttpServletRequest request) {
		String laneCode = request.getParameter("laneCode");
		ControlEntity controlEntity = new ControlEntity();
		controlEntity.setLanecode(laneCode);
		controlEntity.setStation("lift");
		controlEntity.setDetail("1");
		Long result = redisUtils.lpushQueue("control_data", JSONObject.toJSONString(controlEntity), laneDBUtils.getLaneDB(laneCode));
		if(result == null){
			return false;
		}
		return true;
	}

	@ApiIgnore
	@RequestMapping(value = "/lightSet",method = RequestMethod.GET)
	public boolean lightSet(HttpServletRequest request){
		String lightOnTime = Integer.valueOf(request.getParameter("lightOnTime").substring(0,2)).toString();
		String lightOffTime = Integer.valueOf(request.getParameter("lightOffTime").substring(0,2)).toString();
		String laneCode = request.getParameter("laneCode");
		ControlEntity controlEntity = new ControlEntity();
		controlEntity.setTime_on(lightOnTime);
		controlEntity.setTime_off(lightOffTime);
		controlEntity.setLanecode(laneCode);
		controlEntity.setStation("light");
		controlEntity.setDetail("1");
		Long result = redisUtils.lpushQueue("control_data", JSONObject.toJSONString(controlEntity), laneDBUtils.getLaneDB(laneCode));
		System.out.println("result:"+result);
		if(result == null){
			return false;
		}
		return true;
	}

	@RequestMapping(value="/getLaneEquipmentStatus",method = RequestMethod.GET)
	public String getLaneEquipmentStatus(String laneCode){
		return redisUtils.get("receiveStatus"+laneCode);
	}

}
