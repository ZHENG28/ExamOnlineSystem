/*
 Navicat MySQL Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : exam_online_system

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 03/06/2022 21:17:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`  (
  `answer_id` int NOT NULL AUTO_INCREMENT,
  `answer_sign` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ABC或者1-对',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_correct` int NOT NULL DEFAULT 0 COMMENT '是否是正确答案，1-是',
  `question_id` int NOT NULL,
  PRIMARY KEY (`answer_id`) USING BTREE,
  INDEX `fk_answer_question_1`(`question_id`) USING BTREE,
  CONSTRAINT `fk_answer_question_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 611 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz`  (
  `clazz_id` int NOT NULL AUTO_INCREMENT,
  `clazz_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `major_id` int NOT NULL,
  PRIMARY KEY (`clazz_id`) USING BTREE,
  UNIQUE INDEX `clazz_name`(`clazz_name`) USING BTREE,
  INDEX `fk_clazz_major_1`(`major_id`) USING BTREE,
  CONSTRAINT `fk_clazz_major_1` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for compare_short_answer
-- ----------------------------
DROP TABLE IF EXISTS `compare_short_answer`;
CREATE TABLE `compare_short_answer`  (
  `compare_id` int NOT NULL AUTO_INCREMENT,
  `compare_threshold` float NOT NULL,
  `short_answer_id` int NOT NULL,
  `reply1_id` int NOT NULL,
  `reply2_id` int NOT NULL,
  PRIMARY KEY (`compare_id`) USING BTREE,
  INDEX `fk_compare_short_answer_short_answer_1`(`short_answer_id`) USING BTREE,
  INDEX `fk_compare_short_answer_reply_1`(`reply1_id`) USING BTREE,
  INDEX `fk_compare_short_answer_reply_2`(`reply2_id`) USING BTREE,
  INDEX `compare_id`(`compare_id`) USING BTREE,
  CONSTRAINT `fk_compare_short_answer_reply_1` FOREIGN KEY (`reply1_id`) REFERENCES `test_history_question_reply` (`reply_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_compare_short_answer_reply_2` FOREIGN KEY (`reply2_id`) REFERENCES `test_history_question_reply` (`reply_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_compare_short_answer_short_answer_1` FOREIGN KEY (`short_answer_id`) REFERENCES `short_answer` (`short_answer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 59010 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `major_id` int NOT NULL AUTO_INCREMENT,
  `major_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`major_id`) USING BTREE,
  UNIQUE INDEX `major_name`(`major_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `question_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `subject_id` int NOT NULL,
  `type_id` int NOT NULL,
  PRIMARY KEY (`question_id`) USING BTREE,
  INDEX `fk_question_subject_1`(`subject_id`) USING BTREE,
  INDEX `fk_question_question_type_1`(`type_id`) USING BTREE,
  CONSTRAINT `fk_question_question_type_1` FOREIGN KEY (`type_id`) REFERENCES `question_type` (`type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_question_subject_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 281 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question_type
-- ----------------------------
DROP TABLE IF EXISTS `question_type`;
CREATE TABLE `question_type`  (
  `type_id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`type_id`) USING BTREE,
  UNIQUE INDEX `type_name`(`type_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_type
-- ----------------------------
INSERT INTO `question_type` VALUES (1, '选择题', 'choice question');
INSERT INTO `question_type` VALUES (2, '判断题', 'judge question');
INSERT INTO `question_type` VALUES (3, '简答题', 'short-answer question');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `role_name`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_ADMIN', '管理员');
INSERT INTO `role` VALUES (2, 'ROLE_TEACHER', '教师');
INSERT INTO `role` VALUES (3, 'ROLE_STUDENT', '学生');

-- ----------------------------
-- Table structure for short_answer
-- ----------------------------
DROP TABLE IF EXISTS `short_answer`;
CREATE TABLE `short_answer`  (
  `short_answer_id` int NOT NULL AUTO_INCREMENT,
  `threshold` int NOT NULL,
  `question_id` int NOT NULL,
  `test_id` int NOT NULL,
  PRIMARY KEY (`short_answer_id`) USING BTREE,
  INDEX `fk_short_answer_question_1`(`question_id`) USING BTREE,
  INDEX `fk_short_answer_test_1`(`test_id`) USING BTREE,
  INDEX `short_answer_id`(`short_answer_id`) USING BTREE,
  CONSTRAINT `fk_short_answer_question_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_short_answer_test_1` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`  (
  `subject_id` int NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `teacher_id` int NOT NULL,
  `clazz_id` int NOT NULL,
  PRIMARY KEY (`subject_id`) USING BTREE,
  UNIQUE INDEX `subject_name`(`subject_name`) USING BTREE,
  INDEX `fk_subject_user_1`(`teacher_id`) USING BTREE,
  INDEX `fk_subject_clazz_1`(`clazz_id`) USING BTREE,
  CONSTRAINT `fk_subject_clazz_1` FOREIGN KEY (`clazz_id`) REFERENCES `clazz` (`clazz_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_subject_user_1` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `test_id` int NOT NULL AUTO_INCREMENT,
  `test_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `begin_date` datetime NULL DEFAULT NULL,
  `end_date` datetime NULL DEFAULT NULL,
  `exam_duration` int NULL DEFAULT NULL COMMENT '测验时长',
  `exam_time` int NULL DEFAULT NULL COMMENT '测验次数',
  `question_total` int NULL DEFAULT NULL COMMENT '题目数量',
  `subject_id` int NOT NULL,
  PRIMARY KEY (`test_id`) USING BTREE,
  INDEX `fk_test_subject_1`(`subject_id`) USING BTREE,
  CONSTRAINT `fk_test_subject_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 94 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for test_history
-- ----------------------------
DROP TABLE IF EXISTS `test_history`;
CREATE TABLE `test_history`  (
  `history_id` int NOT NULL AUTO_INCREMENT,
  `correct` int NULL DEFAULT NULL,
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成测验的结束时间',
  `take_time` int NULL DEFAULT NULL COMMENT '完成测验的时长（秒）',
  `test_id` int NOT NULL,
  `student_id` int NOT NULL,
  PRIMARY KEY (`history_id`) USING BTREE,
  INDEX `fk_test_history_test_1`(`test_id`) USING BTREE,
  INDEX `fk_test_history_user_1`(`student_id`) USING BTREE,
  INDEX `history_id`(`history_id`) USING BTREE,
  CONSTRAINT `fk_test_history_test_1` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_test_history_user_1` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7272 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for test_history_question_reply
-- ----------------------------
DROP TABLE IF EXISTS `test_history_question_reply`;
CREATE TABLE `test_history_question_reply`  (
  `reply_id` int NOT NULL AUTO_INCREMENT,
  `history_id` int NOT NULL,
  `question_id` int NOT NULL,
  `reply` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`reply_id`) USING BTREE,
  INDEX `reply_id`(`reply_id`) USING BTREE,
  INDEX `question_id`(`question_id`) USING BTREE,
  INDEX `history_id`(`history_id`) USING BTREE,
  CONSTRAINT `test_history_question_reply_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `test_history_question_reply_ibfk_2` FOREIGN KEY (`history_id`) REFERENCES `test_history` (`history_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 48317 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for test_question_list
-- ----------------------------
DROP TABLE IF EXISTS `test_question_list`;
CREATE TABLE `test_question_list`  (
  `test_id` int NOT NULL,
  `question_id` int NOT NULL,
  PRIMARY KEY (`test_id`, `question_id`) USING BTREE,
  INDEX `fk_test_question_list_question_1`(`question_id`) USING BTREE,
  CONSTRAINT `fk_test_question_list_question_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_test_question_list_test_1` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE,
  INDEX `fk_user_role_1`(`role_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_user_role_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 850 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '123', '$2a$10$9PjZyTvqpcBb0d671uGqOeefCh6b1.ijXfW3cbwq0LwIKQcFNNZwq', 'admin', 1);

-- ----------------------------
-- Table structure for user_clazz
-- ----------------------------
DROP TABLE IF EXISTS `user_clazz`;
CREATE TABLE `user_clazz`  (
  `student_id` int NOT NULL,
  `clazz_id` int NOT NULL,
  PRIMARY KEY (`student_id`, `clazz_id`) USING BTREE,
  INDEX `fk_user_clazz_clazz_1`(`clazz_id`) USING BTREE,
  CONSTRAINT `fk_user_clazz_clazz_1` FOREIGN KEY (`clazz_id`) REFERENCES `clazz` (`clazz_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_clazz_user_1` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_major
-- ----------------------------
DROP TABLE IF EXISTS `user_major`;
CREATE TABLE `user_major`  (
  `teacher_id` int NOT NULL,
  `major_id` int NOT NULL,
  PRIMARY KEY (`teacher_id`, `major_id`) USING BTREE,
  INDEX `fk_user_major_major_1`(`major_id`) USING BTREE,
  CONSTRAINT `fk_user_major_major_1` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_major_user_1` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for compare_short_answer_with_reply
-- ----------------------------
DROP VIEW IF EXISTS `compare_short_answer_with_reply`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `compare_short_answer_with_reply` AS select `compare_short_answer`.`compare_id` AS `compare_id`,`compare_short_answer`.`compare_threshold` AS `compare_threshold`,`compare_short_answer`.`short_answer_id` AS `short_answer_id`,`short_answer`.`question_id` AS `question_id`,`question`.`question_title` AS `question_title`,`short_answer`.`test_id` AS `test_id`,`compare_short_answer`.`reply1_id` AS `reply1_id`,`test_history_question_reply_1`.`reply` AS `reply1_text`,(select rank() OVER (ORDER BY `test_history_ranking1`.`history_id` )  AS `ranking` from `test_history` `test_history_ranking1` where ((`test_history_ranking1`.`test_id` = `short_answer`.`test_id`) and (`test_history_ranking1`.`student_id` = `user1`.`user_id`) and (`test_history_1`.`history_id` = `test_history_ranking1`.`history_id`))) AS `reply1_ranking`,`user1`.`user_id` AS `user_id`,`user1`.`username` AS `username`,`compare_short_answer`.`reply2_id` AS `reply2_id`,`test_history_question_reply_2`.`reply` AS `reply2_text`,(select rank() OVER (ORDER BY `test_history_ranking2`.`history_id` )  AS `ranking` from `test_history` `test_history_ranking2` where ((`test_history_ranking2`.`test_id` = `short_answer`.`test_id`) and (`test_history_ranking2`.`student_id` = `user2`.`user_id`) and (`test_history_2`.`history_id` = `test_history_ranking2`.`history_id`))) AS `reply2_ranking`,`user2`.`user_id` AS `compare_user_id`,`user2`.`username` AS `compare_username` from ((((((((`compare_short_answer` join `short_answer` on((`short_answer`.`short_answer_id` = `compare_short_answer`.`short_answer_id`))) join `question` on((`short_answer`.`question_id` = `question`.`question_id`))) join `test_history_question_reply` `test_history_question_reply_1` on((`test_history_question_reply_1`.`reply_id` = `compare_short_answer`.`reply1_id`))) join `test_history` `test_history_1` on((`test_history_1`.`history_id` = `test_history_question_reply_1`.`history_id`))) join `user` `user1` on((`user1`.`user_id` = `test_history_1`.`student_id`))) join `test_history_question_reply` `test_history_question_reply_2` on((`test_history_question_reply_2`.`reply_id` = `compare_short_answer`.`reply2_id`))) join `test_history` `test_history_2` on((`test_history_2`.`history_id` = `test_history_question_reply_2`.`history_id`))) join `user` `user2` on((`user2`.`user_id` = `test_history_2`.`student_id`)));

-- ----------------------------
-- View structure for question_with_type_subject
-- ----------------------------
DROP VIEW IF EXISTS `question_with_type_subject`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `question_with_type_subject` AS select `question`.`question_id` AS `question_id`,`question`.`question_title` AS `question_title`,`question`.`subject_id` AS `subject_id`,`question`.`type_id` AS `type_id`,`question_type`.`type_name` AS `type_name`,`subject`.`subject_name` AS `subject_name` from ((`question` join `question_type` on((`question`.`type_id` = `question_type`.`type_id`))) join `subject` on((`question`.`subject_id` = `subject`.`subject_id`)));

-- ----------------------------
-- View structure for student_with_role_clazz_major
-- ----------------------------
DROP VIEW IF EXISTS `student_with_role_clazz_major`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `student_with_role_clazz_major` AS select `user`.`user_id` AS `user_id`,`user`.`account` AS `account`,`user`.`password` AS `password`,`user`.`username` AS `username`,`user`.`role_id` AS `role_id`,`role`.`role_name` AS `role_name`,`role`.`description` AS `description`,`clazz`.`clazz_id` AS `clazz_id`,`clazz`.`clazz_name` AS `clazz_name`,`clazz`.`major_id` AS `major_id`,`major`.`major_name` AS `major_name` from ((((`user` join `role` on((`role`.`role_id` = `user`.`role_id`))) left join `user_clazz` on((`user_clazz`.`student_id` = `user`.`user_id`))) left join `clazz` on((`clazz`.`clazz_id` = `user_clazz`.`clazz_id`))) join `major` on((`major`.`major_id` = `clazz`.`major_id`))) where (`role`.`role_id` = '3');

-- ----------------------------
-- View structure for subject_with_user_clazz
-- ----------------------------
DROP VIEW IF EXISTS `subject_with_user_clazz`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `subject_with_user_clazz` AS select `subject`.`subject_id` AS `subject_id`,`subject`.`subject_name` AS `subject_name`,`subject`.`teacher_id` AS `teacher_id`,`subject`.`clazz_id` AS `clazz_id`,`user`.`username` AS `teacher_name`,`clazz`.`clazz_name` AS `clazz_name`,`major`.`major_id` AS `major_id`,`major`.`major_name` AS `major_name` from (((`subject` join `user` on((`user`.`user_id` = `subject`.`teacher_id`))) join `clazz` on((`clazz`.`clazz_id` = `subject`.`clazz_id`))) join `major` on((`major`.`major_id` = `clazz`.`major_id`)));

-- ----------------------------
-- View structure for teacher_with_role_major
-- ----------------------------
DROP VIEW IF EXISTS `teacher_with_role_major`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `teacher_with_role_major` AS select `user`.`user_id` AS `user_id`,`user`.`account` AS `account`,`user`.`password` AS `password`,`user`.`username` AS `username`,`user`.`role_id` AS `role_id`,`role`.`role_name` AS `role_name`,`role`.`description` AS `description`,`major`.`major_id` AS `major_id`,`major`.`major_name` AS `major_name` from (((`user` join `role` on((`role`.`role_id` = `user`.`role_id`))) left join `user_major` on((`user_major`.`teacher_id` = `user`.`user_id`))) left join `major` on((`major`.`major_id` = `user_major`.`major_id`))) where (`role`.`role_id` = '2');

-- ----------------------------
-- View structure for test_history_question_reply_with_user
-- ----------------------------
DROP VIEW IF EXISTS `test_history_question_reply_with_user`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `test_history_question_reply_with_user` AS select `test_history_question_reply`.`reply_id` AS `reply_id`,`test_history_question_reply`.`history_id` AS `history_id`,`test_history_question_reply`.`question_id` AS `question_id`,`test_history_question_reply`.`reply` AS `reply`,`user`.`user_id` AS `user_id`,`user`.`username` AS `username` from ((`test_history_question_reply` join `test_history` on((`test_history`.`history_id` = `test_history_question_reply`.`history_id`))) join `user` on((`user`.`user_id` = `test_history`.`student_id`)));

-- ----------------------------
-- View structure for test_history_with_user_test_subject
-- ----------------------------
DROP VIEW IF EXISTS `test_history_with_user_test_subject`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `test_history_with_user_test_subject` AS select max(`test_history`.`correct`) AS `max_correct`,count(`test_history`.`history_id`) AS `complete_time`,`test_history`.`test_id` AS `test_id`,`test_history`.`student_id` AS `student_id`,`student_with_role_clazz_major`.`username` AS `username`,`student_with_role_clazz_major`.`clazz_id` AS `clazz_id`,`student_with_role_clazz_major`.`clazz_name` AS `clazz_name`,`student_with_role_clazz_major`.`major_id` AS `major_id`,`student_with_role_clazz_major`.`major_name` AS `major_name`,`test_with_subject_clazz`.`test_name` AS `test_name`,`test_with_subject_clazz`.`begin_date` AS `begin_date`,`test_with_subject_clazz`.`end_date` AS `end_date`,`test_with_subject_clazz`.`description` AS `description`,`test_with_subject_clazz`.`subject_id` AS `subject_id`,`test_with_subject_clazz`.`subject_name` AS `subject_name`,`test_with_subject_clazz`.`question_total` AS `question_total`,`test_with_subject_clazz`.`exam_time` AS `exam_time` from ((`test_history` join `student_with_role_clazz_major` on((`student_with_role_clazz_major`.`user_id` = `test_history`.`student_id`))) join `test_with_subject_clazz` on((`test_with_subject_clazz`.`test_id` = `test_history`.`test_id`))) group by `test_history`.`test_id`,`test_history`.`student_id`;

-- ----------------------------
-- View structure for test_with_subject_clazz
-- ----------------------------
DROP VIEW IF EXISTS `test_with_subject_clazz`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `test_with_subject_clazz` AS select `test`.`test_id` AS `test_id`,`test`.`test_name` AS `test_name`,`test`.`description` AS `description`,`test`.`begin_date` AS `begin_date`,`test`.`end_date` AS `end_date`,`test`.`exam_duration` AS `exam_duration`,`test`.`exam_time` AS `exam_time`,`test`.`question_total` AS `question_total`,`subject_with_user_clazz`.`subject_id` AS `subject_id`,`subject_with_user_clazz`.`subject_name` AS `subject_name`,`subject_with_user_clazz`.`teacher_id` AS `teacher_id`,`subject_with_user_clazz`.`clazz_id` AS `clazz_id`,`subject_with_user_clazz`.`teacher_name` AS `teacher_name`,`subject_with_user_clazz`.`clazz_name` AS `clazz_name`,`subject_with_user_clazz`.`major_id` AS `major_id`,`subject_with_user_clazz`.`major_name` AS `major_name` from (`test` join `subject_with_user_clazz` on((`subject_with_user_clazz`.`subject_id` = `test`.`subject_id`)));

-- ----------------------------
-- View structure for user_with_role
-- ----------------------------
DROP VIEW IF EXISTS `user_with_role`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `user_with_role` AS select `user`.`user_id` AS `user_id`,`user`.`account` AS `account`,`user`.`password` AS `password`,`user`.`username` AS `username`,`user`.`role_id` AS `role_id`,`role`.`role_name` AS `role_name`,`role`.`description` AS `description` from (`user` join `role` on((`user`.`role_id` = `role`.`role_id`)));

SET FOREIGN_KEY_CHECKS = 1;
