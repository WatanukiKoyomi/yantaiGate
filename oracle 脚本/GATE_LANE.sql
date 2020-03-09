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

Date: 2020-03-05 14:53:22
*/


-- ----------------------------
-- Table structure for GATE_LANE
-- ----------------------------
CREATE TABLE "GATE_LANE" (
"LANE_CODE" VARCHAR2(10 CHAR) NOT NULL ,
"LANE_NAME" VARCHAR2(20 CHAR) NULL ,
"LANE_DIRECTION" VARCHAR2(10 CHAR) NULL ,
"CREATE_TIME" DATE NULL ,
"UPDATE_TIME" DATE NULL ,
"LANE_PROPERTY" VARCHAR2(20 CHAR) NULL ,
"LANE_IP" VARCHAR2(20 CHAR) NULL ,
"LANE_PORT" VARCHAR2(10 CHAR) NULL ,
"OPERATE_STYLE" VARCHAR2(10 CHAR) NULL ,
"SHOW_FLAG" VARCHAR2(5 CHAR) NULL ,
"LANE_DB" NUMBER(10) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "GATE_LANE"."LANE_CODE" IS '车道代码';
COMMENT ON COLUMN "GATE_LANE"."LANE_NAME" IS '车道名称';
COMMENT ON COLUMN "GATE_LANE"."LANE_DIRECTION" IS '车道方向';
COMMENT ON COLUMN "GATE_LANE"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "GATE_LANE"."UPDATE_TIME" IS '更新时间';
COMMENT ON COLUMN "GATE_LANE"."LANE_PROPERTY" IS '车道性质（散杂货，行政。。。）';
COMMENT ON COLUMN "GATE_LANE"."LANE_IP" IS '车道ip';
COMMENT ON COLUMN "GATE_LANE"."LANE_PORT" IS '车道端口';
COMMENT ON COLUMN "GATE_LANE"."OPERATE_STYLE" IS '作业方式（AUTO/MANUAL)';
COMMENT ON COLUMN "GATE_LANE"."SHOW_FLAG" IS '禁用N启用Y标志';

-- ----------------------------
-- Records of GATE_LANE
-- ----------------------------
INSERT INTO "GATE_LANE" VALUES ('HD_1', '华东1', '进', null, null, '1', '127.0.0.1', '9000', '1', 'Y', '0');

-- ----------------------------
-- Checks structure for table GATE_LANE
-- ----------------------------
ALTER TABLE "GATE_LANE" ADD CHECK ("LANE_CODE" IS NOT NULL);
