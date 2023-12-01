/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.108.129
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 192.168.108.129:3307
 Source Schema         : gaoweifeng

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 11/10/2023 11:31:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `role_id` int(0) NOT NULL COMMENT '角色ID',
  `user_id` int(0) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`role_id`, `user_id`) USING BTREE,
  INDEX `user_role_ibfk_2`(`user_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
