/*
Navicat MySQL Data Transfer

Source Server         : MySql
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2016-04-08 11:05:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_test`
-- ----------------------------
DROP TABLE IF EXISTS `t_test`;
CREATE TABLE `t_test` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `test_name` varchar(20) DEFAULT NULL,
  `test_value` varchar(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_test
-- ----------------------------
INSERT INTO `t_test` VALUES ('2', '2', '2', '2016-04-08 10:20:38');
INSERT INTO `t_test` VALUES ('3', '3', '3', '2016-03-30 16:32:10');
INSERT INTO `t_test` VALUES ('4', '3', '3', '2016-03-30 16:38:04');
INSERT INTO `t_test` VALUES ('6', '3', '3', '2016-03-30 17:29:01');
INSERT INTO `t_test` VALUES ('8', '4', '4', '2016-03-30 17:30:35');
INSERT INTO `t_test` VALUES ('9', '4', '4', '2016-03-30 16:21:45');
INSERT INTO `t_test` VALUES ('10', '5', '5', '2016-03-31 11:37:56');
INSERT INTO `t_test` VALUES ('11', '5', '5', '2016-03-30 16:21:45');
INSERT INTO `t_test` VALUES ('12', '6', '6', '2016-04-07 17:44:55');
INSERT INTO `t_test` VALUES ('14', '6', '6', '2016-03-31 13:15:56');
INSERT INTO `t_test` VALUES ('15', 'page', 'page1', '2016-04-07 15:19:17');
INSERT INTO `t_test` VALUES ('16', 'page', 'page2', '2016-04-07 15:19:20');
INSERT INTO `t_test` VALUES ('17', 'page', 'page3', '2016-04-07 15:19:21');
INSERT INTO `t_test` VALUES ('18', 'page', 'page4', '2016-04-07 15:19:22');
INSERT INTO `t_test` VALUES ('19', 'page', 'page5', '2016-04-07 15:19:22');
INSERT INTO `t_test` VALUES ('20', 'page', 'page6', '2016-04-07 15:19:23');
INSERT INTO `t_test` VALUES ('21', 'page', 'page7', '2016-04-07 15:19:26');
INSERT INTO `t_test` VALUES ('23', 'page', 'page8', '2016-04-07 15:19:46');
INSERT INTO `t_test` VALUES ('24', 'page', 'page9', '2016-04-07 15:19:50');
INSERT INTO `t_test` VALUES ('25', 'page', 'page10', '2016-04-07 15:19:57');
INSERT INTO `t_test` VALUES ('26', 'page', 'page11', '2016-04-07 15:19:59');
INSERT INTO `t_test` VALUES ('27', 'page', 'page12', '2016-04-07 15:20:00');
INSERT INTO `t_test` VALUES ('28', 'page', 'page13', '2016-04-07 15:20:01');
INSERT INTO `t_test` VALUES ('29', 'page', 'page14', '2016-04-07 15:20:03');
INSERT INTO `t_test` VALUES ('30', 'persistNum', 'persistNum', null);
INSERT INTO `t_test` VALUES ('32', 'dynamicMerge', 'dynamicMerge', '2016-04-07 16:38:59');
INSERT INTO `t_test` VALUES ('33', 'merge', 'merge', null);
INSERT INTO `t_test` VALUES ('34', 'persistObj', 'persistObj', '2016-04-07 16:30:05');
INSERT INTO `t_test` VALUES ('35', 'batch', 'batchUpdate1', '2016-04-08 10:20:56');
INSERT INTO `t_test` VALUES ('36', 'batch', 'batchUpdate2', '2016-04-08 10:20:56');
INSERT INTO `t_test` VALUES ('37', 'batch', 'batchUpdate3', '2016-04-08 10:20:56');
INSERT INTO `t_test` VALUES ('38', 'batch', 'batchUpdate4', '2016-04-08 10:20:56');

-- ----------------------------
-- Table structure for `t_test_pro`
-- ----------------------------
DROP TABLE IF EXISTS `t_test_pro`;
CREATE TABLE `t_test_pro` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) DEFAULT NULL,
  `value` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_test_pro
-- ----------------------------
INSERT INTO `t_test_pro` VALUES ('1', '1', '11');
INSERT INTO `t_test_pro` VALUES ('2', '张三', 'is sb +1');

-- ----------------------------
-- Procedure structure for `pro_test1`
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_test1`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_test1`(proName VARCHAR(15),proValue VARCHAR(20))
BEGIN
START TRANSACTION;
INSERT t_test_pro(name,value)VALUE(proName,concat(proValue,'1'));
COMMIT;
SELECT id FROM t_test_pro where name=proName;
END
;;
DELIMITER ;
