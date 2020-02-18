package com.huadong.hdgate.test;

import com.huadong.hdgate.common.utils.webapi.HttpsUtils;
import org.junit.Test;

public class HttpUtilsApiTest {
	@Test
	public void postApiTest(){
		String jsonStr = "{\n" +
				"    \"Station\":\"control\",\n" +
				"    \"Stage\":\"\",\n" +
				"    \"Msg\":\"作业成功\",\n" +
				"    \"ArriveTime\": \"2018/12/04 23:27:39\",\n" +
				"    \"EnterTime\": \"2018/12/05 00:00:39\",\n" +
				"    \"GeneralInfo\":\n" +
				"    {\n" +
				"      \"LaneCode\":\"YT-1\",\n" +
				"      \"RFID\":\"WEFSDFWE23456764354\",\n" +
				"      \"ID\":\"3890976689809986788\",\n" +
				"      \"BARCODE\":\"23421312313223\",\n" +
				"      \"WEIGHT\":\"23456\",\n" +
				"      \"CntrSize\":\"2\"\n" +
				"    },\n" +
				"    \"OCRCarPlate\": {\n" +
				"        \"OcrPlate\": \"闽C92154\",\n" +
				"        \"PlateColor\": \"\"\n" +
				"    },\n" +
				"    \"OCRFrontContainer\": {\n" +
				"        \"OCRContainerPos\": \"F\",\n" +
				"        \"OCRContainerNo\": \"CCLU0123456\",\n" +
				"        \"OCRContainerConf\": 0.99,\n" +
				"        \"OCRContainerDirection\": \"A\",\n" +
				"        \"OCRContainerISO\": \"22G1\",\n" +
				"        \"OCRISOConf\": 0.99\n" +
				"    },\n" +
				"    \"OCRAfterContainer\": {\n" +
				"        \"OCRContainerPos\": \"A\",\n" +
				"        \"OCRContainerNo\": \"CCLU6543210\",\n" +
				"        \"OCRContainerConf\": 0.99,\n" +
				"        \"OCRContainerDirection\": \"F\",\n" +
				"        \"OCRContainerISO\": \"22G1\",\n" +
				"        \"OCRISOConf\": 0.99\n" +
				"    },\n" +
				"    \"FtpImages\": {\n" +
				"        \"Folder\": \"232739\",\n" +
				"        \"ImageName\": [\n" +
				"            \"Plate.jpg\",\n" +
				"            \"G01.jpg\",\n" +
				"            \"A01.jpg\",\n" +
				"            \"B01.jpg\",\n" +
				"            \"C01.jpg\",\n" +
				"            \"C02.jpg\",\n" +
				"            \"D01.jpg\",\n" +
				"            \"D02.jpg\",\n" +
				"            \"Left.jpg\",\n" +
				"            \"Right.jpg\",\n" +
				"            \"Top.jpg\"\n" +
				"        ],\n" +
				"        \"ImagePath\": \"ftp://192.168.80.250/G04/2018/201812/20181204/232739\"\n" +
				"    },\n" +
				"    \"LocalImagePath\": \"D:/hd_truckGate/photo/2018/201812/20181204/232739\",\n" +
				"    \"VisitGuid\": \"e18bb27dbb324a59a6b10b3610780b2a\"\n" +
				"}\n";
		String url = "http://10.100.55.11:8085/hdGate/monitor/laneMonitorApi";
		String retMsg = HttpsUtils.doPost(url, jsonStr, "utf-8");
		System.out.println(retMsg);
	}
}
