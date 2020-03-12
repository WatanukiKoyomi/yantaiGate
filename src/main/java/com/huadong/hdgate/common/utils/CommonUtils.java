package com.huadong.hdgate.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.huadong.hdgate.jobManagement.entity.cdiEntity.BusinessEntity;
import com.huadong.hdgate.jobManagement.entity.showEntity.*;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class CommonUtils {

	private static Map<String, WebSocketSession>socketSessionMap = new HashMap<String, WebSocketSession>();
	/**
	 * 获取请求客户端的ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request){
		String ipAddress = request.getHeader("x-forwarded-for");
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
				//根据网卡取本机配置的IP
				InetAddress inet=null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress= inet.getHostAddress();
			}
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
			if(ipAddress.indexOf(",")>0){
				ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	public static Map<String, WebSocketSession> getSocketSessionMap() {
		return socketSessionMap;
	}

	public static void setSocketSessionMap(Map<String, WebSocketSession> socketSessionMap) {
		CommonUtils.socketSessionMap = socketSessionMap;
	}

	// 转换对象的字符串
	public static String cdiEntity2ShowEntityStr(BusinessEntity businessEntity){
		ShowBusinessEntity showEntity = new ShowBusinessEntity(
				businessEntity.getVisitGuid(),
				businessEntity.getStation(),
				businessEntity.getMsg(),
				businessEntity.getArriveTime(),
				businessEntity.getEnterTime());
		GeneralInfoEntity showInfoEntity = new GeneralInfoEntity(
				businessEntity.getGeneralInfo().getLaneCode(),
				businessEntity.getGeneralInfo().getRfid(),
				businessEntity.getGeneralInfo().getWeight(),
				businessEntity.getGeneralInfo().getCntrSize());
		showEntity.setGeneralInfo(showInfoEntity);
		CarPlateEntity showCarEntity = new CarPlateEntity(
				businessEntity.getOcrCarPlate().getOcrPlate(),
				businessEntity.getOcrCarPlate().getPlateColor());
		showEntity.setOcrCarPlate(showCarEntity);
		ContainerEntity frontCntrEntity = new ContainerEntity(
				businessEntity.getOcrFrontContainer().getOcrContainerNo(),
				businessEntity.getOcrFrontContainer().getOcrContainerConf(),
				businessEntity.getOcrFrontContainer().getOcrContainerDirection(),
				businessEntity.getOcrFrontContainer().getOcrContainerISO(),
				businessEntity.getOcrFrontContainer().getOcrIsoConf(),
				businessEntity.getOcrFrontContainer().getOcrDamage(),
				businessEntity.getOcrFrontContainer().getEfid(),
				businessEntity.getOcrFrontContainer().getIsProvideForOneself(),
				businessEntity.getOcrFrontContainer().getLeadSealState(),
				businessEntity.getOcrFrontContainer().getLeadSealNo(),
				businessEntity.getOcrFrontContainer().getProperty());
		showEntity.setOcrFrontContainer(frontCntrEntity);
		ContainerEntity afterCntrEntity = new ContainerEntity(
				businessEntity.getOcrAfterContainer().getOcrContainerNo(),
				businessEntity.getOcrAfterContainer().getOcrContainerConf(),
				businessEntity.getOcrAfterContainer().getOcrContainerDirection(),
				businessEntity.getOcrAfterContainer().getOcrContainerISO(),
				businessEntity.getOcrAfterContainer().getOcrIsoConf(),
				businessEntity.getOcrAfterContainer().getOcrDamage(),
				businessEntity.getOcrAfterContainer().getEfid(),
				businessEntity.getOcrAfterContainer().getIsProvideForOneself(),
				businessEntity.getOcrAfterContainer().getLeadSealState(),
				businessEntity.getOcrAfterContainer().getLeadSealNo(),
				businessEntity.getOcrAfterContainer().getProperty());
		showEntity.setOcrAfterContainer(afterCntrEntity);
		FtpImagesEntity ftpEntity = new FtpImagesEntity(
				businessEntity.getFtpImages().getFolder(),
				businessEntity.getFtpImages().getImageName(),
				businessEntity.getFtpImages().getImagePath());
		showEntity.setFtpImages(ftpEntity);
		return JSONObject.toJSONString(showEntity);
	}
}
