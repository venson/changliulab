-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: changliulab
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `acl_permission`
--

DROP TABLE IF EXISTS `acl_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acl_permission` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '编号',
  `pid` char(19) NOT NULL DEFAULT '' COMMENT '所属上级',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '类型(1:菜单,2:按钮)',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限值',
  `path` varchar(100) DEFAULT NULL COMMENT '访问路径',
  `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `status` tinyint DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_permission`
--

LOCK TABLES `acl_permission` WRITE;
/*!40000 ALTER TABLE `acl_permission` DISABLE KEYS */;
INSERT INTO `acl_permission` (`id`, `pid`, `name`, `type`, `permission_value`, `path`, `component`, `icon`, `status`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1','0','全部数据',0,'','','','',NULL,0,'2019-11-15 17:13:06','2019-11-15 17:13:06'),('1195268474480156673','1','权限管理',1,NULL,'/acl','Layout',NULL,NULL,0,'2019-11-15 17:13:06','2019-11-18 13:54:25'),('1195268616021139457','1195268474480156673','User',1,NULL,'user/list','/acl/user/list',NULL,NULL,0,'2019-11-15 17:13:40','2019-11-18 13:53:12'),('1195268788138598401','1195268474480156673','角色管理',1,NULL,'role/list','/acl/role/list',NULL,NULL,0,'2019-11-15 17:14:21','2019-11-15 17:14:21'),('1195268893830864898','1195268474480156673','菜单管理',1,NULL,'menu/list','/acl/menu/list',NULL,NULL,0,'2019-11-15 17:14:46','2019-11-15 17:14:46'),('1195269143060602882','1195268616021139457','查看',2,'user.list','','',NULL,NULL,0,'2019-11-15 17:15:45','2019-11-17 21:57:16'),('1195269295926206466','1195268616021139457','添加',2,'user.add','user/add','/acl/user/form',NULL,NULL,0,'2019-11-15 17:16:22','2019-11-15 17:16:22'),('1195269473479483394','1195268616021139457','修改',2,'user.update','user/update/:id','/acl/user/form',NULL,NULL,0,'2019-11-15 17:17:04','2019-11-15 17:17:04'),('1195269547269873666','1195268616021139457','删除',2,'user.remove','','',NULL,NULL,0,'2019-11-15 17:17:22','2019-11-15 17:17:22'),('1195269821262782465','1195268788138598401','修改',2,'role.update','role/update/:id','/acl/role/form',NULL,NULL,0,'2019-11-15 17:18:27','2019-11-15 17:19:53'),('1195269903542444034','1195268788138598401','查看',2,'role.list','','',NULL,NULL,0,'2019-11-15 17:18:47','2019-11-15 17:18:47'),('1195270037005197313','1195268788138598401','添加',2,'role.add','role/add','/acl/role/form',NULL,NULL,0,'2019-11-15 17:19:19','2019-11-18 11:05:42'),('1195270442602782721','1195268788138598401','删除',2,'role.remove','','',NULL,NULL,0,'2019-11-15 17:20:55','2019-11-15 17:20:55'),('1195270621548568578','1195268788138598401','角色权限',2,'role.acl','role/distribution/:id','/acl/role/roleForm',NULL,NULL,0,'2019-11-15 17:21:38','2019-11-15 17:21:38'),('1195270744097742849','1195268893830864898','查看',2,'permission.list','','',NULL,NULL,0,'2019-11-15 17:22:07','2019-11-15 17:22:07'),('1195270810560684034','1195268893830864898','添加',2,'permission.add','','',NULL,NULL,0,'2019-11-15 17:22:23','2019-11-15 17:22:23'),('1195270862100291586','1195268893830864898','修改',2,'permission.update','','',NULL,NULL,0,'2019-11-15 17:22:35','2019-11-15 17:22:35'),('1195270887933009922','1195268893830864898','删除',2,'permission.remove','','',NULL,NULL,0,'2019-11-15 17:22:41','2019-11-15 17:22:41'),('1195349439240048642','1','member manage',1,'','/member','Layout',NULL,NULL,0,'2019-11-15 22:34:49','2019-11-15 22:34:49'),('1195349699995734017','1195349439240048642','member list',1,NULL,'table','/edu/member/list',NULL,NULL,0,'2019-11-15 22:35:52','2019-11-15 22:35:52'),('1195349810561781761','1195349439240048642','add member',1,NULL,'save','/edu/member/save',NULL,NULL,0,'2019-11-15 22:36:18','2019-11-15 22:36:18'),('1195349876252971010','1195349810561781761','添加',2,'member.add','','',NULL,NULL,0,'2019-11-15 22:36:34','2019-11-15 22:36:34'),('1195349979797753857','1195349699995734017','查看',2,'member.list','','',NULL,NULL,0,'2019-11-15 22:36:58','2019-11-15 22:36:58'),('1195350117270261762','1195349699995734017','修改',2,'member.update','edit/:id','/edu/member/save',NULL,NULL,0,'2019-11-15 22:37:31','2019-11-15 22:37:31'),('1195350188359520258','1195349699995734017','删除',2,'member.remove','','',NULL,NULL,0,'2019-11-15 22:37:48','2019-11-15 22:37:48'),('1195350299365969922','1','课程分类',1,NULL,'/subject','Layout',NULL,NULL,0,'2019-11-15 22:38:15','2019-11-15 22:38:15'),('1195350397751758850','1195350299365969922','课程分类列表',1,NULL,'list','/edu/subject/list',NULL,NULL,0,'2019-11-15 22:38:38','2019-11-15 22:38:38'),('1195350500512206850','1195350299365969922','导入课程分类',1,NULL,'save','/edu/subject/save',NULL,NULL,0,'2019-11-15 22:39:03','2019-11-15 22:39:03'),('1195350612172967938','1195350397751758850','查看',2,'subject.list','','',NULL,NULL,0,'2019-11-15 22:39:29','2019-11-15 22:39:29'),('1195350687590748161','1195350500512206850','导入',2,'subject.import','','',NULL,NULL,0,'2019-11-15 22:39:47','2019-11-15 22:39:47'),('1195350831744782337','1','课程管理',1,NULL,'/course','Layout',NULL,NULL,0,'2019-11-15 22:40:21','2019-11-15 22:40:21'),('1195350919074385921','1195350831744782337','课程列表',1,NULL,'list','/edu/course/list',NULL,NULL,0,'2019-11-15 22:40:42','2019-11-15 22:40:42'),('1195351020463296513','1195350831744782337','发布课程',1,NULL,'info','/edu/course/info',NULL,NULL,0,'2019-11-15 22:41:06','2019-11-15 22:41:06'),('1195351159672246274','1195350919074385921','完成发布',2,'course.publish','publish/:id','/edu/course/publish',NULL,NULL,0,'2019-11-15 22:41:40','2019-11-15 22:44:01'),('1195351326706208770','1195350919074385921','编辑课程',2,'course.update','info/:id','/edu/course/info',NULL,NULL,0,'2019-11-15 22:42:19','2019-11-15 22:42:19'),('1195351566221938690','1195350919074385921','编辑课程大纲',2,'chapter.update','chapter/:id','/edu/course/chapter',NULL,NULL,0,'2019-11-15 22:43:17','2019-11-15 22:43:17'),('1195351862889254913','1','统计分析',1,NULL,'/sta','Layout',NULL,NULL,0,'2019-11-15 22:44:27','2019-11-15 22:44:27'),('1195351968841568257','1195351862889254913','生成统计',1,NULL,'create','/sta/create',NULL,NULL,0,'2019-11-15 22:44:53','2019-11-15 22:44:53'),('1195352054917074946','1195351862889254913','统计图表',1,NULL,'show','/sta/show',NULL,NULL,0,'2019-11-15 22:45:13','2019-11-15 22:45:13'),('1195352127734386690','1195352054917074946','查看',2,'daily.list','','',NULL,NULL,0,'2019-11-15 22:45:30','2019-11-15 22:45:30'),('1195352215768633346','1195351968841568257','生成',2,'daily.add','','',NULL,NULL,0,'2019-11-15 22:45:51','2019-11-15 22:45:51'),('1195352547621965825','1','CMS管理',1,NULL,'/cms','Layout',NULL,NULL,0,'2019-11-15 22:47:11','2019-11-18 10:51:46'),('1195352856645701633','1195353513549205505','查看',2,'banner.list','',NULL,NULL,NULL,0,'2019-11-15 22:48:24','2019-11-15 22:48:24'),('1195352909401657346','1195353513549205505','添加',2,'banner.add','banner/add','/cms/banner/form',NULL,NULL,0,'2019-11-15 22:48:37','2019-11-18 10:52:10'),('1195353051395624961','1195353513549205505','修改',2,'banner.update','banner/update/:id','/cms/banner/form',NULL,NULL,0,'2019-11-15 22:49:11','2019-11-18 10:52:05'),('1195353513549205505','1195352547621965825','Bander列表',1,NULL,'banner/list','/cms/banner/list',NULL,NULL,0,'2019-11-15 22:51:01','2019-11-18 10:51:29'),('1195353672110673921','1195353513549205505','删除',2,'banner.remove','','',NULL,NULL,0,'2019-11-15 22:51:39','2019-11-15 22:51:39'),('1195354076890370050','1','订单管理',1,NULL,'/order','Layout',NULL,NULL,1,'2019-11-15 22:53:15','2019-11-15 22:53:15'),('1195354153482555393','1195354076890370050','订单列表',1,NULL,'list','/order/list',NULL,NULL,1,'2019-11-15 22:53:33','2019-11-15 22:53:58'),('1195354315093282817','1195354153482555393','查看',2,'order.list','','',NULL,NULL,1,'2019-11-15 22:54:12','2019-11-15 22:54:12'),('1196301740985311234','1195268616021139457','分配角色',2,'user.assgin','user/role/:id','/acl/user/roleForm',NULL,NULL,0,'2019-11-18 13:38:56','2019-11-18 13:38:56'),('1538451978459529218','1','Citation',1,NULL,'/scholar','Layout',NULL,NULL,0,'2022-06-19 17:21:49','2022-06-19 17:21:49'),('1538452366378123266','1538451978459529218','new',1,NULL,'info','/edu/scholar/info',NULL,NULL,0,'2022-06-19 17:23:22','2022-06-19 17:23:22'),('1538452758507798530','1538451978459529218','List',1,NULL,'list','/edu/scholar/list',NULL,NULL,0,'2022-06-19 17:24:55','2022-06-19 17:24:55'),('1538453008496705537','1538452758507798530','edit',2,'scholar.update','info/:id','/edu/scholar/info',NULL,NULL,0,'2022-06-19 17:25:55','2022-06-19 17:25:55'),('1544144710045777921','1','Research',1,NULL,'/research','Layout',NULL,NULL,0,'2022-07-05 10:22:42','2022-07-05 10:22:42'),('1544144867323789314','1538451978459529218','List',1,NULL,'list','/edu/research/list',NULL,NULL,1,'2022-07-05 10:23:20','2022-07-05 10:23:20'),('1544145153702477826','1544144710045777921','Research List',1,NULL,'list','/edu/research/researchList',NULL,NULL,0,'2022-07-05 10:24:28','2022-07-05 10:24:28'),('1544145433848430594','1544145153702477826','Research edit',2,'research.update','researchInfo/:id','/edu/research/researchInfo',NULL,NULL,0,'2022-07-05 10:25:35','2022-07-05 10:25:35'),('1544146180870750210','1544145153702477826','Research publish',2,'research.publish','publish','/edu/research/publish',NULL,NULL,1,'2022-07-05 10:28:33','2022-07-05 10:28:33'),('1544311536268591106','1544311536268591106','methodology',1,NULL,'/methodology','layout',NULL,NULL,0,'2022-07-05 21:25:37','2022-07-05 21:25:37'),('1544312079359655937','1','methodology',1,NULL,'/methodology','Layout',NULL,NULL,0,'2022-07-05 21:27:46','2022-07-05 21:27:46'),('1544312301225754626','1544312079359655937','Methodology list',1,NULL,'list','/edu/methodology/methodologyList',NULL,NULL,0,'2022-07-05 21:28:39','2022-07-05 21:28:39'),('1544313042166333442','1544312301225754626','Edit',2,'methodology.update','methodologyInfo/:id','/edu/methodology/methodologyInfo',NULL,NULL,0,'2022-07-05 21:31:36','2022-07-05 21:31:36'),('1544313530127466498','1544312079359655937','Methodology Publish',1,NULL,'publish/list','/edu/methodology/publish/publishList',NULL,NULL,0,'2022-07-05 21:33:32','2022-07-05 21:33:32'),('1544313805424803842','1544313530127466498','publish',2,'methodology.publish','','',NULL,NULL,0,'2022-07-05 21:34:38','2022-07-05 21:34:38'),('1544321419843977217','1','Activity',1,NULL,'/activity','Layout',NULL,NULL,1,'2022-07-05 22:04:53','2022-07-05 22:04:53'),('1544321568246841346','1544321419843977217','List',1,NULL,'list','/edu/activity/activityList',NULL,NULL,1,'2022-07-05 22:05:29','2022-07-05 22:05:29'),('1544387349332606978','1544144710045777921','publish',1,NULL,'/publish','/edu/research/publish/publishList',NULL,NULL,0,'2022-07-06 02:26:52','2022-07-06 02:26:52'),('1544387787868061698','1544321568246841346','edit',2,'acctivity.update','activityInfo/:id','/edu/activity/activityInfo',NULL,NULL,1,'2022-07-06 02:28:37','2022-07-06 02:28:37'),('1544504947844018177','1544321568246841346','request publish',2,'research.request','','',NULL,NULL,1,'2022-07-06 10:14:10','2022-07-06 10:14:10'),('1544512015661494274','1544145153702477826','request publish',2,'research.request','','',NULL,NULL,0,'2022-07-06 10:42:15','2022-07-06 10:42:15'),('1544682292617830402','1','Activity',1,NULL,'/activity','Layout',NULL,NULL,0,'2022-07-06 21:58:52','2022-07-06 21:58:52'),('1544682397433487361','1544682292617830402','!!!List',1,NULL,'list','/edu/activity/activityList',NULL,NULL,0,'2022-07-06 21:59:17','2022-07-06 21:59:17'),('1544682756235223041','1544682397433487361','Edit',2,'activity.edit','activityInfo/:id','/edu/activity/activityInfo',NULL,NULL,0,'2022-07-06 22:00:43','2022-07-06 22:00:43'),('1544682960921452545','1544682397433487361','New',2,'activity.new','activityInfo','/edu/activity/activityInfo',NULL,NULL,0,'2022-07-06 22:01:31','2022-07-06 22:01:31'),('1544684605457719298','1544682292617830402','publish',1,NULL,'publish','/edu/activity/publish/publishList',NULL,NULL,0,'2022-07-06 22:08:03','2022-07-06 22:08:03');
/*!40000 ALTER TABLE `acl_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_role`
--

DROP TABLE IF EXISTS `acl_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acl_role` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '角色id',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_role`
--

LOCK TABLES `acl_role` WRITE;
/*!40000 ALTER TABLE `acl_role` DISABLE KEYS */;
INSERT INTO `acl_role` (`id`, `role_name`, `role_code`, `remark`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1','superAdmin',NULL,NULL,0,'2019-11-11 13:09:32','2020-08-21 11:59:37'),('1193757683205607426','Lab member',NULL,NULL,0,'2019-11-11 13:09:45','2019-11-18 10:25:44'),('1296656642959503361','Senior lab member',NULL,NULL,0,'2020-08-21 11:53:29','2020-08-21 11:59:51'),('1296658571068469249','Professor',NULL,NULL,0,'2020-08-21 12:01:09','2020-08-21 12:01:09'),('1534892794140942338','lab intern',NULL,NULL,0,'2022-06-09 21:38:54','2022-06-09 21:38:54');
/*!40000 ALTER TABLE `acl_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_role_permission`
--

DROP TABLE IF EXISTS `acl_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acl_role_permission` (
  `id` char(19) NOT NULL DEFAULT '',
  `role_id` char(19) NOT NULL DEFAULT '',
  `permission_id` char(19) NOT NULL DEFAULT '',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_role_permission`
--

LOCK TABLES `acl_role_permission` WRITE;
/*!40000 ALTER TABLE `acl_role_permission` DISABLE KEYS */;
INSERT INTO `acl_role_permission` (`id`, `role_id`, `permission_id`, `gmt_create`, `gmt_modified`) VALUES ('1544385766486142978','1296658571068469249','1','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766490337281','1296658571068469249','1195268474480156673','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766490337282','1296658571068469249','1195268616021139457','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766490337283','1296658571068469249','1195269143060602882','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766490337284','1296658571068469249','1195269295926206466','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766494531585','1296658571068469249','1195269473479483394','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766494531586','1296658571068469249','1195269547269873666','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766494531587','1296658571068469249','1196301740985311234','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766494531588','1296658571068469249','1195268788138598401','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766494531589','1296658571068469249','1195269821262782465','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766494531590','1296658571068469249','1195269903542444034','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766494531591','1296658571068469249','1195270037005197313','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766498725889','1296658571068469249','1195270442602782721','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766498725890','1296658571068469249','1195270621548568578','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766498725891','1296658571068469249','1195349439240048642','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766498725892','1296658571068469249','1195349699995734017','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766498725893','1296658571068469249','1195349979797753857','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766498725894','1296658571068469249','1195350117270261762','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766498725895','1296658571068469249','1195350188359520258','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766502920194','1296658571068469249','1195349810561781761','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766502920195','1296658571068469249','1195349876252971010','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766502920196','1296658571068469249','1195350299365969922','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766502920197','1296658571068469249','1195350397751758850','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766502920198','1296658571068469249','1195350612172967938','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766507114498','1296658571068469249','1195350500512206850','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766507114499','1296658571068469249','1195350687590748161','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766507114500','1296658571068469249','1195350831744782337','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766507114501','1296658571068469249','1195350919074385921','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766507114502','1296658571068469249','1195351159672246274','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766511308801','1296658571068469249','1195351326706208770','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766511308802','1296658571068469249','1195351566221938690','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766511308803','1296658571068469249','1195351862889254913','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766511308804','1296658571068469249','1195351968841568257','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766511308805','1296658571068469249','1195352215768633346','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766511308806','1296658571068469249','1195352054917074946','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766515503106','1296658571068469249','1195352127734386690','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766515503107','1296658571068469249','1195352547621965825','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766515503108','1296658571068469249','1195353513549205505','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766515503109','1296658571068469249','1195352856645701633','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766515503110','1296658571068469249','1195352909401657346','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766515503111','1296658571068469249','1195353051395624961','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766519697409','1296658571068469249','1195353672110673921','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766519697410','1296658571068469249','1538451978459529218','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766519697411','1296658571068469249','1538452366378123266','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766519697412','1296658571068469249','1538452758507798530','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766519697413','1296658571068469249','1538453008496705537','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766519697414','1296658571068469249','1544312079359655937','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766519697415','1296658571068469249','1544312301225754626','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766519697416','1296658571068469249','1544313042166333442','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766519697417','1296658571068469249','1544313530127466498','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766523891714','1296658571068469249','1544313805424803842','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766523891715','1296658571068469249','1544321419843977217','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544385766523891716','1296658571068469249','1544321568246841346','2022-07-06 02:20:35','2022-07-06 02:20:35'),('1544500304363614210','1296656642959503361','1','2022-07-06 09:55:43','2022-07-06 09:55:43'),('1544500304376197122','1296656642959503361','1195350831744782337','2022-07-06 09:55:43','2022-07-06 09:55:43'),('1544500304376197123','1296656642959503361','1195350919074385921','2022-07-06 09:55:43','2022-07-06 09:55:43'),('1544500304380391426','1296656642959503361','1195351159672246274','2022-07-06 09:55:43','2022-07-06 09:55:43'),('1544500304380391427','1296656642959503361','1195351326706208770','2022-07-06 09:55:43','2022-07-06 09:55:43'),('1544500304380391428','1296656642959503361','1195351566221938690','2022-07-06 09:55:43','2022-07-06 09:55:43'),('1544512084259336193','1193757683205607426','1','2022-07-06 10:42:31','2022-07-06 10:42:31'),('1544512084263530498','1193757683205607426','1544144710045777921','2022-07-06 10:42:31','2022-07-06 10:42:31'),('1544512084263530499','1193757683205607426','1544145153702477826','2022-07-06 10:42:31','2022-07-06 10:42:31'),('1544512084267724802','1193757683205607426','1544145433848430594','2022-07-06 10:42:31','2022-07-06 10:42:31');
/*!40000 ALTER TABLE `acl_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_user`
--

DROP TABLE IF EXISTS `acl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acl_user` (
  `id` char(19) NOT NULL COMMENT '会员id',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '微信openid',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `salt` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `token` varchar(100) DEFAULT NULL COMMENT '用户签名',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_user`
--

LOCK TABLES `acl_user` WRITE;
/*!40000 ALTER TABLE `acl_user` DISABLE KEYS */;
INSERT INTO `acl_user` (`id`, `username`, `password`, `nick_name`, `salt`, `token`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1','admin','96e79218965eb72c92a549dd5a330112','admin','',NULL,0,'2019-11-01 10:39:47','2019-11-01 10:39:47'),('1296657852869406722','yang','96e79218965eb72c92a549dd5a330112','龙达达',NULL,NULL,0,'2020-08-21 11:58:18','2020-08-21 11:58:18'),('1296658810076688386','long','96e79218965eb72c92a549dd5a330112','龙达达1',NULL,NULL,0,'2020-08-21 12:02:06','2020-08-21 12:02:06'),('1534887322360979458','hasi','96e79218965eb72c92a549dd5a330112','hasi',NULL,NULL,0,'2022-06-09 21:17:09','2022-06-09 21:17:09');
/*!40000 ALTER TABLE `acl_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acl_user_role`
--

DROP TABLE IF EXISTS `acl_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acl_user_role` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT '主键id',
  `role_id` char(19) NOT NULL DEFAULT '0' COMMENT '角色id',
  `user_id` char(19) NOT NULL DEFAULT '0' COMMENT '用户id',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acl_user_role`
--

LOCK TABLES `acl_user_role` WRITE;
/*!40000 ALTER TABLE `acl_user_role` DISABLE KEYS */;
INSERT INTO `acl_user_role` (`id`, `role_id`, `user_id`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1','1','2',0,'2019-11-11 13:09:53','2019-11-11 13:09:53'),('1296343056387198977','1296341621322825729','1296342997553696769',0,'2020-08-20 15:07:24','2020-08-20 15:07:24'),('1296657892589465601','1296656642959503361','1296657852869406722',1,'2020-08-21 11:58:27','2020-08-21 11:58:27'),('1296659019410206722','1296658571068469249','1296658810076688386',1,'2020-08-21 12:02:56','2020-08-21 12:02:56'),('1544324369215635458','1534892794140942338','1296658810076688386',1,'2022-07-05 22:16:36','2022-07-05 22:16:36'),('1544325099615928321','1193757683205607426','1534887322360979458',1,'2022-07-05 22:19:31','2022-07-05 22:19:31'),('1544333583828881409','1193757683205607426','1534887322360979458',1,'2022-07-05 22:53:13','2022-07-05 22:53:13'),('1544383760748974082','1296658571068469249','1534887322360979458',1,'2022-07-06 02:12:36','2022-07-06 02:12:36'),('1544383837496348673','1296658571068469249','1534887322360979458',0,'2022-07-06 02:12:55','2022-07-06 02:12:55'),('1544500344918339586','1296656642959503361','1296657852869406722',0,'2022-07-06 09:55:52','2022-07-06 09:55:52'),('1544512155600252929','1534892794140942338','1296658810076688386',0,'2022-07-06 10:42:48','2022-07-06 10:42:48'),('1544512155600252930','1193757683205607426','1296658810076688386',0,'2022-07-06 10:42:48','2022-07-06 10:42:48');
/*!40000 ALTER TABLE `acl_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_banner`
--

DROP TABLE IF EXISTS `crm_banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crm_banner` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT 'ID',
  `title` varchar(20) DEFAULT '' COMMENT '标题',
  `image_url` varchar(500) NOT NULL DEFAULT '' COMMENT '图片地址',
  `link_url` varchar(500) DEFAULT '' COMMENT '链接地址',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  `active` tinyint DEFAULT '0' COMMENT '有效状态，0失效，1有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='首页banner表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_banner`
--

LOCK TABLES `crm_banner` WRITE;
/*!40000 ALTER TABLE `crm_banner` DISABLE KEYS */;
INSERT INTO `crm_banner` (`id`, `title`, `image_url`, `link_url`, `sort`, `is_deleted`, `gmt_create`, `gmt_modified`, `active`) VALUES ('1194556896025845762','banner1','http://cellwall.cemps.ac.cn/UPFILE_PATH/upfiles/2018011910365729984.jpg','/course',1,0,'2018-12-30 18:05:32','2018-12-30 10:28:22',1),('1194607458461216769','test2','http://cellwall.cemps.ac.cn/UPFILE_PATH/upfiles/2018012315202578375.jpg','/teacher',2,0,'2019-11-13 21:26:27','2019-11-14 09:12:15',1);
/*!40000 ALTER TABLE `crm_banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_activity`
--

DROP TABLE IF EXISTS `edu_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_activity` (
  `id` char(19) COLLATE utf8mb4_unicode_ci NOT NULL,
  `title` varchar(400) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `activity_date` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `author_member_id` char(19) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `author_member_name` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_member_id` int DEFAULT NULL,
  `is_published` tinyint(1) NOT NULL DEFAULT '0',
  `is_modified` tinyint(1) NOT NULL DEFAULT '1',
  `version` bigint unsigned DEFAULT '1',
  `publish_request` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_activity`
--

LOCK TABLES `edu_activity` WRITE;
/*!40000 ALTER TABLE `edu_activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `edu_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_activity_markdown`
--

DROP TABLE IF EXISTS `edu_activity_markdown`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_activity_markdown` (
  `id` char(19) COLLATE utf8mb4_unicode_ci NOT NULL,
  `markdown` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_activity_markdown`
--

LOCK TABLES `edu_activity_markdown` WRITE;
/*!40000 ALTER TABLE `edu_activity_markdown` DISABLE KEYS */;
/*!40000 ALTER TABLE `edu_activity_markdown` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_activity_published_md`
--

DROP TABLE IF EXISTS `edu_activity_published_md`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_activity_published_md` (
  `id` char(19) COLLATE utf8mb4_unicode_ci NOT NULL,
  `published_md` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_activity_published_md`
--

LOCK TABLES `edu_activity_published_md` WRITE;
/*!40000 ALTER TABLE `edu_activity_published_md` DISABLE KEYS */;
/*!40000 ALTER TABLE `edu_activity_published_md` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_chapter`
--

DROP TABLE IF EXISTS `edu_chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_chapter` (
  `id` char(19) NOT NULL COMMENT '章节ID',
  `course_id` char(19) NOT NULL COMMENT '课程ID',
  `title` varchar(50) NOT NULL COMMENT '章节名称',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '显示排序',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_chapter`
--

LOCK TABLES `edu_chapter` WRITE;
/*!40000 ALTER TABLE `edu_chapter` DISABLE KEYS */;
INSERT INTO `edu_chapter` (`id`, `course_id`, `title`, `sort`, `gmt_create`, `gmt_modified`) VALUES ('1','14','第一章：HTML',0,'2018-12-30 12:27:40','2018-12-30 12:55:30'),('1192252428399751169','1192252213659774977','Chapter One',0,'2018-12-30 09:28:25','2018-12-30 09:28:25'),('1525681593997549570','1525681568466821122','111',1,'2022-05-15 11:36:52','2022-05-15 11:36:52'),('1536721317721788418','1192252213659774977','Chapter two',0,'2022-06-14 22:44:48','2022-06-14 22:44:48'),('1537065083833901057','14','chapter three',3,'2021-12-26 21:30:48','2021-12-26 21:30:48'),('1544500125430411266','1544500068249464833','111',0,'2022-07-06 09:55:00','2022-07-06 09:55:00'),('3','14','第二章：CSS',1,'2018-12-30 12:55:35','2018-12-30 12:27:40');
/*!40000 ALTER TABLE `edu_chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_chapter_section_content`
--

DROP TABLE IF EXISTS `edu_chapter_section_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_chapter_section_content` (
  `id` char(19) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `chapter_section_id` char(19) COLLATE utf8mb4_unicode_ci NOT NULL,
  `chapter_section` tinyint NOT NULL DEFAULT '0' COMMENT '0 for chapter, 1 for section',
  `content` mediumtext COLLATE utf8mb4_unicode_ci,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `version` tinyint DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_chapter_section_content`
--

LOCK TABLES `edu_chapter_section_content` WRITE;
/*!40000 ALTER TABLE `edu_chapter_section_content` DISABLE KEYS */;
INSERT INTO `edu_chapter_section_content` (`id`, `chapter_section_id`, `chapter_section`, `content`, `gmt_create`, `gmt_modified`, `version`) VALUES ('1536726290186579969','1192252428399751169',1,'## Section one','2022-06-14 23:04:33','2022-06-14 23:04:33',1),('1537059626385920001','1537049798716411905',1,'# chapter one\n## section one\n\n```mermaid\ngraph LR\nA[Square Rect] -- Link text --> B((Circle))\nA --> C(Round Rect)\nB --> D{Rhombus}\nC --> D\n```\nYou can render LaTeX mathematical expressions using [KaTeX](https://khan.github.io/KaTeX/):\n\nThe *Gamma function* satisfying $\\Gamma(n) = (n-1)!\\quad\\forall n\\in\\mathbb N$ is via the Euler integral\n\n$$\n\\Gamma(z) = \\int_0^\\infty t^{z-1}e^{-t}dt\\,.\n$$\n\n> You can find more information about **LaTeX** mathematical expressions [here](http://meta.math.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference).\n\n|                |ASCII                          |HTML                         |\n|----------------|-------------------------------|-----------------------------|\n|Single backticks|`\'Isn\'t this fun?\'`            |\'Isn\'t this fun?\'            |\n|Quotes          |`\"Isn\'t this fun?\"`            |\"Isn\'t this fun?\"            |\n|Dashes          |`-- is en-dash, --- is em-dash`|-- is en-dash, --- is em-dash|\n\n\n![f0cfe400c95141d9bf0b54ba96fea03c.jpg](https://changliulab.oss-cn-beijing.aliyuncs.com/f0cfe400c95141d9bf0b54ba96fea03c.jpg)','2021-12-26 21:09:07','2021-12-26 21:09:07',4),('1537063357433208834','1537051156941099009',1,'# 9009\n## section one','2022-06-15 21:23:56','2022-06-15 21:23:56',1),('1537064928967614466','1',0,'# 1 chapter one','2022-06-15 21:30:11','2022-06-15 21:30:11',1),('1537065092318978050','1537065083833901057',0,'# chapter three','2021-12-26 21:30:50','2021-12-26 21:30:50',2),('1544500129087844353','1544500125430411266',0,'','2022-07-06 09:55:01','2022-07-06 09:55:01',1);
/*!40000 ALTER TABLE `edu_chapter_section_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_comment`
--

DROP TABLE IF EXISTS `edu_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_comment` (
  `id` char(19) NOT NULL COMMENT '讲师ID',
  `course_id` varchar(19) NOT NULL DEFAULT '' COMMENT '课程id',
  `teacher_id` char(19) NOT NULL DEFAULT '' COMMENT '讲师id',
  `member_id` varchar(19) NOT NULL DEFAULT '' COMMENT '会员id',
  `nickname` varchar(50) DEFAULT NULL COMMENT '会员昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '会员头像',
  `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_teacher_id` (`teacher_id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_comment`
--

LOCK TABLES `edu_comment` WRITE;
/*!40000 ALTER TABLE `edu_comment` DISABLE KEYS */;
INSERT INTO `edu_comment` (`id`, `course_id`, `teacher_id`, `member_id`, `nickname`, `avatar`, `content`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1194499162790211585','1192252213659774977','1189389726308478977','1','小三123','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','课程很好',0,'2019-11-13 14:16:08','2019-11-13 14:16:08'),('1194898406466420738','1192252213659774977','1189389726308478977','1','小三123','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','11',0,'2019-11-14 16:42:35','2019-11-14 16:42:35'),('1194898484388200450','1192252213659774977','1189389726308478977','1','小三123','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','111',0,'2019-11-14 16:42:53','2019-11-14 16:42:53'),('1195251020861317122','1192252213659774977','1189389726308478977','1',NULL,NULL,'2233',0,'2019-11-15 16:03:45','2019-11-15 16:03:45'),('1195251382720700418','1192252213659774977','1189389726308478977','1',NULL,NULL,'4455',0,'2019-11-15 16:05:11','2019-11-15 16:05:11'),('1195252819177570306','1192252213659774977','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','55',0,'2019-11-15 16:10:53','2019-11-15 16:10:53'),('1195252899448160258','1192252213659774977','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','55',0,'2019-11-15 16:11:13','2019-11-15 16:11:13'),('1195252920587452417','1192252213659774977','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','223344',0,'2019-11-15 16:11:18','2019-11-15 16:11:18'),('1195262128095559681','14','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','11',0,'2019-11-15 16:47:53','2019-11-15 16:47:53'),('1196264505170767874','1192252213659774977','1189389726308478977','1','小三1231','http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132','666666',0,'2019-11-18 11:10:58','2019-11-18 11:10:58');
/*!40000 ALTER TABLE `edu_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_course`
--

DROP TABLE IF EXISTS `edu_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_course` (
  `id` char(19) NOT NULL COMMENT '课程ID',
  `member_id` char(19) NOT NULL COMMENT '课程讲师ID',
  `subject_id` char(19) NOT NULL COMMENT '课程专业ID',
  `subject_parent_id` char(19) NOT NULL COMMENT '课程专业父级ID',
  `title` varchar(50) NOT NULL COMMENT '课程标题',
  `price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '课程销售价格，设置为0则可免费观看',
  `lesson_num` int unsigned NOT NULL DEFAULT '0' COMMENT '总课时',
  `cover` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '课程封面图片路径',
  `buy_count` bigint unsigned NOT NULL DEFAULT '0' COMMENT '销售数量',
  `view_count` bigint unsigned NOT NULL DEFAULT '0' COMMENT '浏览数量',
  `version` bigint unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `status` varchar(10) NOT NULL DEFAULT 'Draft' COMMENT '课程状态 Draft未发布  Normal已发布',
  `is_deleted` tinyint DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  `available` tinyint DEFAULT '0' COMMENT '课程类别，0 免费公开，1免费注册',
  PRIMARY KEY (`id`),
  KEY `idx_title` (`title`),
  KEY `idx_subject_id` (`subject_id`),
  KEY `idx_teacher_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_course`
--

LOCK TABLES `edu_course` WRITE;
/*!40000 ALTER TABLE `edu_course` DISABLE KEYS */;
INSERT INTO `edu_course` (`id`, `member_id`, `subject_id`, `subject_parent_id`, `title`, `price`, `lesson_num`, `cover`, `buy_count`, `view_count`, `version`, `status`, `is_deleted`, `gmt_create`, `gmt_modified`, `available`) VALUES ('1192252213659774977','100','1011','101','实验室制度',0.01,2,'http://p0.itc.cn/images01/20201013/70e293099e2e4f5db099101f5fbe18d0.jpeg',4,387,1,'Normal',0,'2019-11-07 09:27:33','2022-06-13 08:48:42',0),('14','101','1011','101','实验室安全',0.00,3,'http://p0.itc.cn/images01/20201013/70e293099e2e4f5db099101f5fbe18d0.jpeg',3,44,15,'Normal',0,'2018-04-02 18:33:34','2022-06-12 22:09:35',0),('15','100','1021','102','统计学基础',0.00,23,'https://pic1.zhimg.com/80/v2-8910f350aa732f837a939c9d44c388dc_720w.jpg',0,51,17,'Normal',0,'2018-04-02 18:34:32','2022-05-15 13:47:46',0),('1525681568466821122','101','1022','102','R语言基础',0.00,1,'https://pic2.zhimg.com/v2-180f0aa9f777a3bec1320a1f5d09aafd_1440w.jpg?source=172ae18b',0,2,1,'Normal',0,'2022-05-15 11:36:46','2022-05-15 13:48:20',1),('1544500068249464833','100','1011','101','1',0.00,1,'',0,0,1,'Normal',0,'2022-07-06 09:54:46','2022-07-06 09:55:10',0);
/*!40000 ALTER TABLE `edu_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_course_collect`
--

DROP TABLE IF EXISTS `edu_course_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_course_collect` (
  `id` char(19) NOT NULL COMMENT '收藏ID',
  `course_id` char(19) NOT NULL COMMENT '课程讲师ID',
  `member_id` char(19) NOT NULL DEFAULT '' COMMENT '课程专业ID',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程收藏';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_course_collect`
--

LOCK TABLES `edu_course_collect` WRITE;
/*!40000 ALTER TABLE `edu_course_collect` DISABLE KEYS */;
INSERT INTO `edu_course_collect` (`id`, `course_id`, `member_id`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1196269345666019330','1192252213659774977','1',1,'2019-11-18 11:30:12','2019-11-18 11:30:12');
/*!40000 ALTER TABLE `edu_course_collect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_course_description`
--

DROP TABLE IF EXISTS `edu_course_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_course_description` (
  `id` char(19) NOT NULL COMMENT '课程ID',
  `description` text COMMENT '课程简介',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='课程简介';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_course_description`
--

LOCK TABLES `edu_course_description` WRITE;
/*!40000 ALTER TABLE `edu_course_description` DISABLE KEYS */;
INSERT INTO `edu_course_description` (`id`, `description`, `gmt_create`, `gmt_modified`) VALUES ('1104870479077879809','<p>11</p>','2019-03-11 06:23:44','2019-03-11 06:23:44'),('1192252213659774977','# title\n```mermaid\ngraph TD\na --->b\n```','2019-11-07 09:27:33','2022-06-13 08:48:42'),('14','','2019-03-13 06:04:43','2022-06-12 22:09:35'),('15','','2019-03-13 06:03:33','2019-03-13 06:04:22'),('1525681568466821122','<p>1111111111</p>','2022-05-15 11:36:46','2022-05-15 11:36:46'),('1544500068249464833','1111','2022-07-06 09:54:46','2022-07-06 09:54:46');
/*!40000 ALTER TABLE `edu_course_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_member`
--

DROP TABLE IF EXISTS `edu_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_member` (
  `id` char(19) NOT NULL COMMENT '讲师ID',
  `name` varchar(20) NOT NULL COMMENT '讲师姓名',
  `intro` varchar(500) NOT NULL DEFAULT '' COMMENT '讲师简介',
  `career` varchar(500) DEFAULT NULL COMMENT '讲师资历,一句话说明讲师',
  `level` int unsigned NOT NULL COMMENT '头衔 1高级讲师 2首席讲师',
  `avatar` varchar(255) DEFAULT NULL COMMENT '讲师头像',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='讲师';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_member`
--

LOCK TABLES `edu_member` WRITE;
/*!40000 ALTER TABLE `edu_member` DISABLE KEYS */;
INSERT INTO `edu_member` (`id`, `name`, `intro`, `career`, `level`, `avatar`, `sort`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('100','hasi','Professor Muscle','Professor',1,'https://changliulab.oss-cn-beijing.aliyuncs.com/WechatIMG34.jpeg',100,0,'2022-05-20 21:21:19','2022-05-20 21:21:22'),('101','chang liu','Lord of the Ring','Professor',1,'https://changliulab.oss-cn-beijing.aliyuncs.com/f0cfe400c95141d9bf0b54ba96fea03c.jpg',101,0,'2022-05-20 21:25:35','2022-05-20 21:25:37');
/*!40000 ALTER TABLE `edu_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_member_scholar`
--

DROP TABLE IF EXISTS `edu_member_scholar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_member_scholar` (
  `id` char(19) COLLATE utf8mb4_unicode_ci NOT NULL,
  `scholar_id` char(19) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`,`scholar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_member_scholar`
--

LOCK TABLES `edu_member_scholar` WRITE;
/*!40000 ALTER TABLE `edu_member_scholar` DISABLE KEYS */;
INSERT INTO `edu_member_scholar` (`id`, `scholar_id`, `gmt_create`, `gmt_modified`, `name`) VALUES ('100','1540709429594497026','2021-12-26 21:21:19','2021-12-26 21:21:22','hasi'),('101','1540709429594497026','2021-12-26 21:25:35','2021-12-26 21:25:37','chang liu');
/*!40000 ALTER TABLE `edu_member_scholar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_methodology`
--

DROP TABLE IF EXISTS `edu_methodology`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_methodology` (
  `id` char(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `markdown` text COLLATE utf8mb4_unicode_ci,
  `published_md` text COLLATE utf8mb4_unicode_ci,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `language` char(2) COLLATE utf8mb4_unicode_ci DEFAULT 'zh',
  `is_modified` tinyint(1) DEFAULT NULL,
  `publish_request` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_methodology`
--

LOCK TABLES `edu_methodology` WRITE;
/*!40000 ALTER TABLE `edu_methodology` DISABLE KEYS */;
INSERT INTO `edu_methodology` (`id`, `markdown`, `published_md`, `gmt_create`, `gmt_modified`, `language`, `is_modified`, `publish_request`) VALUES ('1','# title',NULL,'2022-07-04 21:43:17','2022-07-05 21:01:28','zh',1,0),('2','# 标题\n## 副标题',NULL,'2022-07-04 21:43:16','2022-07-05 22:00:16','en',1,0);
/*!40000 ALTER TABLE `edu_methodology` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_research`
--

DROP TABLE IF EXISTS `edu_research`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_research` (
  `id` char(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `markdown` text COLLATE utf8mb4_unicode_ci,
  `published_md` text COLLATE utf8mb4_unicode_ci,
  `language` char(2) COLLATE utf8mb4_unicode_ci DEFAULT 'zh',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `is_modified` tinyint(1) DEFAULT NULL,
  `publish_request` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_research`
--

LOCK TABLES `edu_research` WRITE;
/*!40000 ALTER TABLE `edu_research` DISABLE KEYS */;
INSERT INTO `edu_research` (`id`, `markdown`, `published_md`, `language`, `gmt_create`, `gmt_modified`, `is_modified`, `publish_request`) VALUES ('1','# 研究简介\n## 成立时间\n## 简\n# chapter one\n## section one\n\n```mermaid\ngraph LR\nA[Square Rect] -- Link text --> B((Circle))\nA --> C(Round Rect)\nB --> D{Rhombus}\nC --> D\n```\nYou can render LaTeX mathematical expressions using [KaTeX](https://khan.github.io/KaTeX/):\n\nThe *Gamma function* satisfying $\\Gamma(n) = (n-1)!\\quad\\forall n\\in\\mathbb N$ is via the Euler integral\n\n$$\n\\Gamma(z) = \\int_0^\\infty t^{z-1}e^{-t}dt\\,.\n$$\n\n> You can find more information about **LaTeX** mathematical expressions [here](http://meta.math.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference).\n\n|                |ASCII                          |HTML                         |\n|----------------|-------------------------------|-----------------------------|\n|Single backticks|`\'Isn\'t this fun?\'`            |\'Isn\'t this fun?\'            |\n|Quotes          |`\"Isn\'t this fun?\"`            |\"Isn\'t this fun?\"            |\n|Dashes          |`-- is en-dash, --- is em-dash`|-- is en-dash, --- is em-dash|\n\n\n![f0cfe400c95141d9bf0b54ba96fea03c.jpg](https://changliulab.oss-cn-beijing.aliyuncs.com/f0cfe400c95141d9bf0b54ba96fea03c.jpg)','# 研究简介\n## 成立时间\n## 简\n# chapter one\n## section one\n\n```mermaid\ngraph LR\nA[Square Rect] -- Link text --> B((Circle))\nA --> C(Round Rect)\nB --> D{Rhombus}\nC --> D\n```\nYou can render LaTeX mathematical expressions using [KaTeX](https://khan.github.io/KaTeX/):\n\nThe *Gamma function* satisfying $\\Gamma(n) = (n-1)!\\quad\\forall n\\in\\mathbb N$ is via the Euler integral\n\n$$\n\\Gamma(z) = \\int_0^\\infty t^{z-1}e^{-t}dt\\,.\n$$\n\n> You can find more information about **LaTeX** mathematical expressions [here](http://meta.math.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference).\n\n|                |ASCII                          |HTML                         |\n|----------------|-------------------------------|-----------------------------|\n|Single backticks|`\'Isn\'t this fun?\'`            |\'Isn\'t this fun?\'            |\n|Quotes          |`\"Isn\'t this fun?\"`            |\"Isn\'t this fun?\"            |\n|Dashes          |`-- is en-dash, --- is em-dash`|-- is en-dash, --- is em-dash|\n\n\n![f0cfe400c95141d9bf0b54ba96fea03c.jpg](https://changliulab.oss-cn-beijing.aliyuncs.com/f0cfe400c95141d9bf0b54ba96fea03c.jpg)','zh','2022-07-04 21:42:25','2022-07-05 19:41:20',1,0),('2','# Research \n## description','# Research \n## description','en','2022-07-04 21:42:29','2022-07-05 21:09:05',1,0);
/*!40000 ALTER TABLE `edu_research` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_scholar`
--

DROP TABLE IF EXISTS `edu_scholar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_scholar` (
  `id` char(19) COLLATE utf8mb4_unicode_ci NOT NULL,
  `title` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `year` char(4) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `authors` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `publication_date` char(7) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `journal` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `volume` int DEFAULT NULL,
  `issue` int DEFAULT NULL,
  `pages` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `publisher` varchar(60) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `link_text` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `link_url` varchar(400) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_citations` int DEFAULT '0',
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `version` bigint unsigned NOT NULL DEFAULT '1',
  `is_deleted` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_scholar`
--

LOCK TABLES `edu_scholar` WRITE;
/*!40000 ALTER TABLE `edu_scholar` DISABLE KEYS */;
INSERT INTO `edu_scholar` (`id`, `title`, `year`, `authors`, `publication_date`, `journal`, `volume`, `issue`, `pages`, `publisher`, `description`, `link_text`, `link_url`, `total_citations`, `gmt_create`, `gmt_modified`, `version`, `is_deleted`) VALUES ('1540709429594497026','A gene-editing/complementation strategy for tissue-specific lignin reduction while preserving biomass yield','2021','Hasi Yu, Chang Liu, Richard A Dixon','2021-12','Biotechnology for biofuels',14,1,'1-12','BioMed Central','Lignification of secondary cell walls is a major factor conferring recalcitrance of lignocellulosic biomass to deconstruction for fuels and chemicals. Genetic modification can reduce lignin content and enhance saccharification efficiency, but usually at the cost of moderate-to-severe growth penalties. We have developed a method, using a single DNA construct that uses CRISPR–Cas9 gene editing to knock-out expression of an endogenous gene of lignin monomer biosynthesis while at the same time expressing a modified version of the gene’s open reading frame that escapes cutting by the Cas9 system and complements the introduced mutation in a tissue-specific manner. Expressing the complementing open reading frame in vessels allows for the regeneration of Arabidopsis plants with reduced lignin, wild-type biomass yield, and up to fourfold enhancement of cell wall sugar yield per plant. The above phenotypes are seen in both homozygous and bi-allelic heterozygous T1 lines, and are stable over at least four generations. The method provides a rapid approach for generating reduced lignin trees or crops with one single transformation event, and, paired with a range of tissue-specific promoters, provides a general strategy for optimizing loss-of-function traits that are associated with growth penalties. This method should be applicable to any plant species in which transformation and gene editing are feasible and validated vessel-specific promoters are available.','[HTML] from biomedcentral.com','https://biotechnologyforbiofuels.biomedcentral.com/articles/10.1186/s13068-021-02026-5',2,'2021-12-26 22:52:08','2021-12-26 22:53:19',9,0);
/*!40000 ALTER TABLE `edu_scholar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_scholar_citation`
--

DROP TABLE IF EXISTS `edu_scholar_citation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_scholar_citation` (
  `id` char(19) COLLATE utf8mb4_unicode_ci NOT NULL,
  `scholar_id` char(19) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `year` char(4) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `citations` int DEFAULT '0',
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `version` bigint unsigned DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_scholar_citation`
--

LOCK TABLES `edu_scholar_citation` WRITE;
/*!40000 ALTER TABLE `edu_scholar_citation` DISABLE KEYS */;
INSERT INTO `edu_scholar_citation` (`id`, `scholar_id`, `year`, `citations`, `gmt_create`, `gmt_modified`, `version`) VALUES ('1540729490027773953','1540709429594497026','2021',0,'2022-06-26 00:11:50','2022-06-26 00:11:50',1),('1540729490031968257','1540709429594497026','2022',2,'2022-06-26 00:11:50','2022-06-26 00:11:50',1);
/*!40000 ALTER TABLE `edu_scholar_citation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_section`
--

DROP TABLE IF EXISTS `edu_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_section` (
  `id` char(19) COLLATE utf8mb4_unicode_ci NOT NULL,
  `course_id` char(19) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `chapter_id` char(19) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `video_link` tinytext COLLATE utf8mb4_unicode_ci,
  `available` tinyint DEFAULT '0',
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `content` mediumtext COLLATE utf8mb4_unicode_ci,
  `version` bigint unsigned DEFAULT '1',
  `view_count` bigint unsigned DEFAULT '0',
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  `sort` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_section`
--

LOCK TABLES `edu_section` WRITE;
/*!40000 ALTER TABLE `edu_section` DISABLE KEYS */;
INSERT INTO `edu_section` (`id`, `course_id`, `chapter_id`, `title`, `video_link`, `available`, `gmt_create`, `gmt_modified`, `content`, `version`, `view_count`, `is_deleted`, `sort`) VALUES ('1536740526384164866','1192252213659774977','1192252428399751169','section one','<iframe src=\"//player.bilibili.com/player.html?aid=930916458&bvid=BV1HK4y1X76U&cid=349536179&page=1\" scrolling=\"no\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\"> </iframe>',0,'2022-06-15 00:01:07','2022-06-15 00:01:07',NULL,1,0,0,NULL),('1536755975322808322','1192252213659774977','1536721317721788418','section two','section two',0,'2022-06-15 01:02:31','2022-06-15 01:02:31',NULL,1,0,0,NULL),('1537049798716411905','14','1','section one','<iframe src=\"//player.bilibili.com/player.html?aid=930916458&bvid=BV1HK4y1X76U&cid=349536390&page=2\" scrolling=\"no\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\"> </iframe>',0,'2021-12-26 20:30:04','2021-12-26 20:30:04',NULL,3,0,0,NULL),('1537051156941099009','14','3','section one','<iframe src=\"//player.bilibili.com/player.html?aid=930916458&bvid=BV1HK4y1X76U&cid=349536390&page=2\" scrolling=\"no\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\"> </iframe>',0,'2021-12-26 20:35:28','2021-12-26 20:35:28',NULL,3,0,0,NULL);
/*!40000 ALTER TABLE `edu_section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_subject`
--

DROP TABLE IF EXISTS `edu_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_subject` (
  `id` char(19) NOT NULL COMMENT '课程类别ID',
  `title` varchar(10) NOT NULL COMMENT '类别名称',
  `parent_id` char(19) NOT NULL DEFAULT '0' COMMENT '父ID',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程科目';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_subject`
--

LOCK TABLES `edu_subject` WRITE;
/*!40000 ALTER TABLE `edu_subject` DISABLE KEYS */;
INSERT INTO `edu_subject` (`id`, `title`, `parent_id`, `sort`, `gmt_create`, `gmt_modified`) VALUES ('101','实验','0',101,'2022-05-20 21:34:27','2022-05-20 21:34:29'),('1011','注意事项','101',201,'2022-05-20 21:35:47','2022-05-20 21:35:48'),('102','统计学','0',102,'2022-05-20 21:36:35','2022-05-20 21:36:36'),('1021','回归分析','102',202,'2022-05-20 21:37:34','2022-05-20 21:37:35'),('1022','R','102',203,'2022-05-20 21:39:02','2022-05-20 21:39:03');
/*!40000 ALTER TABLE `edu_subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edu_video`
--

DROP TABLE IF EXISTS `edu_video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edu_video` (
  `id` char(19) NOT NULL COMMENT '视频ID',
  `course_id` char(19) NOT NULL COMMENT '课程ID',
  `chapter_id` char(19) NOT NULL COMMENT '章节ID',
  `title` varchar(50) NOT NULL COMMENT '节点名称',
  `video_source_id` varchar(100) DEFAULT NULL COMMENT '云端视频资源',
  `video_original_name` varchar(100) DEFAULT NULL COMMENT '原始文件名称',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `play_count` bigint unsigned NOT NULL DEFAULT '0' COMMENT '播放次数',
  `is_free` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否可以试听：0收费 1免费',
  `duration` float NOT NULL DEFAULT '0' COMMENT '视频时长（秒）',
  `status` varchar(20) NOT NULL DEFAULT 'Empty' COMMENT 'Empty未上传 Transcoding转码中  Normal正常',
  `size` bigint unsigned NOT NULL DEFAULT '0' COMMENT '视频源文件大小（字节）',
  `version` bigint unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_chapter_id` (`chapter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='课程视频';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edu_video`
--

LOCK TABLES `edu_video` WRITE;
/*!40000 ALTER TABLE `edu_video` DISABLE KEYS */;
INSERT INTO `edu_video` (`id`, `course_id`, `chapter_id`, `title`, `video_source_id`, `video_original_name`, `sort`, `play_count`, `is_free`, `duration`, `status`, `size`, `version`, `gmt_create`, `gmt_modified`) VALUES ('1185312444399071234','14','1','12','','',0,0,0,0,'Empty',0,1,'2019-10-19 05:51:23','2019-10-19 05:51:33'),('1192252824606289921','1192252213659774977','1192252428399751169','第一课时','756cf06db9cb4f30be85a9758b19c645','eae2b847ef8503b81f5d5593d769dde2.mp4',0,0,0,0,'Empty',0,1,'2019-11-07 09:29:59','2019-11-07 09:29:59'),('1192628092797730818','1192252213659774977','1192252428399751169','第二课时','2a02d726622f4c7089d44cb993c531e1','eae2b847ef8503b81f5d5593d769dde2.mp4',0,0,1,0,'Empty',0,1,'2019-11-08 10:21:10','2019-11-08 10:21:22'),('1192632495013380097','1192252213659774977','1192252428399751169','第三课时','4e560c892fdf4fa2b42e0671aa42fa9d','eae2b847ef8503b81f5d5593d769dde2.mp4',0,0,1,0,'Empty',0,1,'2019-11-08 10:38:40','2019-11-08 10:38:40'),('1194117638832111617','1192252213659774977','1192252428399751169','第四课时','4e560c892fdf4fa2b42e0671aa42fa9d','eae2b847ef8503b81f5d5593d769dde2.mp4',0,0,0,0,'Empty',0,1,'2019-11-12 13:00:05','2019-11-12 13:00:05'),('1196263770832023554','1192252213659774977','1192252428399751169','第五课时','27d21158b0834cb5a8d50710937de330','eae2b847ef8503b81f5d5593d769dde2.mp4',5,0,0,0,'Empty',0,1,'2019-11-18 11:08:03','2019-11-18 11:08:03'),('1525681625874259970','1525681568466821122','1525681593997549570','1.1111',NULL,NULL,1,0,1,0,'Empty',0,1,'2022-05-15 11:37:00','2022-05-15 11:37:00');
/*!40000 ALTER TABLE `edu_video` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lab_record`
--

DROP TABLE IF EXISTS `lab_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lab_record` (
  `id` char(19) COLLATE utf8mb4_unicode_ci NOT NULL,
  `title` varchar(400) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `author_id` char(19) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `author_name` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `version` bigint unsigned DEFAULT '1',
  `column_8` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lab_record`
--

LOCK TABLES `lab_record` WRITE;
/*!40000 ALTER TABLE `lab_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `lab_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lab_record_content`
--

DROP TABLE IF EXISTS `lab_record_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lab_record_content` (
  `id` char(19) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text COLLATE utf8mb4_unicode_ci,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lab_record_content`
--

LOCK TABLES `lab_record_content` WRITE;
/*!40000 ALTER TABLE `lab_record_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `lab_record_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ucenter_member`
--

DROP TABLE IF EXISTS `ucenter_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ucenter_member` (
  `id` char(19) NOT NULL COMMENT '会员id',
  `openid` varchar(128) DEFAULT NULL COMMENT '微信openid',
  `email` varchar(60) DEFAULT '' COMMENT '邮箱',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `sex` tinyint unsigned DEFAULT NULL COMMENT '性别 1 女，2 男',
  `age` tinyint unsigned DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `sign` varchar(100) DEFAULT NULL COMMENT '用户签名',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 1（true）已禁用，  0（false）未禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ucenter_member`
--

LOCK TABLES `ucenter_member` WRITE;
/*!40000 ALTER TABLE `ucenter_member` DISABLE KEYS */;
INSERT INTO `ucenter_member` (`id`, `openid`, `email`, `password`, `nickname`, `sex`, `age`, `avatar`, `sign`, `is_disabled`, `is_deleted`, `gmt_create`, `gmt_modified`) VALUES ('1',NULL,'13700000001','$2a$10$S7ZwODxXQRyOO0HrqVWHMeQ35fbLHz/w8XG4Wy5U3KQKhvtiwv/5e','小三123',1,5,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-01 12:11:33','2019-11-08 11:56:01'),('1080736474267144193',NULL,'13700000011','96e79218965eb72c92a549dd5a330112','用户XJtDfaYeKk',1,19,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-02 12:12:45','2019-01-02 12:12:56'),('1080736474355224577',NULL,'13700000002','96e79218965eb72c92a549dd5a330112','用户wUrNkzAPrc',1,27,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-02 12:13:56','2019-01-02 12:14:07'),('1086387099449442306',NULL,'13520191388','96e79218965eb72c92a549dd5a330112','用户XTMUeHDAoj',2,20,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1086387099520745473',NULL,'13520191389','96e79218965eb72c92a549dd5a330112','用户vSdKeDlimn',1,21,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1086387099608825858',NULL,'13520191381','96e79218965eb72c92a549dd5a330112','用户EoyWUVXQoP',1,18,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1086387099701100545',NULL,'13520191382','96e79218965eb72c92a549dd5a330112','用户LcAYbxLNdN',2,24,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1086387099776598018',NULL,'13520191383','96e79218965eb72c92a549dd5a330112','用户dZdjcgltnk',2,25,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1086387099852095490',NULL,'13520191384','96e79218965eb72c92a549dd5a330112','用户wNHGHlxUwX',2,23,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1293112972314537985',NULL,'13206203240','96e79218965eb72c92a549dd5a330112','龙达达',NULL,NULL,'https://edu-longyang.oss-cn-beijing.aliyuncs.com/fa104ef58c4e5bc4270d911da1d1b34d.jpg',NULL,0,0,'2020-08-12 17:12:12','2020-08-11 17:12:12'),('1293479645739515905','o3_SC5100iQtZIOEa1O6_wwhB61I','',NULL,'龙达达',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/UEVqZKDCKVXJiazYbOM1A8WX4STK0UtqCygsAicEMQvCeyb7rKwUgLdo4efTVcERe21w2fOWw3KFbxXmMqfM4FOQ/132',NULL,0,0,'2020-08-12 17:29:14','2020-08-12 17:29:14'),('1293485029254529025','o3_SC54LD44rVqQ1PohBLgQD4PhE','',NULL,'牵',NULL,NULL,'http://thirdwx.qlogo.cn/mmopen/vi_32/AvAFZY0bJOibR2oHmjf03ojAfCUyiaI8iaqGWkuTCp81Y2LpjFw6fcIyduYSf3VUrF5der96A2flYdtUmqCe1bhlQ/132',NULL,0,0,'2020-08-12 17:50:37','2020-08-12 17:50:37'),('1294176575985618946',NULL,'18584807318','96e79218965eb72c92a549dd5a330112','廖媛媛',NULL,NULL,'https://edu-longyang.oss-cn-beijing.aliyuncs.com/fa104ef58c4e5bc4270d911da1d1b34d.jpg',NULL,0,0,'2020-08-14 15:38:35','2020-08-14 15:38:35'),('1295360096565501954',NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,0,0,'2020-08-17 22:01:28','2020-08-17 22:01:28'),('1295360104345935873',NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,0,0,'2020-08-17 22:01:30','2020-08-17 22:01:30'),('1295360157382909954',NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,0,0,'2020-08-17 22:01:43','2020-08-17 22:01:43'),('1529852065836388353',NULL,'406574996@qq.com','$2a$10$w7LhyL06o1tghpXL4Ja6u.UKQlki4ucIO0.N2E6SVnPbo7AhsIggu','venson',NULL,NULL,NULL,NULL,0,0,'2022-05-26 23:48:50','2022-05-26 23:48:50');
/*!40000 ALTER TABLE `ucenter_member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-06 23:10:29
