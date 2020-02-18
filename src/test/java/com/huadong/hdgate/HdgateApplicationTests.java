package com.huadong.hdgate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HdgateApplicationTests {

	@Test
	public void contextLoads() {
	}

	/*
      参数：

java -jar -Doracle.net.tns_admin=D:\hdGate  hdgate-0.0.1-SNAPSHOT.jar



	{
    "Station":"control",
    "Stage":"",
    "Msg":"作业成功",
    "ArriveTime": "2018/12/04 23:27:39",
    "EnterTime": "2018/12/05 00:00:39",
    "GeneralInfo":
    {
      "LaneCode":"YT-1",
      "RFID":"WEFSDFWE23456764354",
      "ID":"3890976689809986788",
      "BARCODE":"23421312313223",
      "WEIGHT":"23456",
      "CntrSize":"2"
    },
    "OCRCarPlate": {
        "OcrPlate": "闽C92154",
        "PlateColor": ""
    },
    "OCRFrontContainer": {
        "OCRContainerPos": "F",
        "OCRContainerNo": "CCLU0123456",
        "OCRContainerConf": 0.99,
        "OCRContainerDirection": "A",
        "OCRContainerISO": "22G1",
        "OCRISOConf": 0.99,
        "OCRDamage":"0",
        "EFID":"E"
    },
    "OCRAfterContainer": {
        "OCRContainerPos": "A",
        "OCRContainerNo": "CCLU6543210",
        "OCRContainerConf": 0.99,
        "OCRContainerDirection": "F",
        "OCRContainerISO": "22G1",
        "OCRISOConf": 0.99,
        "OCRDamage":"0",
        "EFID":"E"
    },
    "FtpImages": {
        "Folder": "232739",
        "ImageName": [
            "Plate.jpg",
            "G01.jpg",
            "A01.jpg",
            "B01.jpg",
            "C01.jpg",
            "C02.jpg",
            "D01.jpg",
            "D02.jpg",
            "Left.jpg",
            "Right.jpg",
            "Top.jpg",
            "","","",""
        ],
        "ImagePath": "ftp://192.168.80.250/G04/2018/201812/20181204/232739"
    },
    "LocalImagePath": "D:/hd_truckGate/photo/2018/201812/20181204/232739",
    "VisitGuid": "1",
    "Damage": "0"
}

	* */
}