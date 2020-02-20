package com.huadong.hdgate.jobManagement.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.huadong.hdgate.jobManagement.entity.cdiEntity.BusinessEntity;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface BusinessMapper extends BaseMapper<BusinessEntity>{

	public void insertBusiness(@Param("visitGuid") String visitGuid,
							   @Param("station") String station,
							   @Param("msg") String msg,
							   @Param("arriveTime") String arriveTime,
							   @Param("enterTime") String enterTime,
							   @Param("laneCode") String laneCode,
							   @Param("rfid") String rfid,
							   @Param("weight") String weight,
							   @Param("cntrSize") String cntrSize,
							   @Param("ocrPlate") String ocrPlate,
							   @Param("plateColor") String plateColor,
							   @Param("aOcrContainerNo") String aOcrContainerNo,
							   @Param("aOcrContainerConf") String aOcrContainerConf,
							   @Param("aOcrContainerDirection") String aOcrContainerDirection,
							   @Param("aOcrContainerISO") String aOcrContainerISO,
							   @Param("aOcrIsoConf") String aOcrIsoConf,
							   @Param("aOcrDamage") String aOcrDamage,
							   @Param("aEfId") String aEfId,
							   @Param("bOcrContainerNo") String bOcrContainerNo,
							   @Param("bOcrContainerConf") String bOcrContainerConf,
							   @Param("bOcrContainerDirection") String bOcrContainerDirection,
							   @Param("bOcrContainerISO") String bOcrContainerISO,
							   @Param("bOcrIsoConf") String bOcrIsoConf,
							   @Param("bOcrDamage") String bOcrDamage,
							   @Param("bEfId") String bEfId,
							   @Param("folder") String folder,
							   @Param("imagePath") String imagePath,
							   @Param("imageName") String imageName);

	public void updateBusiness(@Param("visitGuid") String visitGuid,
							   @Param("station") String station,
							   @Param("msg") String msg,
							   @Param("arriveTime") String arriveTime,
							   @Param("enterTime") String enterTime,
							   @Param("laneCode") String laneCode,
							   @Param("rfid") String rfid,
							   @Param("cntrSize") String cntrSize,
							   @Param("ocrPlate") String ocrPlate,
							   @Param("plateColor") String plateColor,
							   @Param("aOcrContainerNo") String aOcrContainerNo,
							   @Param("aOcrContainerConf") String aOcrContainerConf,
							   @Param("aOcrContainerDirection") String aOcrContainerDirection,
							   @Param("aOcrContainerISO") String aOcrContainerISO,
							   @Param("aOcrIsoConf") String aOcrIsoConf,
							   @Param("aOcrDamage") String aOcrDamage,
							   @Param("aEfId") String aEfId,
							   @Param("bOcrContainerNo") String bOcrContainerNo,
							   @Param("bOcrContainerConf") String bOcrContainerConf,
							   @Param("bOcrContainerDirection") String bOcrContainerDirection,
							   @Param("bOcrContainerISO") String bOcrContainerISO,
							   @Param("bOcrIsoConf") String bOcrIsoConf,
							   @Param("bOcrDamage") String bOcrDamage,
							   @Param("bEfId") String bEfId,
							   @Param("folder") String folder,
							   @Param("imagePath") String imagePath,
							   @Param("imageName") String imageName,
							   @Param("updatePlate") String updatePlate,
							   @Param("updateFrontNo") String updateFrontNo,
							   @Param("updateBackNo") String updateBackNo,
							   @Param("weight") String weight);

	public List<BusinessEntity> queryHistoryDataByParam(@Param("laneCode")String laneCode,
														@Param("truckNo")String truckNo,
														@Param("cntrNo")String cntrNo,
														@Param("beginTime")Timestamp beginTime,
														@Param("endTime")Timestamp endTime);

}
