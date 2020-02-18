package com.huadong.hdgate.systemManagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.huadong.hdgate.common.filter.MyWebsocketHandler;
import com.huadong.hdgate.common.utils.CommonUtils;
import com.huadong.hdgate.common.utils.MyCacheUtils;
import com.huadong.hdgate.common.utils.RedisUtils;
import com.huadong.hdgate.common.utils.webapi.HttpsUtils;
import com.huadong.hdgate.laneManagement.entity.EquipmentStatusEntity;
import com.huadong.hdgate.laneManagement.entity.GateLane;
import com.huadong.hdgate.laneManagement.service.GateLaneService;
import com.huadong.hdgate.systemManagement.entity.SysResourceEntity;
import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import com.huadong.hdgate.systemManagement.service.SysService;
import com.huadong.hdgate.systemManagement.service.SysUserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统操作
 */
@RestController
@RequestMapping(value = "/sys")
@Api(value="车道设备监控",tags={"车道设备监控页面"})
public class SysController {

	private static final Logger logger = LoggerFactory.getLogger(SysController.class);

	// session 登录后存储的属性。
	public static final String SESSION_USER = "user";
	@Resource
	private SysService sysService;
	@Resource
	private SysUserService sysUserService;

	@ApiIgnore
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public SysUserEntity login(String username, String password, HttpServletRequest request) {
		SysUserEntity user = new SysUserEntity();
		user = init(sysUserService.doLogin(username, password));// 访问集控的数据库登录，默认test用户访问数据库
		String msg = user.getUserState();
		if(!msg.contains("error:")){ // 不包含error说明登录成功
			String ip = CommonUtils.getIpAddr(request);
			logger.info("用户：" + user + "登录成功。" + ip);
			// 登录成功添加到缓存中
			Map<String,SysUserEntity> userMap = new HashMap<>();
			Object userObj = MyCacheUtils.get("users");
			if(userObj!=null){
				userMap = (Map) userObj;
			}
			// 赋值用来判断是否推送数据的，如果当前时间与在此赋值时间大于2填，则不给该用户推送
			user.setAccountExpirationDate(new Timestamp(System.currentTimeMillis()));
			userMap.put(user.getAccount(),user);
			MyCacheUtils.put("users",userMap,0);//0为永不失效 ,存入缓存

			// 登录成功新增或修改集控数据库的该用户信息。
			sysUserService.saveOrUpdateUser(user);
			// 根据用户获取按钮信息,并赋值
			String userId = user.getUserId();
			List<SysResourceEntity> btns = sysService.findUsersBtnsByUserId(userId);
			user.setBtnsList(btns);
			//dashboardService.addSysLog(user.getUserName(),ip,"信息",new Timestamp(new Date().getTime()),username+"登录成功");

			WebSocketSession webSocketSession = MyWebsocketHandler.socketSessionMap.get("user:"+user.getAccount());
			System.out.println("webSocketSession:"+webSocketSession);
			try {
				if(webSocketSession!=null){
					webSocketSession.sendMessage(new TextMessage("kickout"));
					MyWebsocketHandler.sendMessageToHtml("user:"+user.getAccount(),"kickout");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	@ApiIgnore
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public void logout(HttpServletRequest request){
		String username = request.getParameter("username");
		System.out.println("username:"+username);
		SysUserEntity user = sysUserService.queryUserByAccount(username);
		String ip = CommonUtils.getIpAddr(request);
		//dashboardService.addSysLog(user.getUserName(),ip,"信息",new Timestamp(new Date().getTime()),username+"登出成功");
		//使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		//SecurityUtils.getSubject().logout();
	/*	Element element = new Element("loginId"+ CommonUtils.toUpperFristChar(username),null);
		//redirectAttributes.addFlashAttribute("msg", "您已安全退出");
		return "redirect:/login";*/
	}

	@ApiIgnore
	@RequestMapping(value = "/findUsersMenu",method = RequestMethod.GET)
	public List<SysResourceEntity> findUsersMenu(HttpServletRequest request) {
		String username = request.getParameter("username");
		System.out.println("findUsersMenu:"+username);
		SysUserEntity user = sysUserService.queryUserByAccount(username);
		List<SysResourceEntity> list = sysService.findUsersMenu(user);
		logger.info("用户：" + user + ",获取到的菜单:" + list);
		return list;
	}

	@ApiIgnore
	@RequestMapping(value = "/findAllMenusTree",method = RequestMethod.GET)
	public List<SysResourceEntity> findAllMenusTree() {
		List<SysResourceEntity> dbMenus = sysService.findAllMenus();
		return sysService.getMennus2List(dbMenus);
	}

	private SysUserEntity init(SysUserEntity user) {
		//查询角色id，赋值到user中
		if(user.getUserState() == null || (!user.getUserState().contains("error:")) ){
			user.setRoleIdLs(sysService.findRoleIdsByUserId(user.getUserId()));
			user.setAdmin(sysService.isAdmin(user.getUserId()));
		}
		return user;
	}

	@ApiIgnore
	@RequestMapping(value = "/updateRoleMenus",method = RequestMethod.GET)
	public void updateRoleMenus(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String permIdsStr = request.getParameter("permIds");
		//System.out.println("获取的角色id:"+roleId);
		//System.out.println("获取的权限id："+permIdsStr);
		sysService.updateRoleMenus(roleId,permIdsStr);
	}

	/**
	 * 根据角色查询页面中的按钮
	 * @param request 参数
	 * @return 按钮
	 */
	@ApiIgnore
	@RequestMapping(value = "/findBtnsByRoleAndMenuId",method = RequestMethod.GET)
	public List<SysResourceEntity> findBtnsByRoleAndMenuId(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String menuId = request.getParameter("menuId");
		return sysService.findBtnsByRoleAndMenuId(roleId,menuId);
	}

	/**
	 * 不根据角色查询页面中的按钮
	 * @param request 参数
	 * @return 按钮
	 */
	@ApiIgnore
	@RequestMapping(value = "/findAllBtnsByMenuId",method = RequestMethod.GET)
	public List<SysResourceEntity> findAllBtnsByMenuId(HttpServletRequest request) {
		String menuId = request.getParameter("menuId");
		return sysService.findAllBtnsByMenuId(menuId);
	}

	/**
	 * 修改角色的按钮权限
	 * @param request 参数
	 */
	@ApiIgnore
	@RequestMapping(value = "/changeRoleBtn",method = RequestMethod.GET)
	public void changeRoleBtn(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String btnId = request.getParameter("btnId");
		String flag = request.getParameter("flag");
		if("true".equals(flag)){
			sysService.addRoleBtn(roleId,btnId);
		}else{
			sysService.deleteRoleBtn(roleId,btnId);
		}
	}

	@Resource
	private GateLaneService gateLaneService;
	@Resource
	private RedisUtils redisUtils;
	/**
	 * 接收设备状态信息
	 * @param data 车道所有设备状态信息
	 */
	@RequestMapping(value="/equipmentStatus",method=RequestMethod.POST)
	public void equipmentStatus(@RequestBody String data){
		logger.info("收到的设备状态数据内容:" + data);
		List<GateLane> list = gateLaneService.queryAllGateLanes();
		for (GateLane lane:list){
			if (data.contains(lane.getLaneCode())){ // 收到数据包含某一个车道，说明是某车道数据
				String laneCode = lane.getLaneCode();
				long oldTime = Timestamp.valueOf(redisUtils.get("receiveMsgTime"+laneCode)).getTime();
				long curTime = System.currentTimeMillis();
				String curTimeStr = new Timestamp(curTime).toString();
				long subTime = curTime - oldTime;
				// logger.info("上次时间与本次时间间隔："+subTime);
				redisUtils.set("receiveMsgTime"+laneCode, curTimeStr);
				// 解析收到数据data，判断某个设备是否异常，将异常反馈前端页面
				String oldData = redisUtils.get("receiveStatus"+laneCode);
				if (!data.equals(oldData)){
					// 判断一致不推送数据，不一致推送数据
					sendUsersWailiMsg(data);
				}
			}
		}
	}

	/**
	 * 对用户推送错误系统错误信息
	 * @param data
	 */
	@RequestMapping(value="/showErrorMsg",method=RequestMethod.POST)
	public void showErroeMsg(@RequestBody String data){
		sendUsersWailiMsg(data);
	}

	/**
	 * 从缓存中获取用户数据，然后对用户推送系统错误信息
	 * @param str 要推送的数据
	 */
	public void sendUsersWailiMsg(String str){
		Object userObj = MyCacheUtils.get("users");
		if(userObj!=null){
			Map<String,SysUserEntity> userMap = (Map) userObj;
			for (String key : userMap.keySet()) {
				try {
					MyWebsocketHandler.sendMessageToHtml("user:" + key,str);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
