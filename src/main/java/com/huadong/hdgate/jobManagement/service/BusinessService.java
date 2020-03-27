package com.huadong.hdgate.jobManagement.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huadong.hdgate.common.filter.MyWebsocketHandler;
import com.huadong.hdgate.common.utils.DateUtils;
import com.huadong.hdgate.common.utils.LaneDBUtils;
import com.huadong.hdgate.common.utils.RedisUtils;
import com.huadong.hdgate.common.utils.webapi.HttpsUtils;
import com.huadong.hdgate.jobManagement.entity.StatisticsRationEntity;
import com.huadong.hdgate.jobManagement.entity.XijingParamsEntity;
import com.huadong.hdgate.jobManagement.entity.cdiEntity.BusinessEntity;
import com.huadong.hdgate.jobManagement.mapper.BusinessMapper;
import com.huadong.hdgate.systemManagement.entity.SysUserEntity;
import com.huadong.hdgate.systemManagement.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service("businessService")
public class BusinessService extends ServiceImpl<BusinessMapper,BusinessEntity> {
	@Resource
	private BusinessMapper businessMapper;
	@Resource
	private RedisUtils redisUtils;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private LaneDBUtils laneDBUtils;

	public boolean insertOrUpdateBusinessData(String businessDataStr){
		BusinessEntity businessEntity = JSONObject.parseObject(businessDataStr,BusinessEntity.class);
		redisUtils.setex(businessEntity.getGeneralInfo().getLaneCode(),3600*5,businessDataStr);// 每次接收到数据存储到redis中，保证redis中对应每条车道都是最新数据
		log.info("laneMonitorApi接收到数据：{},\n转换对象后{}",businessDataStr,businessEntity);

		String redisData = redisUtils.get(businessEntity.getVisitGuid());
		if(redisData == null || "".equals(redisData)){
			businessMapper.insertBusiness(
					businessEntity.getVisitGuid(),
					businessEntity.getStation(),
					businessEntity.getMsg(),
					businessEntity.getArriveTime(),
					businessEntity.getEnterTime(),
					businessEntity.getGeneralInfo().getLaneCode(),
					businessEntity.getGeneralInfo().getRfid(),
					businessEntity.getGeneralInfo().getWeight(),
					businessEntity.getGeneralInfo().getCntrSize(),
					businessEntity.getOcrCarPlate().getOcrPlate(),
					businessEntity.getOcrCarPlate().getPlateColor(),
					businessEntity.getOcrFrontContainer().getOcrContainerNo(),
					businessEntity.getOcrFrontContainer().getOcrContainerConf(),
					businessEntity.getOcrFrontContainer().getOcrContainerDirection(),
					businessEntity.getOcrFrontContainer().getOcrContainerISO(),
					businessEntity.getOcrFrontContainer().getOcrIsoConf(),
					businessEntity.getOcrFrontContainer().getOcrDamage(),
					businessEntity.getOcrFrontContainer().getEfid(),
					businessEntity.getOcrAfterContainer().getOcrContainerNo(),
					businessEntity.getOcrAfterContainer().getOcrContainerConf(),
					businessEntity.getOcrAfterContainer().getOcrContainerDirection(),
					businessEntity.getOcrAfterContainer().getOcrContainerISO(),
					businessEntity.getOcrAfterContainer().getOcrIsoConf(),
					businessEntity.getOcrAfterContainer().getOcrDamage(),
					businessEntity.getOcrAfterContainer().getEfid(),
					businessEntity.getFtpImages().getFolder(),
					businessEntity.getFtpImages().getImagePath(),
					businessEntity.getFtpImages().getImageName().toString(),
					businessEntity.getOcrFrontContainer().getLeadSealState(),
					businessEntity.getOcrFrontContainer().getLeadSealNo(),
					businessEntity.getOcrFrontContainer().getProperty(),
					businessEntity.getOcrAfterContainer().getLeadSealState(),
					businessEntity.getOcrAfterContainer().getLeadSealNo(),
					businessEntity.getOcrAfterContainer().getProperty()
			);
			redisUtils.setex(businessEntity.getVisitGuid(),3600*5,businessDataStr);
		}else{
			businessMapper.updateBusiness(
					businessEntity.getVisitGuid(),
					businessEntity.getStation(),
					businessEntity.getMsg(),
					businessEntity.getArriveTime(),
					businessEntity.getEnterTime(),
					businessEntity.getGeneralInfo().getLaneCode(),
					businessEntity.getGeneralInfo().getRfid(),
					businessEntity.getGeneralInfo().getCntrSize(),
					businessEntity.getOcrCarPlate().getOcrPlate(),
					businessEntity.getOcrCarPlate().getPlateColor(),
					businessEntity.getOcrFrontContainer().getOcrContainerNo(),
					businessEntity.getOcrFrontContainer().getOcrContainerConf(),
					businessEntity.getOcrFrontContainer().getOcrContainerDirection(),
					businessEntity.getOcrFrontContainer().getOcrContainerISO(),
					businessEntity.getOcrFrontContainer().getOcrIsoConf(),
					businessEntity.getOcrFrontContainer().getOcrDamage(),
					businessEntity.getOcrFrontContainer().getEfid(),
					businessEntity.getOcrAfterContainer().getOcrContainerNo(),
					businessEntity.getOcrAfterContainer().getOcrContainerConf(),
					businessEntity.getOcrAfterContainer().getOcrContainerDirection(),
					businessEntity.getOcrAfterContainer().getOcrContainerISO(),
					businessEntity.getOcrAfterContainer().getOcrIsoConf(),
					businessEntity.getOcrAfterContainer().getOcrDamage(),
					businessEntity.getOcrAfterContainer().getEfid(),
					businessEntity.getFtpImages().getFolder(),
					businessEntity.getFtpImages().getImagePath(),
					businessEntity.getFtpImages().getImageName().toString(),
					businessEntity.getCarPlate().getOcrPlate(),
					businessEntity.getFrontContainer().getOcrContainerNo(),
					businessEntity.getAfterContainer().getOcrContainerNo(),
					businessEntity.getGeneralInfo().getWeight(),
					businessEntity.getOcrFrontContainer().getLeadSealState(),
					businessEntity.getOcrFrontContainer().getLeadSealNo(),
					businessEntity.getOcrFrontContainer().getProperty(),
					businessEntity.getOcrAfterContainer().getLeadSealState(),
					businessEntity.getOcrAfterContainer().getLeadSealNo(),
					businessEntity.getOcrAfterContainer().getProperty()
			);
		}
		return true;
	}

