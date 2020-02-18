package com.huadong.hdgate.jobManagement.controller;

import com.github.pagehelper.PageInfo;
import com.huadong.hdgate.common.utils.DateUtils;
import com.huadong.hdgate.jobManagement.entity.StatisticsRationEntity;
import com.huadong.hdgate.jobManagement.entity.cdiEntity.BusinessEntity;
import com.huadong.hdgate.jobManagement.service.BusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 车道历史记录controller
 */
@Api(value="车道监控",tags={"车道监控页面"})
@RestController
@RequestMapping(value = "/history")
@Slf4j
public class LaneHistoryController {

	@Resource
	private BusinessService businessService;

	@ApiIgnore
	@ApiOperation(value = "查询历史记录", notes = "根据传入参数，查询记录记录数据"  )
	@RequestMapping(value = "/queryHistoryDataByParam" , method = RequestMethod.GET)
	public PageInfo<BusinessEntity> queryHistoryDataByParam(HttpServletRequest request) {
		String laneCode = request.getParameter("laneCode");
		String truckNo = request.getParameter("truckNo").toUpperCase();
		String cntrNo = request.getParameter("cntrNo").toUpperCase();
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		log.info("查询历史记录:参数：laneCode:{},truckNo:{},cntrNo:{},pageNum:{},pageSize:{},beginTime:{},endTime:{}",laneCode,truckNo,cntrNo,pageNum,pageSize,beginTime,endTime);
		return businessService.queryHistoryDataByParam(laneCode,truckNo,cntrNo,pageNum,pageSize,beginTime,endTime);
	}

	@ApiIgnore
	@ApiOperation(value = "最近一周平均过车时间", notes = "最近一周平均过车时间"  )
	@RequestMapping(value = "/queryHistoryDataAverageTime" , method = RequestMethod.GET)
	public long queryHistoryDataAverageTime() {
		return businessService.queryHistoryDataAverageTime();
	}

	@ApiIgnore
	//获取横坐标
	@RequestMapping(value = "/queryWeeksXAxis",method = RequestMethod.GET)
	public List<String> queryWeeksXAxis() {
		List<String> list = new ArrayList<>();
		int dayOfWeek = DateUtils.getDayOfWork(new Date());
		Timestamp time = DateUtils.getSimpleCurTimestampDay();
		time = DateUtils.addDay(time,-dayOfWeek+1-7);
		for (int i=0;i<14;i++){
			list.add(time.toString().substring(0,10));
			time = DateUtils.addDay(time,1);
		}
		return list;
	}

	@ApiIgnore
	@RequestMapping(value = "/queryWeeksData",method = RequestMethod.GET)
	public List<StatisticsRationEntity> queryWeeksData() {
		return businessService.queryWeeksData();
	}

	@ApiIgnore
	@RequestMapping(value = "/queryYearsData",method = RequestMethod.GET)
	public List<StatisticsRationEntity> queryYearsData() {
		return businessService.queryYearsData();
	}

	@ApiIgnore
	@RequestMapping(value = "/queryRation",method = RequestMethod.GET)
	public List<StatisticsRationEntity> queryTruckNoRation() {
		return businessService.queryRation(StatisticsRationEntity.DAYS);
	}

}
