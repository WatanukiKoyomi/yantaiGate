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

Date: 2020-03-05 14:53:31
*/


-- ----------------------------
-- Table structure for GATE_RESOURCE
-- ----------------------------
CREATE TABLE "GATE_RESOURCE" (
"RESOURCE_ID" VARCHAR2(20 CHAR) NOT NULL ,
"RESOURCE_NAME" VARCHAR2(100 CHAR) NULL ,
"RESOURCE_EN_NAME" VARCHAR2(100 CHAR) NULL ,
"ICON" VARCHAR2(50 CHAR) NULL ,
"MENU_NAME" VARCHAR2(20 CHAR) NULL ,
"PARENT_RESOURCE_ID" VARCHAR2(20 CHAR) NULL ,
"DESCRIBE" VARCHAR2(100 CHAR) NULL ,
"SORT_NO" VARCHAR2(10 CHAR) NULL ,
"STATE" VARCHAR2(10 CHAR) NULL ,
"HASCHILD" VARCHAR2(10 CHAR) NULL ,
"RESOURCE_TYPE" VARCHAR2(10 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "GATE_RESOURCE"."ICON" IS '菜单图标';
COMMENT ON COLUMN "GATE_RESOURCE"."MENU_NAME" IS '菜单对应页面名称';
COMMENT ON COLUMN "GATE_RESOURCE"."PARENT_RESOURCE_ID" IS '如果为菜单表示是上级菜单id，如果是按钮表示按钮所在页面';
COMMENT ON COLUMN "GATE_RESOURCE"."DESCRIBE" IS '描述';
COMMENT ON COLUMN "GATE_RESOURCE"."RESOURCE_TYPE" IS '资源类型m,b';

-- ----------------------------
-- Records of GATE_RESOURCE
-- ----------------------------
INSERT INTO "GATE_RESOURCE" VALUES ('0', '主页', 'dashboard', 'fa fa-dashboard', 'dashboard', null, null, '1', 'open', null, 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('1', '业务', 'service', 'fa fa-bar-chart', null, null, null, '2', 'open', '1', 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('11', '车道监控', null, 'fa', 'monitor', '1', '车道监控', '1', 'open', null, 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('12', '车道作业', 'history', 'fa', 'task', '1', '车道作业', '2', 'open', null, 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('13', '历史数据', null, 'fa', 'history', '1', '历史数据', '4', 'open', null, 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('5', '车道管理', null, 'fa fa-pencil', null, null, null, '5', 'open', '1', 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('51', '车道信息', null, 'fa', 'lane', '5', '车道相关信息', '6', 'open', null, 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('52', '设备监控', null, 'fa', 'equipment', '5', null, '7', 'open', null, 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('9', '系统管理', 'sys', 'fa fa-cog', null, null, null, '9', 'open', '1', 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('91', '用户', null, 'fa', 'user', '9', null, '1', 'open', null, 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('92', '角色', null, 'fa', 'role', '9', null, '2', 'open', null, 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('93', '资源', null, 'fa', 'resource', '9', null, '3', 'open', null, 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('94', '部门', null, 'fa', 'dept', '9', null, '4', 'open', null, 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('95', '用户角色划分', null, 'fa', 'userRole', '9', null, '5', 'open', null, 'm');
INSERT INTO "GATE_RESOURCE" VALUES ('dept_add', '部门添加按钮', 'dept_add', null, 'dept_add', '94', null, null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('dept_delete', '部门删除按钮', 'dept_delete', null, 'dept_delete', '94', null, null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('dept_update', '部门修改按钮', 'dept_update', null, 'dept_update', '93', null, null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('resource_add', '资源新增', 'resource_add', null, 'resource_add', '93', null, null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('resource_update', '资源修改', 'resource_update', null, 'resource_update', '93', null, null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('role_add', '角色添加', 'role_add', null, 'role_add', '92', null, null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('role_btn_change', '按钮赋权', 'role_btn_change', null, 'role_btn_change', '92', null, null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('role_delete', '角色删除', 'role_delete', null, 'role_delete', '92', null, null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('role_save', '保存按钮', 'role_save', null, 'role_save', '92', null, null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('role_update', '角色修改', 'role_update', null, 'role_update', '92', null, null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('user_add', '用户添加按钮', 'user_add', null, 'user_add', '91', null, null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('user_delete', '用户删除按钮', 'user_delete', null, 'user_delete', '91', '删除单独用户删除按钮', null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('user_delete_dept', '用户删除按钮(部门)', 'user_delete_dept', null, 'user_delete_dept', '91', '删除部门下所有用户的删除按钮', null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('user_role_change', '用户角色划分', 'user_role_change', null, 'user_role_change', '95', '用户角色划分的开关', null, 'open', null, 'b');
INSERT INTO "GATE_RESOURCE" VALUES ('user_update', '用户修改按钮', 'user_update', null, 'user_update', '91', null, null, 'open', null, 'b');

-- ----------------------------
-- Checks structure for table GATE_RESOURCE
-- ----------------------------
ALTER TABLE "GATE_RESOURCE" ADD CHECK ("RESOURCE_ID" IS NOT NULL);
