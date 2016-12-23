DROP SCHEMA IF EXISTS `r2db` ;
CREATE SCHEMA IF NOT EXISTS `r2db` DEFAULT CHARACTER SET utf8 ;

DROP TABLE IF EXISTS `CsDwellTime` ;

CREATE  TABLE IF NOT EXISTS `CsDwellTime` (
 `idCsDwellTime` BIGINT NOT NULL AUTO_INCREMENT ,
 `idNMSAccount`  BIGINT NOT NULL ,
 `idClientStationMacAddr` VARCHAR(23) NOT NULL ,
 `isVisitor` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '0-false: not visitor , 1-true: vistor ',
 `firstSeen` BIGINT   NOT NULL,
 `lastSeen`  BIGINT   NOT NULL,
  PRIMARY KEY (`idCsDwellTime`) ,
  KEY `idx_acct_vis_cMac` (`idNMSAccount`,`isVisitor`,`idClientStationMacAddr`)
) ENGINE=InnoDB;



-- -----------------------------------------------------
-- Table  student  sql_mode_ansi_outer_ref_st
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sql_mode_ansi_outer_ref_st` ;

CREATE  TABLE IF NOT EXISTS `sql_mode_ansi_outer_ref_st` (
 `id_outer_ref_st` BIGINT NOT NULL AUTO_INCREMENT ,
 `name`  VARCHAR(32) NOT NULL ,
 `age` INT NULL ,
 `avg_age` INT NULL ,
 `class_name`  VARCHAR(32) NOT NULL ,
  PRIMARY KEY (`id_outer_ref_st`)
) ENGINE=InnoDB;

--
-- Dumping data for table `sql_mode_ansi_outer_ref_st`
--

LOCK TABLES `sql_mode_ansi_outer_ref_st` WRITE;
/*!40000 ALTER TABLE `sql_mode_ansi_outer_ref_st` DISABLE KEYS */;
INSERT INTO `sql_mode_ansi_outer_ref_st` VALUES (1,'test_1',20,25,'class_1'),(2,'test_2',30,25,'class_1'),(3,'test_3',18,25,'class_1'),(4,'test_4',19,22,'class_2'),(5,'test_5',20,22,'class_2');
/*!40000 ALTER TABLE `sql_mode_ansi_outer_ref_st` ENABLE KEYS */;
UNLOCK TABLES;

-- -----------------------------------------------------
-- Table  class  sql_mode_ansi_outer_ref_cl
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sql_mode_ansi_outer_ref_class` ;

CREATE  TABLE IF NOT EXISTS `sql_mode_ansi_outer_ref_class` (
 `id_outer_ref_cl` BIGINT NOT NULL AUTO_INCREMENT ,
 `class_name`  VARCHAR(32) NOT NULL ,
  PRIMARY KEY (`id_outer_ref_cl`)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table  class  sql_mode_only_group_by
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sql_mode_only_group_by` ;

CREATE  TABLE IF NOT EXISTS `sql_mode_only_group_by` (
 `id_group_by` BIGINT NOT NULL AUTO_INCREMENT ,
 `name`  VARCHAR(32) NOT NULL ,
 `name_null`  VARCHAR(32) NULL ,
 `age_col` INT NULL,
 `int_col` INT NULL,
  PRIMARY KEY (`id_group_by`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `null_name_UNIQUE` (`name_null`)
) ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table sql_mode_strict_trans_tables
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sql_mode_strict` ;

CREATE  TABLE IF NOT EXISTS `sql_mode_strict` (
 `id_strict` BIGINT NOT NULL AUTO_INCREMENT ,
 `date_col`  DATE  NULL ,
 `date_time_col`   DATETIME NULL ,
 `time_stamp_col`  TIMESTAMP NULL,
 `int_col` INT NULL,
  PRIMARY KEY (`id_strict`)
) ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table NO_AUTO_VALUE_ON_ZERO sql_mode_no_auto_on_zero
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sql_mode_no_auto_on_zero` ;

CREATE  TABLE IF NOT EXISTS `sql_mode_no_auto_on_zero` (
 `id_no_auto_on_zero` BIGINT NOT NULL AUTO_INCREMENT ,
 `name`  VARCHAR(32) NULL ,
  PRIMARY KEY (`id_no_auto_on_zero`)
) ENGINE=InnoDB;

