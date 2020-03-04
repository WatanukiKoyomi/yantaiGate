CREATE DATABASE  IF NOT EXISTS `yantai` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `yantai`;
-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: yantai
-- ------------------------------------------------------
-- Server version	5.7.29-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gate_business`
--

DROP TABLE IF EXISTS `gate_business`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `gate_business` (
  `visit_guid` varchar(50) NOT NULL COMMENT '主键',
  `station` varchar(10) DEFAULT NULL,
  `msg` varchar(200) DEFAULT NULL COMMENT '消息',
  `arrive_time` datetime NOT NULL COMMENT '开始时间',
  `enter_time` datetime DEFAULT NULL COMMENT '结束时间',
  `lane_code` varchar(10) DEFAULT NULL COMMENT '车道编号',
  `rfid` varchar(20) DEFAULT NULL COMMENT 'rfid',
  `weight` varchar(20) DEFAULT NULL COMMENT '重量',
  `cntr_size` varchar(5) DEFAULT NULL COMMENT '集装箱数量',
  `ocr_plate_no` varchar(20) DEFAULT NULL COMMENT '识别车牌号',
  `plate_color` varchar(20) DEFAULT NULL COMMENT '车牌颜色',
  `plate_no` varchar(20) DEFAULT NULL COMMENT '纠正车牌号',
  `ocr_front_cntr_no` varchar(20) DEFAULT NULL COMMENT '识别前箱号',
  `ocr_front_conf` varchar(10) DEFAULT NULL COMMENT '识别前箱号可信度',
  `ocr_front_direction` varchar(20) DEFAULT NULL COMMENT '识别前箱箱门朝向',
  `ocr_front_iso` varchar(10) DEFAULT NULL COMMENT '识别前箱箱型',
  `ocr_front_iso_conf` varchar(10) DEFAULT NULL COMMENT '识别前箱箱型可信度',
  `ocr_front_damage` varchar(10) DEFAULT NULL COMMENT '识别前箱残损情况',
  `ocr_front_efid` varchar(10) DEFAULT NULL COMMENT '识别前箱空重',
  `ocr_back_cntr_no` varchar(20) DEFAULT NULL COMMENT '识别后箱箱号',
  `ocr_back_conf` varchar(10) DEFAULT NULL COMMENT '识别后箱箱号可信度',
  `ocr_back_direction` varchar(10) DEFAULT NULL COMMENT '识别后箱箱门朝向',
  `ocr_back_iso` varchar(10) DEFAULT NULL COMMENT '识别后箱箱型',
  `ocr_back_iso_conf` varchar(10) DEFAULT NULL COMMENT '识别后箱箱型可信度',
  `ocr_back_damage` varchar(10) DEFAULT NULL COMMENT '识别后箱残损情况',
  `ocr_back_efid` varchar(10) DEFAULT NULL COMMENT '识别后箱空重',
  `front_cntr_no` varchar(20) DEFAULT NULL COMMENT '纠正前箱箱号',
  `back_cntr_no` varchar(20) DEFAULT NULL COMMENT '纠正后箱箱号',
  `img_folder` varchar(20) DEFAULT NULL COMMENT '图片文件夹名称',
  `img_path` varchar(200) DEFAULT NULL COMMENT '图片存放ftp路径',
  `img_names` varchar(1000) DEFAULT NULL COMMENT '图片文件名称',
  `update_user` varchar(20) DEFAULT NULL COMMENT '修改数据用户',
  `ocr_front_leadsealstate` varchar(45) DEFAULT NULL COMMENT '前箱铅封状态',
  `ocr_front_leadsealno` varchar(45) DEFAULT NULL COMMENT '前箱铅封号',
  `ocr_front_property` varchar(45) DEFAULT NULL COMMENT '前箱箱属',
  `ocr_back_leadsealstate` varchar(45) DEFAULT NULL COMMENT '后箱铅封状态',
  `ocr_back_leadsealno` varchar(45) DEFAULT NULL COMMENT '后箱铅封号',
  `ocr_back_property` varchar(45) DEFAULT NULL COMMENT '后箱箱属',
  PRIMARY KEY (`visit_guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gate_business`
--

LOCK TABLES `gate_business` WRITE;
/*!40000 ALTER TABLE `gate_business` DISABLE KEYS */;
INSERT INTO `gate_business` VALUES ('0fde0d435da74f40af077ff607ec8d79','huadong','未找到车辆信息','2020-02-06 15:32:30','2020-02-06 15:32:35','HD_1',NULL,'0','2','鲁Y88888','Blue','鲁Y16451','CCLU7908356',NULL,'F','22G1',NULL,NULL,'','CCLU7908356',NULL,'F','22G1',NULL,NULL,'',NULL,NULL,NULL,NULL,'http://127.0.0.1/laneno/ymdhms/lorry.jpg,http://127.0.0.1/laneno/ymdhms/behind.jpg,http://127.0.0.1/laneno/ymdhms/top.jpg,http://127.0.0.1/laneno/ymdhms/right1.jpg,http://127.0.0.1/laneno/ymdhms/left.jpg,http://127.0.0.1/laneno/ymdhms/ahead.jpg,http://127.0.0.1/laneno/ymdhms/right2.jpg,http://127.0.0.1/laneno/ymdhms/plate.jpg,http://127.0.0.1/laneno/ymdhms/left2.jpg,http://127.0.0.1/laneno/ymdhms/left1.jpg,http://127.0.0.1/laneno/ymdhms/right.jpg',NULL,'Y','1138908','危品','Y','1138908','危品'),('1','control','作业成功','2018-12-04 23:27:39','2018-12-05 00:00:39','test','WEFSDFWE23456764354','23456','2','闽C92111','','闽C92145','CCLU0123451','0.99','A','22G1','0.99','0','F','CCLU6543211','0.99','F','22G1','0.99','0','E','CCLU0123452','CCLU6543211','232739','ftp://192.168.80.250/G04/2018/201812/20181204/232739','Plate.jpg,A01.jpg,B01.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('11','control','作业成功','2019-11-04 23:27:39','2019-11-05 00:00:39','YT-1','WEFSDFWE23456764354','23456','2','闽C92154','',NULL,'CCLU0123456','0.99','A','22G1','0.99','0','E','CCLU6543210','0.99','F','22G1','0.99','0','E',NULL,NULL,'232739','ftp://192.168.80.250/static','Plate.jpg,A01.jpg,B01.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('12','control','作业成功','2019-10-04 23:27:39','2019-10-05 00:00:39','YT-1','WEFSDFWE23456764354','23456','2','闽C92154','',NULL,'CCLU0123456','0.99','A','22G1','0.99','0','E','CCLU6543210','0.99','F','22G1','0.99','0','E',NULL,NULL,'232739','ftp://192.168.80.250/static','Plate.jpg,A01.jpg,B01.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('13','control','作业成功','2019-10-04 23:27:39','2019-10-05 00:00:39','YT-1','WEFSDFWE23456764354','23456','2','闽C92154','',NULL,'CCLU0123456','0.99','A','22G1','0.99','0','E','CCLU6543210','0.99','F','22G1','0.99','0','E',NULL,NULL,'232739','ftp://192.168.80.250/static','Plate.jpg,A01.jpg,B01.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('14','control','作业成功','2019-12-22 23:55:39','2019-12-23 00:00:39','YT-1','WEFSDFWE23456764354','23456','2','闽C92154','','1','CCLU0123456','0.99','A','22G1','0.99','0','E','CCLU6543210','0.99','F','22G1','0.99','0','E','1',NULL,'232739','ftp://192.168.80.250/static','Plate.jpg,A01.jpg,B01.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('1fde0d435da74f40af077ff607ec8d79','huadong','未找到车辆信息','2020-02-06 15:32:30','2020-02-06 15:32:35','YT-1',NULL,'0','2','鲁Y88881','Blue',NULL,'CCLU7908356',NULL,'F','22G1',NULL,NULL,'','CCLU7908356',NULL,'F','22G1',NULL,NULL,'',NULL,NULL,NULL,NULL,'http://127.0.0.1:9527/lorry.jpg,http://127.0.0.1:9527/behind.jpg,http://127.0.0.1:9527/top.jpg,http://127.0.0.1:9527/right1.jpg,http://127.0.0.1:9527/left.jpg,http://127.0.0.1:9527/ahead.jpg,http://127.0.0.1:9527/right2.jpg,http://127.0.0.1:9527/plate.jpg,http://127.0.0.1:9527/left2.jpg,http://127.0.0.1:9527/left1.jpg,http://127.0.0.1:9527/right.jpg',NULL,'Y','1138908','危品','Y','1138908','危品'),('2','control','作业成功111','2019-12-20 08:27:39','2019-12-20 08:32:55','YT-1','WEFSDFWE23456764354','23452','2','闽C92222','',NULL,'CCLU0123422','0.99','A','25G1','0.99','0','F','CCLU6543212','0.99','F','22G1','0.99','0','F','1','1','232739','ftp://192.168.80.250/static','Plate.jpg,A01.jpg,B01.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('2fde0d435da74f40af077ff607ec8d79','huadong','未找到车辆信息','2020-02-06 15:32:30','2020-02-06 15:32:35','YT-1',NULL,'0','2','鲁Y88882','Blue',NULL,'CCLU7908356',NULL,'F','22G1',NULL,NULL,'','CCLU7908356',NULL,'F','22G1',NULL,NULL,'',NULL,NULL,NULL,NULL,'http://127.0.0.1:9527/lorry.jpg,http://127.0.0.1:9527/behind.jpg,http://127.0.0.1:9527/top.jpg,http://127.0.0.1:9527/right1.jpg,http://127.0.0.1:9527/left.jpg,http://127.0.0.1:9527/ahead.jpg,http://127.0.0.1:9527/right2.jpg,http://127.0.0.1:9527/plate.jpg,http://127.0.0.1:9527/left2.jpg,http://127.0.0.1:9527/left1.jpg,http://127.0.0.1:9527/right.jpg',NULL,'Y','1138908','危品','Y','1138908','危品'),('30','control','作业成功111','2019-12-20 08:27:39','2019-12-20 08:32:55','test2','WEFSDFWE23456764354','23452','2','闽C92222','',NULL,'CCLU0123422','0.99','A','25G1','0.99','0','F','CCLU6543212','0.99','F','22G1','0.99','0','F',NULL,NULL,'232739','ftp://192.168.80.250/static','Plate.jpg,A01.jpg,B01.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('5fde0d435da74f40af077ff607ec8d79','huadong','未找到车辆信息','2020-02-06 15:32:30','2020-02-06 15:32:35','YT-1',NULL,'0','2','鲁Y88885','Blue','鲁Y88886','CCLU7908356',NULL,'F','22G1',NULL,NULL,'','CCLU7908356',NULL,'F','22G1',NULL,NULL,'',NULL,NULL,NULL,NULL,'http://127.0.0.1:9527/lorry.jpg,http://127.0.0.1:9527/behind.jpg,http://127.0.0.1:9527/top.jpg,http://127.0.0.1:9527/right1.jpg,http://127.0.0.1:9527/left.jpg,http://127.0.0.1:9527/ahead.jpg,http://127.0.0.1:9527/right2.jpg,http://127.0.0.1:9527/plate.jpg,http://127.0.0.1:9527/left2.jpg,http://127.0.0.1:9527/left1.jpg,http://127.0.0.1:9527/right.jpg',NULL,'Y','1138908','危品','Y','1138908','危品'),('6fde0d435da74f40af077ff607ec8d79','huadong','未找到车辆信息','2020-02-06 15:32:30','2020-02-06 15:32:35','HD_1',NULL,'0','2','鲁Y88886','Blue',NULL,'CCLU7908356',NULL,'F','22G1',NULL,NULL,'','CCLU7908356',NULL,'F','22G1',NULL,NULL,'',NULL,NULL,NULL,NULL,'http://127.0.0.1:9527/lorry.jpg,http://127.0.0.1:9527/behind.jpg,http://127.0.0.1:9527/top.jpg,http://127.0.0.1:9527/right1.jpg,http://127.0.0.1:9527/left.jpg,http://127.0.0.1:9527/ahead.jpg,http://127.0.0.1:9527/right2.jpg,http://127.0.0.1:9527/plate.jpg,http://127.0.0.1:9527/left2.jpg,http://127.0.0.1:9527/left1.jpg,http://127.0.0.1:9527/right.jpg',NULL,'Y','1138908','危品','Y','1138908','危品');
/*!40000 ALTER TABLE `gate_business` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gate_lane`
--

