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

Date: 2020-03-05 14:53:39
*/


-- ----------------------------
-- Table structure for GATE_ROLE
-- ----------------------------
CREATE TABLE "GATE_ROLE" (
"ROLE_ID" VARCHAR2(20 CHAR) NOT NULL ,
"ROLE_NAME" VARCHAR2(100 CHAR) NULL ,
"PARENT_ROLE_ID" VARCHAR2(20 CHAR) NULL ,
"ROLE_DESCRIPTION" VARCHAR2(100 CHAR) NULL ,
"IS_ADMIN" VARCHAR2(10 CHAR) NULL ,
"ORGN_ID" VARCHAR2(20 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of GATE_ROLE
-- ----------------------------
INSERT INTO "GATE_ROLE" VALUES ('1', '管理员', null, '管理员角色', '1', null);
INSERT INTO "GATE_ROLE" VALUES ('1112602015056359426', '测试角色', null, 'test', '0', null);

-- ----------------------------
-- Checks structure for table GATE_ROLE
-- ----------------------------
ALTER TABLE "GATE_ROLE" ADD CHECK ("ROLE_ID" IS NOT NULL);
