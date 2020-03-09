/*
Navicat Oracle Data Transfer
Oracle Client Version : 10.2.0.5.0

Source Server         : ora
Source Server Version : 110200
Source Host           : 127.0.0.1:1521
Source Schema         : YANTAI

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2020-03-05 14:53:55
*/


-- ----------------------------
-- Table structure for GATE_USER
-- ----------------------------
CREATE TABLE "GATE_USER" (
"USER_ID" VARCHAR2(20 CHAR) NOT NULL ,
"ORGN_ID" VARCHAR2(20 CHAR) NOT NULL ,
"ACCOUNT" VARCHAR2(20 CHAR) NOT NULL ,
"ACCOUNT_EXPIRATION_DATE" DATE NULL ,
"USER_NAME" VARCHAR2(100 CHAR) NULL ,
"USER_PWD" VARCHAR2(100 CHAR) NULL ,
"PWD_NEVER_EXPIRES" VARCHAR2(20 CHAR) NULL ,
"PWD_VALID_DAYS" VARCHAR2(20 CHAR) NULL ,
"USER_DESCRIPTION" VARCHAR2(100 CHAR) NULL ,
"LANGUAGE" VARCHAR2(10 CHAR) NULL ,
"SHOW_LANE" VARCHAR2(100 CHAR) NULL ,
"USER_STATE" VARCHAR2(10 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of GATE_USER
-- ----------------------------
INSERT INTO "GATE_USER" VALUES ('1', '1', 'admin', null, '管理员', 'df655ad8d3229f3269fad2a8bab59b6c', null, '0', null, null, 'HD_1,XJ_1,XJ_2,YT-1,test,test2', 'success');

-- ----------------------------
-- Checks structure for table GATE_USER
-- ----------------------------
ALTER TABLE "GATE_USER" ADD CHECK ("USER_ID" IS NOT NULL);
ALTER TABLE "GATE_USER" ADD CHECK ("ORGN_ID" IS NOT NULL);
ALTER TABLE "GATE_USER" ADD CHECK ("ACCOUNT" IS NOT NULL);
