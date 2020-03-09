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

Date: 2020-03-05 14:54:04
*/


-- ----------------------------
-- Table structure for GATE_USER_ROLE
-- ----------------------------
CREATE TABLE "GATE_USER_ROLE" (
"USER_ID" VARCHAR2(20 CHAR) NULL ,
"ROLE_ID" VARCHAR2(20 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of GATE_USER_ROLE
-- ----------------------------
INSERT INTO "GATE_USER_ROLE" VALUES ('1', '1');
