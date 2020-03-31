package com.huadong.hdgate.laneManagement.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huadong.hdgate.common.filter.MyWebsocketHandler;
import com.huadong.hdgate.laneManagement.entity.GateLane;
import com.huadong.hdgate.laneManagement.mapper.GateLaneMapper;
import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import com.huadong.hdgate.systemManagement.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service("gateLaneService")
public class GateLaneService extends ServiceImpl<GateLaneMapper,GateLane> {

	@Resource
	private SysUserService sysUserService;

	/**
	 * 查询所有车道信息
	 * @return 车道信息
	 */
	public List<GateLane> queryAllGateLanes(){
		Wrapper<GateLane> wrapper = new EntityWrapper<>();
		return super.selectList(wrapper.orderBy("LANE_DB"));
	}

	/**
	 * 根据车道编号查询车道信息
	 * @return 车道信息
	 */
	public GateLane queryGateLanesByLaneCode(String laneCode){
		Wrapper<GateLane> wrapper = new EntityWrapper<>();
		wrapper.eq(GateLane.LANE_CODE,laneCode);
		return super.selectOne(wrapper);
	}

	/**
	 * 查询启用的车道信息（监控显示的车道信息）
	 * @return 车道信息
	 */
	public List<GateLane> queryShowGateLanes(String showFlag){
		Wrapper<GateLane> wrapper = new EntityWrapper<>();
		wrapper.eq(GateLane.SHOW_FLAG, showFlag).orderBy("LANE_DB");
		return super.selectList(wrapper);
	}

	/**
	 * 查询启用的车道信息（监控显示的车道信息）
	 * @return 结果
	 */
	public boolean changeGateLaneShowFlag(String laneCode,String showFlag){
		GateLane gateLane = queryGateLanesByLaneCode(laneCode);
		gateLane.setShowFlag(showFlag);
		return super.insertOrUpdate(gateLane);
	}

	/**
	 * 新增或修改车道信息
	 * @return 结果
	 */
	public boolean insertOrUpdateLane(String laneFormStr){
		GateLane lane = JSONObject.parseObject(laneFormStr,GateLane.class);
		return super.insertOrUpdate(lane);
	}

	/**
	 * 新增或修改车道信息
	 * @return 结果
	 */
	public boolean deleteLane(String laneFormStr){
		GateLane lane = JSONObject.parseObject(laneFormStr,GateLane.class);
		Wrapper<GateLane> wrapper = new EntityWrapper<>();
		wrapper.eq(GateLane.LANE_CODE,lane.getLaneCode());
		return super.delete(wrapper);
	}

	public void sendData2Html(String laneCode,String laneDataStr){
		List<SysUserEntity> userEntityList = sysUserService.queryAllUser();
		for(SysUserEntity user: userEntityList){
			try{
				MyWebsocketHandler.sendMessageToHtml("equipment:"+user.getAccount()+laneCode,laneDataStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