	/**
	 * 获取所有用户，循环用户全部用户推送最新数据
	 * @param businessDataStr 最新数据
	 */
	public void sendData2Html(String laneCode,String businessDataStr){
		List<SysUserEntity> userList = sysUserService.queryAllUser();
		for (SysUserEntity user : userList){
			try {
				MyWebsocketHandler.sendMessageToHtml("monitor:"+user.getAccount()+laneCode,businessDataStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public BusinessEntity queryBusinessDataByLaneCode(String laneCode){
		String dataStr = redisUtils.get(laneCode);
		//log.info("车道{}获取最新数据：{}",laneCode,dataStr);
		return JSONObject.parseObject(dataStr,BusinessEntity.class);
	}

	@Autowired
	private XijingParamsEntity xijingParamsEntity;

	/**
	 * 将收到数据进行处理，存储到redis中，然后反馈最新数据的字符串
	 * @param laneCode 车道编号
	 * @param account 修改人
	 * @param todoUpdateDataStr 操作员修改过后的数据
	 * @return 处理后的数据
	 */
	public String updateBusinessData(String laneCode,String account,String todoUpdateDataStr){
		BusinessEntity newBusinessData = JSONObject.parseObject(todoUpdateDataStr,BusinessEntity.class);
		BusinessEntity oldBusinessData = queryBusinessDataByLaneCode(laneCode);
		// 修改人赋值
		oldBusinessData.setAccount(account);
		// 重量赋值
		oldBusinessData.getGeneralInfo().setWeight(newBusinessData.getGeneralInfo().getWeight());
		// 集卡号赋值
		if(newBusinessData.getCarPlate().getOcrPlate() != null && !newBusinessData.getCarPlate().getOcrPlate().equals("")){
			oldBusinessData.getCarPlate().setOcrPlate(newBusinessData.getCarPlate().getOcrPlate());
		}else{
			if(newBusinessData.getOcrCarPlate().getOcrPlate() != null && !newBusinessData.getOcrCarPlate().getOcrPlate().equals(oldBusinessData.getOcrCarPlate().getOcrPlate())){
				oldBusinessData.getCarPlate().setOcrPlate(newBusinessData.getOcrCarPlate().getOcrPlate());
			}
		}
		// 前箱号赋值
		if(newBusinessData.getFrontContainer().getOcrContainerNo() != null && !newBusinessData.getFrontContainer().getOcrContainerNo().equals("")){
			oldBusinessData.getFrontContainer().setOcrContainerNo(newBusinessData.getFrontContainer().getOcrContainerNo().toUpperCase());
		}else{
			if(newBusinessData.getOcrFrontContainer().getOcrContainerNo() != null && !newBusinessData.getOcrFrontContainer().getOcrContainerNo().equals(oldBusinessData.getOcrFrontContainer().getOcrContainerNo())){
				oldBusinessData.getFrontContainer().setOcrContainerNo(newBusinessData.getOcrFrontContainer().getOcrContainerNo().toUpperCase());
			}
		}
		// 前箱型赋值
		if(newBusinessData.getFrontContainer().getOcrContainerISO() != null && !newBusinessData.getFrontContainer().getOcrContainerISO().equals("")){
			oldBusinessData.getFrontContainer().setOcrContainerISO(newBusinessData.getFrontContainer().getOcrContainerISO().toUpperCase());
		}else{
			if(newBusinessData.getOcrFrontContainer().getOcrContainerISO() != null && !newBusinessData.getOcrFrontContainer().getOcrContainerISO().equals(oldBusinessData.getOcrFrontContainer().getOcrContainerISO())){
				oldBusinessData.getFrontContainer().setOcrContainerISO(newBusinessData.getOcrFrontContainer().getOcrContainerISO().toUpperCase());
			}
		}
		// 前箱型空重
		if(newBusinessData.getFrontContainer().getEfid() != null && !newBusinessData.getFrontContainer().getEfid().equals("")){
			oldBusinessData.getFrontContainer().setEfid(newBusinessData.getFrontContainer().getEfid().toUpperCase());
		}else{
			// 再次判断，如果值没有变化不需要更新，有变化说明修改过
			if(newBusinessData.getOcrFrontContainer().getEfid() != null && !newBusinessData.getOcrFrontContainer().getEfid().equals(oldBusinessData.getOcrFrontContainer().getEfid())){
				oldBusinessData.getOcrFrontContainer().setEfid(newBusinessData.getOcrFrontContainer().getEfid().toUpperCase());
			}
		}
		//前箱铅封状态
		if(newBusinessData.getFrontContainer().getLeadSealState() != null && !newBusinessData.getFrontContainer().getLeadSealState().equals("")){
			oldBusinessData.getFrontContainer().setLeadSealState(newBusinessData.getFrontContainer().getLeadSealState());
		}else{
			if(newBusinessData.getOcrFrontContainer().getLeadSealState() != null && !newBusinessData.getOcrFrontContainer().getLeadSealState().equals(oldBusinessData.getOcrFrontContainer().getLeadSealState())){
				oldBusinessData.getOcrFrontContainer().setLeadSealState(newBusinessData.getOcrFrontContainer().getLeadSealState());
			}
		}
		//前箱铅封号
		if(newBusinessData.getFrontContainer().getLeadSealNo() != null && !newBusinessData.getFrontContainer().getLeadSealNo().equals("")){
			oldBusinessData.getFrontContainer().setLeadSealNo(newBusinessData.getFrontContainer().getLeadSealNo());
		}else{
			if(newBusinessData.getOcrFrontContainer().getLeadSealNo() != null && !newBusinessData.getOcrFrontContainer().getLeadSealNo().equals(oldBusinessData.getOcrFrontContainer().getLeadSealNo())){
				oldBusinessData.getOcrFrontContainer().setLeadSealNo(newBusinessData.getOcrFrontContainer().getLeadSealNo());
			}
		}
		//前箱箱属
		if(newBusinessData.getFrontContainer().getProperty() != null && !newBusinessData.getFrontContainer().getProperty().equals("")){
			oldBusinessData.getFrontContainer().setProperty(newBusinessData.getFrontContainer().getProperty());
		}else{
			if(newBusinessData.getOcrFrontContainer().getProperty() != null && !newBusinessData.getOcrFrontContainer().getProperty().equals(oldBusinessData.getOcrFrontContainer().getProperty())){
				oldBusinessData.getOcrFrontContainer().setProperty(newBusinessData.getOcrFrontContainer().getProperty());
			}
		}
		//前箱残损
		if(newBusinessData.getFrontContainer().getOcrDamage() != null && !newBusinessData.getFrontContainer().getOcrDamage().equals("")){
			oldBusinessData.getFrontContainer().setOcrDamage(newBusinessData.getFrontContainer().getOcrDamage());
		}else{
			if(newBusinessData.getOcrFrontContainer().getOcrDamage() != null && !newBusinessData.getOcrFrontContainer().getOcrDamage().equals(oldBusinessData.getOcrFrontContainer().getOcrDamage())){
				oldBusinessData.getOcrFrontContainer().setOcrDamage(newBusinessData.getOcrFrontContainer().getOcrDamage());
			}
		}
		// 后箱号赋值
		if(newBusinessData.getAfterContainer().getOcrContainerNo() != null && !newBusinessData.getAfterContainer().getOcrContainerNo().equals("")){
			oldBusinessData.getAfterContainer().setOcrContainerNo(newBusinessData.getAfterContainer().getOcrContainerNo().toUpperCase());
		}else{
			// 再次判断，如果值没有变化不需要更新，有变化说明修改过
			if(newBusinessData.getOcrAfterContainer().getOcrContainerNo() != null && !newBusinessData.getOcrAfterContainer().getOcrContainerNo().equals(oldBusinessData.getOcrAfterContainer().getOcrContainerNo())){
				oldBusinessData.getAfterContainer().setOcrContainerNo(newBusinessData.getOcrAfterContainer().getOcrContainerNo().toUpperCase());
			}
		}
		// 后箱型赋值
		if(newBusinessData.getAfterContainer().getOcrContainerISO() != null && !newBusinessData.getAfterContainer().getOcrContainerISO().equals("")){
			oldBusinessData.getAfterContainer().setOcrContainerISO(newBusinessData.getAfterContainer().getOcrContainerISO().toUpperCase());
		}else{
			// 再次判断，如果值没有变化不需要更新，有变化说明修改过
			if(newBusinessData.getOcrAfterContainer().getOcrContainerISO() != null && !newBusinessData.getOcrAfterContainer().getOcrContainerISO().equals(oldBusinessData.getOcrAfterContainer().getOcrContainerISO())){
				oldBusinessData.getAfterContainer().setOcrContainerISO(newBusinessData.getOcrAfterContainer().getOcrContainerISO().toUpperCase());
			}
		}
		// 后箱空重
		if(newBusinessData.getAfterContainer().getEfid() != null && !newBusinessData.getAfterContainer().getEfid().equals("")){
			oldBusinessData.getAfterContainer().setEfid(newBusinessData.getAfterContainer().getEfid().toUpperCase());
		}else{
			// 再次判断，如果值没有变化不需要更新，有变化说明修改过
			if(newBusinessData.getOcrAfterContainer().getEfid() != null && !newBusinessData.getOcrAfterContainer().getEfid().equals(oldBusinessData.getOcrAfterContainer().getEfid())){
				oldBusinessData.getOcrAfterContainer().setEfid(newBusinessData.getOcrAfterContainer().getEfid().toUpperCase());
			}
		}
		//后箱铅封状态
		if(newBusinessData.getAfterContainer().getLeadSealState() != null && !newBusinessData.getAfterContainer().getLeadSealState().equals("")){
			oldBusinessData.getAfterContainer().setLeadSealState(newBusinessData.getAfterContainer().getLeadSealState());
		}else{
			if(newBusinessData.getOcrAfterContainer().getLeadSealState() != null && !newBusinessData.getOcrAfterContainer().getLeadSealState().equals(oldBusinessData.getOcrAfterContainer().getLeadSealState())){
				oldBusinessData.getOcrAfterContainer().setLeadSealState(newBusinessData.getOcrAfterContainer().getLeadSealState());
			}
		}
		//后箱铅封号
		if(newBusinessData.getAfterContainer().getLeadSealNo() != null && !newBusinessData.getAfterContainer().getLeadSealNo().equals("")){
			oldBusinessData.getAfterContainer().setLeadSealNo(newBusinessData.getAfterContainer().getLeadSealNo());
		}else{
			if(newBusinessData.getOcrAfterContainer().getLeadSealNo() != null && !newBusinessData.getOcrAfterContainer().getLeadSealNo().equals(oldBusinessData.getOcrAfterContainer().getLeadSealNo())){
				oldBusinessData.getOcrAfterContainer().setLeadSealNo(newBusinessData.getOcrAfterContainer().getLeadSealNo());
			}
		}
		//后箱箱属
		if(newBusinessData.getAfterContainer().getProperty() != null && !newBusinessData.getAfterContainer().getProperty().equals("")){
			oldBusinessData.getAfterContainer().setProperty(newBusinessData.getAfterContainer().getProperty());
		}else{
			if(newBusinessData.getOcrAfterContainer().getProperty() != null && !newBusinessData.getOcrAfterContainer().getProperty().equals(oldBusinessData.getOcrAfterContainer().getProperty())){
				oldBusinessData.getOcrAfterContainer().setProperty(newBusinessData.getOcrAfterContainer().getProperty());
			}
		}
		//后箱残损
		if(newBusinessData.getAfterContainer().getOcrDamage() != null && !newBusinessData.getAfterContainer().getOcrDamage().equals("")){
			oldBusinessData.getAfterContainer().setOcrDamage(newBusinessData.getAfterContainer().getOcrDamage());
		}else{
			if(newBusinessData.getOcrAfterContainer().getOcrDamage() != null && !newBusinessData.getOcrAfterContainer().getOcrDamage().equals(oldBusinessData.getOcrAfterContainer().getOcrDamage())){
				oldBusinessData.getOcrAfterContainer().setOcrDamage(newBusinessData.getOcrAfterContainer().getOcrDamage());
			}
		}
		String finalBusinessDataStr = JSONObject.toJSONString(oldBusinessData);
		System.out.println("finalBStr:"+finalBusinessDataStr);
		redisUtils.set(laneCode,finalBusinessDataStr);

		redisUtils.lpushQueue("ocr_data",BusinessDataToJson(oldBusinessData,laneCode), laneDBUtils.getLaneDB(laneCode));

		return finalBusinessDataStr;
	}

	public void updateMessage(String uuid,String msg){
		businessMapper.updateMessage(uuid,msg);
	}
	private String BusinessDataToJson(BusinessEntity oldBusinessData, String laneCode){
		StringBuffer redisData = new StringBuffer();
		redisData.append("{\"lanecode\": \"");
		redisData.append(laneCode);
		redisData.append("\", \"uuid\": \"");
		redisData.append(oldBusinessData.getVisitGuid());
		redisData.append("\", \"station\": \"");
		redisData.append("web");
		redisData.append("\", \"message\": \"");
		redisData.append(oldBusinessData.getMsg());
		redisData.append("\", \"starttime\": \"");
		redisData.append(oldBusinessData.getArriveTime());
		redisData.append("\", \"endtime\": \"");
		redisData.append(oldBusinessData.getEnterTime());
		redisData.append("\", \"containersize\": \"");
		redisData.append(oldBusinessData.getGeneralInfo().getCntrSize());
		redisData.append("\", \"weight\": \"");
		redisData.append(oldBusinessData.getGeneralInfo().getWeight());
		redisData.append("\", \"lorry\": { \"Plate\": \"");
		if(oldBusinessData.getCarPlate().getOcrPlate() == null || oldBusinessData.getCarPlate().getOcrPlate().isEmpty()){
			redisData.append(oldBusinessData.getOcrCarPlate().getOcrPlate());
		}else{
			redisData.append(oldBusinessData.getCarPlate().getOcrPlate());
		}
		redisData.append("\", \"Color\": \"");
		if(oldBusinessData.getCarPlate().getPlateColor() == null || oldBusinessData.getCarPlate().getPlateColor().isEmpty()){
			redisData.append(oldBusinessData.getOcrCarPlate().getPlateColor());
		}else{
			redisData.append(oldBusinessData.getCarPlate().getPlateColor());
		}
		redisData.append("\"}, \"containerahead\": { \"number\": \"");
		if(oldBusinessData.getFrontContainer().getOcrContainerNo() == null || oldBusinessData.getFrontContainer().getOcrContainerNo().isEmpty()){
			redisData.append(oldBusinessData.getOcrFrontContainer().getOcrContainerNo());
		}else{
			redisData.append(oldBusinessData.getFrontContainer().getOcrContainerNo());
		}
		redisData.append("\", \"iso\": \"");
		if(oldBusinessData.getFrontContainer().getOcrContainerISO() == null || oldBusinessData.getFrontContainer().getOcrContainerISO().isEmpty()){
			redisData.append(oldBusinessData.getOcrFrontContainer().getOcrContainerISO());
		}else{
			redisData.append(oldBusinessData.getFrontContainer().getOcrContainerISO());
		}
		redisData.append("\", \"direction\": \"");
		if(oldBusinessData.getFrontContainer().getOcrContainerDirection() ==null || oldBusinessData.getFrontContainer().getOcrContainerDirection().isEmpty()){
			redisData.append(oldBusinessData.getOcrFrontContainer().getOcrContainerDirection());
		}else{
			redisData.append(oldBusinessData.getFrontContainer().getOcrContainerDirection());
		}
		redisData.append("\", \"leadsealstate\": \"");
		if(oldBusinessData.getFrontContainer().getLeadSealState() == null || oldBusinessData.getFrontContainer().getLeadSealState().isEmpty()){
			redisData.append(oldBusinessData.getOcrFrontContainer().getLeadSealState());
		}else{
			redisData.append(oldBusinessData.getFrontContainer().getLeadSealState());
		}
		redisData.append("\", \"leadsealno\": \"");
		if(oldBusinessData.getFrontContainer().getLeadSealNo() == null || oldBusinessData.getFrontContainer().getLeadSealNo().isEmpty()){
			redisData.append(oldBusinessData.getOcrFrontContainer().getLeadSealNo());
		}else{
			redisData.append(oldBusinessData.getFrontContainer().getLeadSealNo());
		}
		redisData.append("\", \"damage_code\": \"");
		if(oldBusinessData.getFrontContainer().getOcrDamage() == null || oldBusinessData.getFrontContainer().getOcrDamage().isEmpty()){
			redisData.append(oldBusinessData.getOcrFrontContainer().getOcrDamage());
		}else{
			redisData.append(oldBusinessData.getFrontContainer().getOcrDamage());
		}
		redisData.append("\", \"property\": \"");
		if(oldBusinessData.getFrontContainer().getProperty() == null || oldBusinessData.getFrontContainer().getProperty().isEmpty()){
			redisData.append(oldBusinessData.getOcrFrontContainer().getProperty());
		}else{
			redisData.append(oldBusinessData.getFrontContainer().getProperty());
		}
		redisData.append("\", \"efid\": \"");
		if(oldBusinessData.getFrontContainer().getEfid() == null || oldBusinessData.getFrontContainer().getEfid().isEmpty()){
			redisData.append(oldBusinessData.getOcrFrontContainer().getEfid());
		}else{
			redisData.append(oldBusinessData.getFrontContainer().getEfid());
		}
		redisData.append("\"}, \"containerbehind\": { \"number\": \"");
		if(oldBusinessData.getAfterContainer().getOcrContainerNo() == null || oldBusinessData.getAfterContainer().getOcrContainerNo().isEmpty()){
			redisData.append(oldBusinessData.getOcrAfterContainer().getOcrContainerNo());
		}else{
			redisData.append(oldBusinessData.getAfterContainer().getOcrContainerNo());
		}
		redisData.append("\", \"iso\": \"");
		if(oldBusinessData.getAfterContainer().getOcrContainerISO() == null || oldBusinessData.getAfterContainer().getOcrContainerISO().isEmpty()){
			redisData.append(oldBusinessData.getOcrAfterContainer().getOcrContainerISO());
		}else{
			redisData.append(oldBusinessData.getAfterContainer().getOcrContainerISO());
		}
		redisData.append("\", \"direction\": \"");
		if(oldBusinessData.getAfterContainer().getOcrContainerDirection() == null || oldBusinessData.getAfterContainer().getOcrContainerDirection().isEmpty()){
			redisData.append(oldBusinessData.getOcrAfterContainer().getOcrContainerDirection());
		}else{
			redisData.append(oldBusinessData.getAfterContainer().getOcrContainerDirection());
		}
		redisData.append("\", \"leadsealstate\": \"");
		if(oldBusinessData.getAfterContainer().getLeadSealState() == null || oldBusinessData.getAfterContainer().getLeadSealState().isEmpty()){
			redisData.append(oldBusinessData.getOcrAfterContainer().getLeadSealState());
		}else{
			redisData.append(oldBusinessData.getAfterContainer().getLeadSealState());
		}
		redisData.append("\", \"leadsealno\": \"");
		if(oldBusinessData.getAfterContainer().getLeadSealNo() == null || oldBusinessData.getAfterContainer().getLeadSealNo().isEmpty()){
			redisData.append(oldBusinessData.getOcrAfterContainer().getLeadSealNo());
		}else{
			redisData.append(oldBusinessData.getAfterContainer().getLeadSealNo());
		}
		redisData.append("\", \"damage_code\": \"");
		if(oldBusinessData.getAfterContainer().getOcrDamage() == null || oldBusinessData.getAfterContainer().getOcrDamage().isEmpty()){
			redisData.append(oldBusinessData.getOcrAfterContainer().getOcrDamage());
		}else{
			redisData.append(oldBusinessData.getAfterContainer().getOcrDamage());
		}
		redisData.append("\", \"property\": \"");
		if(oldBusinessData.getAfterContainer().getProperty() == null || oldBusinessData.getAfterContainer().getProperty().isEmpty()){
			redisData.append(oldBusinessData.getOcrAfterContainer().getProperty());
		}else{
			redisData.append(oldBusinessData.getAfterContainer().getProperty());
		}
		redisData.append("\", \"efid\": \"");
		if(oldBusinessData.getAfterContainer().getEfid() == null || oldBusinessData.getAfterContainer().getEfid().isEmpty()){
			redisData.append(oldBusinessData.getOcrAfterContainer().getEfid());
		}else{
			redisData.append(oldBusinessData.getAfterContainer().getEfid());
		}
		redisData.append("\"}, \"images\": {");
		for(String s:oldBusinessData.getFtpImages().getImageName().split(",")){
			redisData.append("\"")
					.append(s, s.lastIndexOf("/")+1, s.length()-4)
					.append("\": \"").append(s).append("\",");
		}
		redisData.deleteCharAt(redisData.length()-1);
		redisData.append("} }");
		System.out.println("redisData:"+redisData.toString());
		return redisData.toString();
	}

	// 分页查询
	public PageInfo<BusinessEntity> queryHistoryDataByParam(String laneCode,String truckNo,String cntrNo,String pageNumStr,String pageSizeStr,
															String beginTime,String endTime){
		int pageNum = Integer.parseInt(pageNumStr);
		int pageSize = Integer.parseInt(pageSizeStr);
		List<BusinessEntity> list = new ArrayList<>();
		PageHelper.startPage(pageNum,pageSize); //pageNum：当前页数   pageSize：当前页需要显示的数量
		list = businessMapper.queryHistoryDataByParam(laneCode,truckNo,cntrNo, Timestamp.valueOf(beginTime),Timestamp.valueOf(endTime));
		PageInfo<BusinessEntity> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	// 不分页(最近一周数据平均过车时间)
	public long queryHistoryDataAverageTime(){
		int dayOfWeek = DateUtils.getDayOfWork(new Date());
		Timestamp currentTime = DateUtils.getSimpleCurTimestampDay();
		Timestamp beginTime = DateUtils.addDay(currentTime,-dayOfWeek+1-7);
		Timestamp endTime = DateUtils.addDay(currentTime,1);
		// 直传时间字段查询作业数据
		List<BusinessEntity> list = businessMapper.queryHistoryDataByParam(null,null,null,beginTime,endTime);
		if (list.size() == 0) {
			return 0;
		}
		long averageTime = 0;
		for (BusinessEntity entity:list){
			String beginTimeStr = entity.getArriveTime();
			String endTimeStr = entity.getEnterTime();
			if(endTimeStr != null && (!"".equals(endTimeStr))){
				averageTime = averageTime + Timestamp.valueOf(endTimeStr).getTime() - Timestamp.valueOf(beginTimeStr).getTime();
			}
		}
		return averageTime / list.size() / 1000;
	}

	public List<StatisticsRationEntity> queryWeeksData() {
		int dayOfWeek = DateUtils.getDayOfWork(new Date());
		Timestamp currentTime = DateUtils.getSimpleCurTimestampDay();
		Timestamp beginTime = DateUtils.addDay(currentTime,-dayOfWeek+1-7);
		Timestamp endTime = DateUtils.addDay(currentTime,1);
		// 直传时间字段查询作业数据
		List<BusinessEntity> list = businessMapper.queryHistoryDataByParam(null,null,null,beginTime,endTime);

		Map<String,Map<String,Integer>> map = changeList2MapData(list,10);
		map = sortMapByKeyAsc(map);
		return changeMap2List(map);
	}

	//获取当年，每月数据
	public List<StatisticsRationEntity> queryYearsData() {
		Timestamp currentTime = DateUtils.getSimpleCurTimestampDay();
		Timestamp endTime = DateUtils.addDay(currentTime,1);
		Timestamp beginTime = DateUtils.getSimpleCurYearTimestampDay();
		log.info("获取当年数据，begintime:{},endTime:{}",beginTime,endTime);
		List<BusinessEntity> list = businessMapper.queryHistoryDataByParam(null,null,null,beginTime,endTime);
		Map<String,Map<String,Integer>> map = changeList2MapData(list,7);
		map = sortMapByKeyAsc(map);
		return changeMap2List(map);
	}

	public List<StatisticsRationEntity> queryRation(int dayCount) {
		List<StatisticsRationEntity> retList = new ArrayList<>();
		Timestamp currentTime = DateUtils.getSimpleCurTimestampDay();
		Timestamp endTime = DateUtils.addDay(currentTime,1);
		Timestamp beginTime = DateUtils.getSimpleCurTimestampDay();
		beginTime = DateUtils.addDay(beginTime,-dayCount);
		List<BusinessEntity> list = businessMapper.queryHistoryDataByParam(null,null,null,beginTime,endTime);
		Map<String,Map<String,Integer>> map = changeList2MapData(list,10);

		map = sortMapByKeyDesc(map);
		return changeMap2List(map);
	}

	/**
	 * 数据转map
	 * @param list 数据
	 * @param level 天10，月7，年4等级
	 * @return
	 */
	private Map<String,Map<String,Integer>> changeList2MapData(List<BusinessEntity> list,int level){
		Map<String,Map<String,Integer>> map = new HashMap<>();
		for (BusinessEntity entity:list){
			Timestamp time = Timestamp.valueOf(entity.getArriveTime());
			String key = time.toString().substring(0,level);
			Map<String,Integer> countMap = map.get(key);
			if(countMap == null){
				countMap = new HashMap<>();
				countMap.put("totalTrucks",1);
				countMap.put("totalCntrs",1);
				countMap.put("errorTrucks",0);//默认0
				countMap.put("errorCntrs",0);//默认0
				// 修改的车牌不是空，并且与识别车牌不一致，才认为识别失败
				if(entity.getCarPlate().getOcrPlate() != null && (!"".equals(entity.getCarPlate().getOcrPlate())) && (!entity.getCarPlate().getOcrPlate().equals(entity.getOcrCarPlate().getOcrPlate()))){
					countMap.put("errorTrucks",1);
				}
				// 箱数赋值
				countMap.put("totalCntrs",Integer.parseInt(entity.getGeneralInfo().getCntrSize()));
				// 前箱识别错误
				int errorFCntrs = 0;
				if(entity.getFrontContainer().getOcrContainerNo() != null && (!"".equals(entity.getFrontContainer().getOcrContainerNo()))
						&& (!entity.getFrontContainer().getOcrContainerNo().equals(entity.getOcrFrontContainer().getOcrContainerNo()))){
					errorFCntrs++;
				}
				// 后箱识别错误
				int errorBCntrs = 0;
				if(entity.getAfterContainer().getOcrContainerNo() != null && (!"".equals(entity.getAfterContainer().getOcrContainerNo()))
						&& (!entity.getAfterContainer().getOcrContainerNo().equals(entity.getOcrAfterContainer().getOcrContainerNo()))){
					errorBCntrs++;
				}
				int errorCntrs = errorFCntrs + errorBCntrs;
				countMap.put("errorCntrs",errorCntrs);
			}else{
				int totalTrucks = countMap.get("totalTrucks");
				int totalCntrs = countMap.get("totalCntrs");
				int errorTrucks = countMap.get("errorTrucks");
				int errorCntrs = countMap.get("errorCntrs");
				totalTrucks++;//集卡+1
				// 箱数
				totalCntrs = totalCntrs + Integer.parseInt(entity.getGeneralInfo().getCntrSize());
				countMap.put("totalTrucks",totalTrucks);
				countMap.put("totalCntrs",totalCntrs);

				// 识别错误车牌
				if(entity.getCarPlate().getOcrPlate() != null && (!"".equals(entity.getCarPlate().getOcrPlate())) && (!entity.getCarPlate().getOcrPlate().equals(entity.getOcrCarPlate().getOcrPlate()))){
					errorTrucks++;
					countMap.put("errorTrucks",errorTrucks);
				}

				// 前箱识别错误
				int errorFCntrs = 0;
				if(entity.getFrontContainer().getOcrContainerNo() != null && (!"".equals(entity.getFrontContainer().getOcrContainerNo()))
						&& (!entity.getFrontContainer().getOcrContainerNo().equals(entity.getOcrFrontContainer().getOcrContainerNo()))){
					errorFCntrs++;
				}
				// 后箱识别错误
				int errorBCntrs = 0;
				if(entity.getAfterContainer().getOcrContainerNo() != null && (!"".equals(entity.getAfterContainer().getOcrContainerNo()))
						&& (!entity.getAfterContainer().getOcrContainerNo().equals(entity.getOcrAfterContainer().getOcrContainerNo()))){
					errorBCntrs++;
				}
				errorCntrs = errorCntrs + errorFCntrs + errorBCntrs;
				countMap.put("errorCntrs",errorCntrs);
			}
			map.put(key,countMap);
		}
		return map;
	}

	private Map<String, Map<String,Integer>> sortMapByKeyDesc(Map<String, Map<String,Integer>> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, Map<String,Integer>> sortMap = new TreeMap<String, Map<String,Integer>>(
				new MapKeyComparatorDesc());
		sortMap.putAll(map);
		return sortMap;
	}

	private Map<String, Map<String,Integer>> sortMapByKeyAsc(Map<String, Map<String,Integer>> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, Map<String,Integer>> sortMap = new TreeMap<String, Map<String,Integer>>(
				new MapKeyComparatorAsc());
		sortMap.putAll(map);
		return sortMap;
	}

	// 从大到小
	static class MapKeyComparatorDesc implements Comparator<String> {
		@Override
		public int compare(String str1, String str2) {
			return str2.compareTo(str1);
		}
	}
	// 从小到大
	static class MapKeyComparatorAsc implements Comparator<String> {
		@Override
		public int compare(String str1, String str2) {
			return str1.compareTo(str2);
		}
	}

	//转换成页面需要的数据
	private List<StatisticsRationEntity> changeMap2List(Map<String,Map<String,Integer>> map){
		List<StatisticsRationEntity> retList = new ArrayList<>();
		if(map == null){ // 没有查到数据反馈页面空的list
			return retList;
		}
		for (Map.Entry<String, Map<String,Integer>> entry : map.entrySet()) {
			//System.out.println(entry.getKey() + " " + entry.getValue());
			StatisticsRationEntity entity = new StatisticsRationEntity();
			entity.setName(entry.getKey());
			int totalTrucks = entry.getValue().get("totalTrucks");//总数量
			int errorTrucks = entry.getValue().get("errorTrucks");//识别错误数量
			int totalCntrs = entry.getValue().get("totalCntrs");
			int errorCntrs = entry.getValue().get("errorCntrs");
			entity.setTruckCount(totalTrucks);
			entity.setCntrCount(totalCntrs);
			entity.setTruckRation(((double)totalTrucks-errorTrucks)/totalTrucks*100);
			entity.setCntrRation(((double)totalCntrs-errorCntrs)/totalCntrs*100);
			retList.add(entity);
		}
		return retList;
	}

}
