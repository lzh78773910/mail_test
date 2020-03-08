/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.17 : Database - mail_test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mail_test` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `mail_test`;

/*Table structure for table `broker_message_log` */

DROP TABLE IF EXISTS `broker_message_log`;

CREATE TABLE `broker_message_log` (
  `message_id` varchar(128) NOT NULL,
  `message` varchar(4000) DEFAULT NULL,
  `try_count` int(4) DEFAULT '0',
  `status` varchar(10) DEFAULT '',
  `next_retry` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `broker_message_log` */

insert  into `broker_message_log`(`message_id`,`message`,`try_count`,`status`,`next_retry`,`create_time`,`update_time`) values ('1583643546514$8de77431-bdd7-4476-97e7-a639a9bda515','78773910@qq.com',1,'1','2020-03-08 05:00:07','2020-03-08 04:59:07','2020-03-08 05:14:58'),('1583643751052$5453db32-9132-45d4-b7d6-5045abda18fe','78773910@qq.com',0,'1','2020-03-08 05:03:31','2020-03-08 05:02:31','2020-03-08 05:02:31'),('1583644608708$8783b55d-82ac-4110-99ef-ada27ebdb893','78773910@qq.com',3,'2','2020-03-08 05:17:49','2020-03-08 05:16:49','2020-03-08 05:17:27');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
