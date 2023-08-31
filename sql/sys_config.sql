/*
 Navicat Premium Data Transfer

 Source Server         : 雨轩服务器
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : eve_corp_manager_cacx

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 31/08/2023 10:43:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '配置名称',
  `value` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '配置值',
  `title` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `update_id` bigint DEFAULT NULL COMMENT '更新人',
  `update_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` bigint DEFAULT NULL COMMENT '创建人',
  `create_by` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=659 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='系统配置表';

SET FOREIGN_KEY_CHECKS = 1;
