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

Date: 2020-03-05 14:53:15
*/


-- ----------------------------
-- Table structure for GATE_DEPT
-- ----------------------------
CREATE TABLE "GATE_DEPT" (
"DEPT_ID" VARCHAR2(20 CHAR) NOT NULL ,
"DEPT_CODE" VARCHAR2(20 CHAR) NULL ,
"DEPT_TYPE" VARCHAR2(20 CHAR) NULL ,
"PARENT_DEPT_ID" VARCHAR2(20 CHAR) NULL ,
"DEPT_NAME" VARCHAR2(100 CHAR) NULL ,
"DEPT_SHORT_NAM" VARCHAR2(50 CHAR) NULL ,
"DEPT_EN_NAME" VARCHAR2(50 CHAR) NULL ,
"COMMENTS" VARCHAR2(100 CHAR) NULL ,
"STATE" VARCHAR2(20 CHAR) NULL ,
"SORT_NO" VARCHAR2(10 CHAR) NULL ,
"HASCHILD" VARCHAR2(10 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of GATE_DEPT
-- ----------------------------
INSERT INTO "GATE_DEPT" VALUES ('1', null, null, null, '华东电子', null, null, null, 'open', '0', '1');
INSERT INTO "GATE_DEPT" VALUES ('1112601666224484353', null, null, '1', '智控部', '智控', 'zhikong', '智控部门', null, '1', null);
INSERT INTO "GATE_DEPT" VALUES ('1112601729457811457', null, null, '1', '实施部', null, null, null, null, '0', null);

-- ----------------------------
-- Indexes structure for table GATE_DEPT
-- ----------------------------

-- ----------------------------
-- Checks structure for table GATE_DEPT
-- ----------------------------
ALTER TABLE "GATE_DEPT" ADD CHECK ("DEPT_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table GATE_DEPT
-- ----------------------------
ALTER TABLE "GATE_DEPT" ADD PRIMARY KEY ("DEPT_ID");
