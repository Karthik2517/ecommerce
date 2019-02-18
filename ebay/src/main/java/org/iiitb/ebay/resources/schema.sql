-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema student_info
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema student_info
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `student_info` DEFAULT CHARACTER SET utf8 ;
USE `student_info` ;

-- -----------------------------------------------------
-- Table `student_info`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `student_info`.`student` (
  `student_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(70) NOT NULL,
  `roll_no` VARCHAR(20) NOT NULL,
  `marks` FLOAT NOT NULL DEFAULT 0,
  `pic_location` VARCHAR(75) NULL,
  `password` VARCHAR(30) NOT NULL,
  `dob` DATE NOT NULL,
  PRIMARY KEY (`student_id`),
  UNIQUE INDEX `roll_no_UNIQUE` (`roll_no` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `student_info`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `student_info`.`message` (
  `message_id` INT NOT NULL AUTO_INCREMENT,
  `sender_id` INT NOT NULL,
  `receiver_id` INT NOT NULL,
  `msg` VARCHAR(100) NULL,
  PRIMARY KEY (`message_id`),
  INDEX `fk_senderid_idx` (`sender_id` ASC),
  INDEX `fk_receiverid_idx` (`receiver_id` ASC),
  CONSTRAINT `fk_senderid`
    FOREIGN KEY (`sender_id`)
    REFERENCES `student_info`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receiverid`
    FOREIGN KEY (`receiver_id`)
    REFERENCES `student_info`.`student` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