DROP TABLE IF EXISTS `gate_lane`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `gate_lane` (
  `LANE_CODE` varchar(10) NOT NULL COMMENT '车道代码',
  `LANE_NAME` varchar(20) DEFAULT NULL COMMENT '车道名称',
  `LANE_DIRECTION` varchar(10) DEFAULT NULL COMMENT '车道方向',
  `CREATE_TIME` date DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` date DEFAULT NULL COMMENT '更新时间',
  `LANE_PROPERTY` varchar(20) DEFAULT NULL COMMENT '车道性质（散杂货，行政。。。）',
  `LANE_IP` varchar(20) DEFAULT NULL COMMENT '车道ip',
  `LANE_PORT` varchar(10) DEFAULT NULL COMMENT '车道端口',
  `OPERATE_STYLE` varchar(10) DEFAULT NULL COMMENT '作业方式（AUTO/MANUAL)',
  `SHOW_FLAG` varchar(5) DEFAULT NULL COMMENT '禁用N启用Y标志',
  `LANE_DB` int(11) DEFAULT NULL,
  PRIMARY KEY (`LANE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gate_lane`
--

LOCK TABLES `gate_lane` WRITE;
/*!40000 ALTER TABLE `gate_lane` DISABLE KEYS */;
INSERT INTO `gate_lane` VALUES ('HD_1','华东1','进',NULL,NULL,NULL,'127.0.0.1','9000',NULL,'Y',0),('XJ_1','西井1','进',NULL,NULL,NULL,'127.0.0.1','9000',NULL,'Y',1),('XJ_2','西井2','进',NULL,NULL,NULL,'127.0.0.1','9000',NULL,'Y',2);
/*!40000 ALTER TABLE `gate_lane` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dept` (
  `DEPT_ID` varchar(20) NOT NULL,
  `DEPT_CODE` varchar(20) DEFAULT NULL,
  `DEPT_TYPE` varchar(20) DEFAULT NULL,
  `PARENT_DEPT_ID` varchar(20) DEFAULT NULL,
  `DEPT_NAME` varchar(100) DEFAULT NULL,
  `DEPT_SHORT_NAM` varchar(50) DEFAULT NULL,
  `DEPT_EN_NAME` varchar(50) DEFAULT NULL,
  `COMMENTS` varchar(100) DEFAULT NULL,
  `STATE` varchar(20) DEFAULT NULL,
  `SORT_NO` varchar(10) DEFAULT NULL,
  `HASCHILD` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`DEPT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES ('1',NULL,NULL,NULL,'华东电子',NULL,NULL,NULL,'open','0','1'),('1112601666224484353',NULL,NULL,'1','智控部','智控','zhikong','智控部门',NULL,'1',NULL),('1112601729457811457',NULL,NULL,'1','实施部',NULL,NULL,NULL,NULL,'0',NULL);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_resource`
--

DROP TABLE IF EXISTS `sys_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_resource` (
  `RESOURCE_ID` varchar(20) NOT NULL,
  `RESOURCE_NAME` varchar(100) DEFAULT NULL,
  `RESOURCE_EN_NAME` varchar(100) DEFAULT NULL,
  `ICON` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `MENU_NAME` varchar(20) DEFAULT NULL COMMENT '菜单对应页面名称',
  `PARENT_RESOURCE_ID` varchar(20) DEFAULT NULL COMMENT '如果为菜单表示是上级菜单id，如果是按钮表示按钮所在页面',
  `DESCRIBE` varchar(100) DEFAULT NULL COMMENT '描述',
  `SORT_NO` varchar(10) DEFAULT NULL,
  `STATE` varchar(10) DEFAULT NULL,
  `HASCHILD` varchar(10) DEFAULT NULL,
  `RESOURCE_TYPE` varchar(10) DEFAULT NULL COMMENT '资源类型m,b',
  PRIMARY KEY (`RESOURCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_resource`
--

LOCK TABLES `sys_resource` WRITE;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` VALUES ('0','主页','dashboard','fa fa-dashboard','dashboard',NULL,NULL,'1','open',NULL,'m'),('1','业务','service','fa fa-bar-chart',NULL,NULL,NULL,'2','open','1','m'),('11','车道监控',NULL,'fa','monitor','1','车道监控','1','open',NULL,'m'),('12','车道作业','history','fa','task','1','车道作业','2','open',NULL,'m'),('13','历史数据',NULL,'fa','history','1','历史数据','4','open',NULL,'m'),('5','车道管理',NULL,'fa fa-pencil',NULL,NULL,NULL,'5','open','1','m'),('51','车道信息',NULL,'fa','lane','5','车道相关信息','6','open',NULL,'m'),('52','设备监控',NULL,'fa','equipment','5',NULL,'7','open',NULL,'m'),('9','系统管理','sys','fa fa-cog','',NULL,NULL,'9','open','1','m'),('91','用户',NULL,'fa','user','9',NULL,'1','open',NULL,'m'),('92','角色',NULL,'fa','role','9',NULL,'2','open',NULL,'m'),('93','资源',NULL,'fa','resource','9',NULL,'3','open',NULL,'m'),('94','部门',NULL,'fa','dept','9',NULL,'4','open',NULL,'m'),('95','用户角色划分',NULL,'fa','userRole','9',NULL,'5','open',NULL,'m'),('dept_add','部门添加按钮','dept_add',NULL,'dept_add','94',NULL,NULL,'open',NULL,'b'),('dept_delete','部门删除按钮','dept_delete',NULL,'dept_delete','94',NULL,NULL,'open',NULL,'b'),('dept_update','部门修改按钮','dept_update',NULL,'dept_update','94',NULL,NULL,'open',NULL,'b'),('resource_add','资源新增','resource_add',NULL,'resource_add','93',NULL,NULL,'open',NULL,'b'),('resource_update','资源修改','resource_update',NULL,'resource_update','93',NULL,NULL,'open',NULL,'b'),('role_add','角色添加','role_add',NULL,'role_add','92',NULL,NULL,'open',NULL,'b'),('role_btn_change','按钮赋权','role_btn_change',NULL,'role_btn_change','92','按钮是否赋权的开关',NULL,'open',NULL,'b'),('role_delete','角色删除','role_delete',NULL,'role_delete','92',NULL,NULL,'open',NULL,'b'),('role_save','保存按钮','role_save',NULL,'role_save','92','修改菜单后的保存按钮',NULL,'open',NULL,'b'),('role_update','角色修改','role_update',NULL,'role_update','92',NULL,NULL,'open',NULL,'b'),('user_add','用户添加按钮','user_add',NULL,'user_add','91',NULL,NULL,'open',NULL,'b'),('user_delete','用户删除按钮','user_delete',NULL,'user_delete','91','删除单独用户删除按钮',NULL,'open',NULL,'b'),('user_delete_dept','用户删除按钮(部门)','user_delete_dept',NULL,'user_delete_dept','91','删除部门下所有用户的删除按钮',NULL,'open',NULL,'b'),('user_role_change','用户角色划分','user_role_change',NULL,'user_role_change','95','用户角色划分的开关',NULL,'open',NULL,'b'),('user_update','用户修改按钮','user_update',NULL,'user_update','91',NULL,NULL,'open',NULL,'b');
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role` (
  `ROLE_ID` varchar(20) NOT NULL,
  `ROLE_NAME` varchar(100) DEFAULT NULL,
  `PARENT_ROLE_ID` varchar(20) DEFAULT NULL,
  `ROLE_DESCRIPTION` varchar(100) DEFAULT NULL,
  `IS_ADMIN` varchar(10) DEFAULT NULL,
  `ORGN_ID` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('1','管理员',NULL,'管理员角色','1',NULL),('1112602015056359426','测试角色',NULL,'test','0',NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_resource`
--

DROP TABLE IF EXISTS `sys_role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role_resource` (
  `ROLE_ID` varchar(20) DEFAULT NULL,
  `RESOURCE_ID` varchar(20) DEFAULT NULL,
  `RESOURCE_TYPE` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_resource`
--

LOCK TABLES `sys_role_resource` WRITE;
/*!40000 ALTER TABLE `sys_role_resource` DISABLE KEYS */;
INSERT INTO `sys_role_resource` VALUES ('1','dept_add','b'),('1','dept_delete','b'),('1','dept_update','b'),('1','user_add','b'),('1','user_delete','b'),('1','user_delete_dept','b'),('1','user_update','b'),('1','role_btn_change','b'),('1','role_add','b'),('1','role_delete','b'),('1','role_save','b'),('1','role_update','b'),('1','resource_add','b'),('1','resource_update','b'),('1','user_role_change','b'),('1112602015056359426','5','m'),('1112602015056359426','51','m'),('1112602015056359426','91','m'),('1112602015056359426','92','m'),('1112602015056359426','95','m'),('1112602015056359426','user_add','b'),('1','0','m'),('1','1','m'),('1','11','m'),('1','12','m'),('1','13','m'),('1','5','m'),('1','51','m'),('1','52','m'),('1','9','m'),('1','91','m'),('1','92','m'),('1','93','m'),('1','94','m'),('1','95','m');
/*!40000 ALTER TABLE `sys_role_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user` (
  `USER_ID` varchar(20) NOT NULL,
  `ORGN_ID` varchar(20) NOT NULL,
  `ACCOUNT` varchar(20) NOT NULL,
  `ACCOUNT_EXPIRATION_DATE` date DEFAULT NULL,
  `USER_NAME` varchar(100) DEFAULT NULL,
  `USER_PWD` varchar(100) DEFAULT NULL,
  `PWD_NEVER_EXPIRES` varchar(20) DEFAULT NULL,
  `PWD_VALID_DAYS` varchar(20) DEFAULT NULL,
  `USER_DESCRIPTION` varchar(100) DEFAULT NULL,
  `LANGUAGE` varchar(10) DEFAULT NULL,
  `SHOW_LANE` varchar(100) DEFAULT NULL,
  `USER_STATE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('1','1','admin',NULL,'管理员','df655ad8d3229f3269fad2a8bab59b6c',NULL,'0',NULL,NULL,'HD_1,XJ_1,XJ_2,YT-1,test,test2','success'),('1112601891672518658','1112601666224484353','li',NULL,'li','b08a25a8bfcb1f5088fea3b6835569de',NULL,'0',NULL,'zh',NULL,NULL),('1112604151882280962','1112601729457811457','test',NULL,'测试','cbce3bcfb2e685657f30b435a5503190',NULL,'0',NULL,'zh',NULL,NULL),('1128854659100852226','1','CHZ',NULL,'崔皓哲','bbb9fe38367f3086be804b36fd1bb57f',NULL,'0',NULL,NULL,NULL,'success');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user_role` (
  `USER_ID` varchar(20) DEFAULT NULL,
  `ROLE_ID` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES ('1','1'),('1112601891672518658','1112602015056359426'),('1112604151882280962','1112602015056359426'),('1128854659100852226','1');
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'yantai'
--

--
-- Dumping routines for database 'yantai'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-04 14:10:39
