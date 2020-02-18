package com.huadong.hdgate.jobManagement.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huadong.hdgate.common.filter.MyWebsocketHandler;
import com.huadong.hdgate.common.utils.DateUtils;
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

	public boolean insertOrUpdateBusinessData(String businessDataStr){
		BusinessEntity businessEntity = JSONObject.parseObject(businessDataStr,BusinessEntity.class);
		redisUtils.setex(businessEntity.getGeneralInfo().getLaneCode(),3600*5,businessDataStr);// 每次接收到数据存储到redis中，保证redis中对应每条车道都是最新数据
		log.info("laneMonitorApi接收到数据：{},\n转换对象后{}",businessDataStr,businessEntity);

		String redisData = redisUtils.get(businessEntity.getVisitGuid());
		if(redisData == null || "".equals(redisData)){
			businessMapper.insertBusiness(businessEntity.getVisitGuid(),businessEntity.getStation(),businessEntity.getMsg(),businessEntity.getArriveTime(),businessEntity.getEnterTime(),
					businessEntity.getGeneralInfo().getLaneCode(),businessEntity.getGeneralInfo().getRfid(),businessEntity.getGeneralInfo().getWeight(),businessEntity.getGeneralInfo().getCntrSize(),
					businessEntity.getOcrCarPlate().getOcrPlate(),businessEntity.getOcrCarPlate().getPlateColor(),
					businessEntity.getOcrFrontContainer().getOcrContainerNo(),
					businessEntity.getOcrFrontContainer().getOcrContainerConf(),businessEntity.getOcrFrontContainer().getOcrContainerDirection(),businessEntity.getOcrFrontContainer().getOcrContainerISO(),
					businessEntity.getOcrFrontContainer().getOcrIsoConf(),businessEntity.getOcrFrontContainer().getOcrDamage(),businessEntity.getOcrFrontContainer().getEfid(),
					businessEntity.getOcrAfterContainer().getOcrContainerNo(),
					businessEntity.getOcrAfterContainer().getOcrContainerConf(),businessEntity.getOcrAfterContainer().getOcrContainerDirection(),businessEntity.getOcrAfterContainer().getOcrContainerISO(),
					businessEntity.getOcrAfterContainer().getOcrIsoConf(),businessEntity.getOcrAfterContainer().getOcrDamage(),businessEntity.getOcrAfterContainer().getEfid(),
					businessEntity.getFtpImages().getFolder(),businessEntity.getFtpImages().getImagePath(),businessEntity.getFtpImages().getImageName().toString()
			);
			redisUtils.setex(businessEntity.getVisitGuid(),3600*5,businessDataStr);
		}else{
			businessMapper.updateBusiness(businessEntity.getVisitGuid(),businessEntity.getStation(),businessEntity.getMsg(),businessEntity.getArriveTime(),businessEntity.getEnterTime(),
					businessEntity.getGeneralInfo().getLaneCode(),businessEntity.getGeneralInfo().getRfid(),businessEntity.getGeneralInfo().getCntrSize(),
					businessEntity.getOcrCarPlate().getOcrPlate(),businessEntity.getOcrCarPlate().getPlateColor(),
					businessEntity.getOcrFrontContainer().getOcrContainerNo(),
					businessEntity.getOcrFrontContainer().getOcrContainerConf(),businessEntity.getOcrFrontContainer().getOcrContainerDirection(),businessEntity.getOcrFrontContainer().getOcrContainerISO(),
					businessEntity.getOcrFrontContainer().getOcrIsoConf(),businessEntity.getOcrFrontContainer().getOcrDamage(),businessEntity.getOcrFrontContainer().getEfid(),
					businessEntity.getOcrAfterContainer().getOcrContainerNo(),
					businessEntity.getOcrAfterContainer().getOcrContainerConf(),businessEntity.getOcrAfterContainer().getOcrContainerDirection(),businessEntity.getOcrAfterContainer().getOcrContainerISO(),
					businessEntity.getOcrAfterContainer().getOcrIsoConf(),businessEntity.getOcrAfterContainer().getOcrDamage(),businessEntity.getOcrAfterContainer().getEfid(),
					businessEntity.getFtpImages().getFolder(),businessEntity.getFtpImages().getImagePath(),businessEntity.getFtpImages().getImageName().toString(),
					businessEntity.getCarPlate().getOcrPlate(),businessEntity.getFrontContainer().getOcrContainerNo(),businessEntity.getAfterContainer().getOcrContainerNo(),
					businessEntity.getGeneralInfo().getWeight()
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
		if(newBusinessData.getCarPlate().getOcrPlate() != null){
			oldBusinessData.getCarPlate().setOcrPlate(newBusinessData.getCarPlate().getOcrPlate().toUpperCase());
		}else{
			// 再次判断，如果值没有变化不需要更新，有变化说明修改过
			if(!newBusinessData.getOcrCarPlate().getOcrPlate().toUpperCase().equals(oldBusinessData.getOcrCarPlate().getOcrPlate().toUpperCase())){
				oldBusinessData.getCarPlate().setOcrPlate(newBusinessData.getOcrCarPlate().getOcrPlate().toUpperCase());
			}
		}
		// 前箱号赋值
		if(newBusinessData.getFrontContainer().getOcrContainerNo() != null){
			oldBusinessData.getFrontContainer().setOcrContainerNo(newBusinessData.getFrontContainer().getOcrContainerNo().toUpperCase());
		}else{
			// 再次判断，如果值没有变化不需要更新，有变化说明修改过
			if(!newBusinessData.getOcrFrontContainer().getOcrContainerNo().toUpperCase().equals(oldBusinessData.getOcrFrontContainer().getOcrContainerNo().toUpperCase())){
				oldBusinessData.getFrontContainer().setOcrContainerNo(newBusinessData.getOcrFrontContainer().getOcrContainerNo().toUpperCase());
			}
		}
		// 前箱型赋值
		if(newBusinessData.getFrontContainer().getOcrContainerISO() != null){
			oldBusinessData.getFrontContainer().setOcrContainerISO(newBusinessData.getFrontContainer().getOcrContainerISO().toUpperCase());
		}else{
			// 再次判断，如果值没有变化不需要更新，有变化说明修改过
			if(!newBusinessData.getOcrFrontContainer().getOcrContainerISO().toUpperCase().equals(oldBusinessData.getOcrFrontContainer().getOcrContainerISO().toUpperCase())){
				oldBusinessData.getFrontContainer().setOcrContainerISO(newBusinessData.getOcrFrontContainer().getOcrContainerISO().toUpperCase());
			}
		}
		// 前箱型空重
		if(newBusinessData.getFrontContainer().getEfid() != null){
			oldBusinessData.getFrontContainer().setEfid(newBusinessData.getFrontContainer().getEfid().toUpperCase());
		}else{
			// 再次判断，如果值没有变化不需要更新，有变化说明修改过
			if(!newBusinessData.getOcrFrontContainer().getEfid().toUpperCase().equals(oldBusinessData.getOcrFrontContainer().getEfid().toUpperCase())){
				oldBusinessData.getOcrFrontContainer().setEfid(newBusinessData.getOcrFrontContainer().getEfid().toUpperCase());
			}
		}
		// 后箱号赋值
		if(newBusinessData.getAfterContainer().getOcrContainerNo() != null){
			oldBusinessData.getAfterContainer().setOcrContainerNo(newBusinessData.getAfterContainer().getOcrContainerNo().toUpperCase());
		}else{
			// 再次判断，如果值没有变化不需要更新，有变化说明修改过
			if(!newBusinessData.getOcrAfterContainer().getOcrContainerNo().toUpperCase().equals(oldBusinessData.getOcrAfterContainer().getOcrContainerNo().toUpperCase())){
				oldBusinessData.getAfterContainer().setOcrContainerNo(newBusinessData.getOcrAfterContainer().getOcrContainerNo().toUpperCase());
			}
		}
		// 后箱型赋值
		if(newBusinessData.getAfterContainer().getOcrContainerISO() != null){
			oldBusinessData.getAfterContainer().setOcrContainerISO(newBusinessData.getAfterContainer().getOcrContainerISO().toUpperCase());
		}else{
			// 再次判断，如果值没有变化不需要更新，有变化说明修改过
			if(!newBusinessData.getOcrAfterContainer().getOcrContainerISO().toUpperCase().equals(oldBusinessData.getOcrAfterContainer().getOcrContainerISO().toUpperCase())){
				oldBusinessData.getAfterContainer().setOcrContainerISO(newBusinessData.getOcrAfterContainer().getOcrContainerISO().toUpperCase());
			}
		}
		// 后箱空重
		if(newBusinessData.getAfterContainer().getEfid() != null){
			oldBusinessData.getAfterContainer().setEfid(newBusinessData.getAfterContainer().getEfid().toUpperCase());
		}else{
			// 再次判断，如果值没有变化不需要更新，有变化说明修改过
			if(!newBusinessData.getOcrAfterContainer().getEfid().toUpperCase().equals(oldBusinessData.getOcrAfterContainer().getEfid().toUpperCase())){
				oldBusinessData.getOcrAfterContainer().setEfid(newBusinessData.getOcrAfterContainer().getEfid().toUpperCase());
			}
		}
		String finalBusinessDataStr = JSONObject.toJSONString(oldBusinessData);
		redisUtils.set(laneCode,finalBusinessDataStr);

		String url = "http://"+xijingParamsEntity.getIp()+":"+xijingParamsEntity.getPort()+"/xijing/monitor/sendUpdateData";
		log.info("车道{}，操作员人工识别修正后，调用西井接收端口，url:{},data:{}",laneCode,url,finalBusinessDataStr);
		HttpsUtils.doPost(url,finalBusinessDataStr,"urf-8");
		return finalBusinessDataStr;
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
