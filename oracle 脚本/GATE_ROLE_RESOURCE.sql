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

Date: 2020-03-05 14:53:47
*/


-- ----------------------------
-- Table structure for GATE_ROLE_RESOURCE
-- ----------------------------
CREATE TABLE "GATE_ROLE_RESOURCE" (
"ROLE_ID" VARCHAR2(20 CHAR) NULL ,
"RESOURCE_ID" VARCHAR2(20 CHAR) NULL ,
"RESOURCE_TYPE" VARCHAR2(10 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of GATE_ROLE_RESOURCE
-- ----------------------------
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'dept_add', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'dept_delete', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'dept_update', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'user_add', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'user_delete', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'user_delete_dept', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'user_update', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'role_btn_change', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'role_add', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'role_delete', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'role_save', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'role_update', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'resource_add', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'resource_update', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', 'user_role_change', 'b');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '0', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '1', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '11', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '12', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '13', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '5', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '51', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '52', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '9', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '91', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '92', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '93', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '94', 'm');
INSERT INTO "GATE_ROLE_RESOURCE" VALUES ('1', '95', 'm');
