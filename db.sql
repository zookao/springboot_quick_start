/*
SQLyog Ultimate v12.4.3 (64 bit)
MySQL - 8.0.14 : Database - quick_start
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`quick_start` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `quick_start`;

/*Table structure for table `qs_menu` */

DROP TABLE IF EXISTS `qs_menu`;

CREATE TABLE `qs_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父菜单主键',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码控制权限标识符',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `sort` tinyint(4) NOT NULL DEFAULT '0' COMMENT '菜单的序号',
  `type` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='菜单表';

/*Data for the table `qs_menu` */

insert  into `qs_menu`(`id`,`parent_id`,`code`,`name`,`sort`,`type`) values 
(2,0,'menu:index','菜单管理',0,1),
(3,2,'menu:add','添加菜单',0,2),
(7,0,'admin:index','管理员管理',0,1),
(8,7,'admin:add','添加管理员',0,2),
(12,0,'role:index','角色管理',0,1),
(13,12,'role:add','添加角色',0,2),
(17,7,'admin:userToRole','分配角色',0,2),
(18,12,'role:roleToMenu','角色绑定权限',0,2),
(19,2,'menu:delete','删除菜单',0,2),
(20,12,'role:delete','删除角色',0,2),
(21,7,'admin:delete','删除管理员',0,2);

/*Table structure for table `qs_operation_log` */

DROP TABLE IF EXISTS `qs_operation_log`;

CREATE TABLE `qs_operation_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `description` varchar(64) DEFAULT NULL COMMENT '日志描述',
  `args` varchar(300) DEFAULT NULL COMMENT '方法参数',
  `user_id` int(11) DEFAULT NULL COMMENT '用户主键',
  `class_name` varchar(300) DEFAULT NULL COMMENT '类名称',
  `method_name` varchar(64) DEFAULT NULL COMMENT '方法名称',
  `ip` varchar(32) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `model_name` varchar(50) DEFAULT NULL COMMENT '模块名称',
  `action` varchar(50) DEFAULT NULL COMMENT '操作',
  `succeed` int(2) DEFAULT NULL COMMENT '是否成功 1:成功 2异常',
  `message` longtext COMMENT '异常堆栈信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='操作日志表';

/*Table structure for table `qs_role` */

DROP TABLE IF EXISTS `qs_role`;

CREATE TABLE `qs_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `qs_role` */

insert  into `qs_role`(`id`,`name`) values 
(1,'sysadmin'),
(2,'admin');

/*Table structure for table `qs_role_to_menu` */

DROP TABLE IF EXISTS `qs_role_to_menu`;

CREATE TABLE `qs_role_to_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色代号',
  `menu_id` int(11) NOT NULL COMMENT '菜单代号,规范权限标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

/*Data for the table `qs_role_to_menu` */

insert  into `qs_role_to_menu`(`id`,`role_id`,`menu_id`) values 
(2,1,2),
(3,1,3),
(4,1,12),
(5,1,13),
(6,1,7),
(7,1,17),
(8,1,18),
(11,1,19),
(12,1,20),
(13,1,21);

/*Table structure for table `qs_user` */

DROP TABLE IF EXISTS `qs_user`;

CREATE TABLE `qs_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(11) NOT NULL COMMENT '是电话号码，也是账号（登录用）',
  `username` varchar(50) NOT NULL COMMENT '姓名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `status` int(2) NOT NULL DEFAULT '2' COMMENT '状态值（1：启用，2：禁用，3：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `qs_user` */

insert  into `qs_user`(`id`,`mobile`,`username`,`password`,`email`,`create_time`,`status`) values 
(1,'17600104430','sysadmin','$2a$10$H5ne99844Ebn8UoYn3SLhOnWAjgNAXhwhZ0LsHKIXqoWlA9mY8F5q','zookao@126.com',1639466277433,1);

/*Table structure for table `qs_user_to_role` */

DROP TABLE IF EXISTS `qs_user_to_role`;

CREATE TABLE `qs_user_to_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `role_id` int(11) NOT NULL COMMENT '角色代号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

/*Data for the table `qs_user_to_role` */

insert  into `qs_user_to_role`(`id`,`user_id`,`role_id`) values 
(1,1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
