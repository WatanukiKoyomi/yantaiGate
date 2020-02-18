package com.huadong.hdgate.test;

import org.junit.Test;

import java.text.MessageFormat;

public class MessageFormatTest {
	@Test
	public void messageFormatTest(){
		//String str = "{\"laneCode\":\"{0}\",\"truckPlcUrl\":\"\",\"frontPlcUrl\":\"\",\"backPlcUrl\":\"\",\"leftFrontPlcUrl\":\"\",\"leftBackPlcUrl\":\"\",\"rightFrontPlcUrl\":\"\",\"rightBackPlcUrl\":\"\",\"platePlcUrl\":\"\",\"containerNumber\":\"\",\"retMsg\":\"\",\"vehicleInfo\":{\"vehicleNo\":\"\",\"traysNo\":\"\",\"traysName\":\"\",\"payUnit\":\"\",\"vehicleWeight\":\"\",\"traysWeight\":\"\",\"totalWeight\":\"\",\"vehicleCompany\":\"\"},\"foreContainer\":{\"consignationNo\":\"\",\"equipmentReceipt\":\"等待数据接入\",\"containerNo\":\"\",\"coldTemperature\":\"\",\"shipCod\":\"\",\"containerISO\":\"\",\"dangerLevel\":\"\",\"ceShipName\":\"\",\"containerSize\":\"\",\"dangerGaugeNo\":\"\",\"voyageNo\":\"\",\"containerCompany\":\"\",\"fumigate\":\"\",\"ladingBillNo\":\"\",\"containerAgent\":\"\",\"customsTransit\":\"\",\"unloadingPort\":\"\",\"purposePort\":\"\",\"leadSeal\":\"\",\"containerWeight\":\"\",\"weight\":\"\",\"position\":\"\",\"overLimit\":\"\",\"overLeft\":\"\",\"overRight\":\"\",\"overFront\":\"\",\"overHeight\":\"\"},\"afterContainer\":{\"consignationNo\":\"\",\"equipmentReceipt\":\"\",\"containerNo\":\"\",\"coldTemperature\":\"\",\"shipCod\":\"\",\"containerISO\":\"\",\"dangerLevel\":\"\",\"ceShipName\":\"\",\"containerSize\":\"\",\"dangerGaugeNo\":\"\",\"voyageNo\":\"\",\"containerCompany\":\"\",\"fumigate\":\"\",\"ladingBillNo\":\"\",\"containerAgent\":\"\",\"customsTransit\":\"\",\"unloadingPort\":\"\",\"purposePort\":\"\",\"leadSeal\":\"\",\"containerWeight\":\"\",\"weight\":\"\",\"position\":\"\",\"overLimit\":\"\",\"overLeft\":\"\",\"overRight\":\"\",\"overFront\":\"\",\"overHeight\":\"\"}}";
		String str = "'{\"laneCode\":\"{0}\",\"vehicleInfo\":'{\"vehicleNo\":\"\"'},\"foreContainer\":'{\"consignationNo\":\"\",\"equipmentReceipt\":\"等待数据接入\",\"containerNo\":\"\"'},\"afterContainer\":'{\"consignationNo\":\"\",\"equipmentReceipt\":\"\",\"containerNo\":\"\"'}'}";
		Object[] params = {"1"};
		String msg = MessageFormat.format(str,1);
		System.out.println(msg);
	}
	@Test
	public void test1(){
		String str = "this is a test!";
		System.out.println(MessageFormat.format("MessageFormat方法：{0}这是{1}的使用", str ,"占位符"));
	}
}
