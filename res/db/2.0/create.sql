CREATE TABLE `answer`  (
  `answer_id` int NOT NULL AUTO_INCREMENT,
  `answer_sign` char(1) NULL,
  `content` varchar(255) NOT NULL,
  `is_corrent` int NOT NULL DEFAULT 0 COMMENT '是否是正确答案，1-是',
  `question_id` int NOT NULL,
  PRIMARY KEY (`answer_id`)
);

CREATE TABLE `clazz`  (
  `clazz_id` int NOT NULL AUTO_INCREMENT,
  `clazz_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`clazz_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

CREATE TABLE `comment`  (
  `post_id` int NOT NULL,
  `user_id` int NOT NULL,
  `comment` longtext NOT NULL,
  `comment_time` datetime NOT NULL,
  PRIMARY KEY (`post_id`, `user_id`)
);

CREATE TABLE `post`  (
  `post_id` int NOT NULL AUTO_INCREMENT,
  `post_title` varchar(255) NOT NULL,
  `description` longtext NULL,
  `post_time` datetime NOT NULL,
  `like` int NULL DEFAULT 0,
  `user_id` int NOT NULL,
  PRIMARY KEY (`post_id`)
);

CREATE TABLE `question`  (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `question_title` varchar(255) NOT NULL,
  `subject_id` int NOT NULL,
  `type_id` int NOT NULL,
  PRIMARY KEY (`question_id`)
);

CREATE TABLE `question_type`  (
  `type_id` int NOT NULL,
  `type_name` varchar(255) NOT NULL,
  `description` text NULL,
  PRIMARY KEY (`type_id`)
);

CREATE TABLE `role`  (
  `role_id` int NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `description` varchar(255) NULL,
  PRIMARY KEY (`role_id`)
);

CREATE TABLE `subject`  (
  `subject_id` int NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `teacher_id` int NOT NULL,
  PRIMARY KEY (`subject_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

CREATE TABLE `test`  (
  `test_id` int NOT NULL AUTO_INCREMENT,
  `begin_date` datetime(6) NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `end_date` datetime(6) NULL DEFAULT NULL,
  `exam_dure` int NULL DEFAULT NULL,
  `exam_time` int NULL DEFAULT NULL,
  `ques_arr` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `test_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `total_score` int NULL DEFAULT NULL,
  `subject_id` int NOT NULL,
  `clazz_id` int NOT NULL,
  PRIMARY KEY (`test_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

CREATE TABLE `test_history`  (
  `student_id` int NOT NULL,
  `test_id` int NOT NULL,
  `finish_time` datetime(6) NULL DEFAULT NULL,
  `score` int NULL DEFAULT NULL,
  PRIMARY KEY (`student_id`, `test_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

CREATE TABLE `user`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NULL,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `user_clazz`  (
  `user_id` int NOT NULL,
  `clazz_id` int NOT NULL,
  PRIMARY KEY (`user_id`, `clazz_id`)
);

CREATE TABLE `user_role`  (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`)
);

ALTER TABLE `answer` ADD CONSTRAINT `fk_answer_question_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`);
ALTER TABLE `comment` ADD CONSTRAINT `fk_comment_post_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`);
ALTER TABLE `comment` ADD CONSTRAINT `fk_comment_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
ALTER TABLE `post` ADD CONSTRAINT `fk_post_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
ALTER TABLE `question` ADD CONSTRAINT `fk_question_subject_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`);
ALTER TABLE `question` ADD CONSTRAINT `fk_question_question_type_1` FOREIGN KEY (`type_id`) REFERENCES `question_type` (`type_id`);
ALTER TABLE `subject` ADD CONSTRAINT `fk_subject_user_1` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`);
ALTER TABLE `test` ADD CONSTRAINT `fk_test_subject_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`);
ALTER TABLE `test` ADD CONSTRAINT `fk_test_clazz_1` FOREIGN KEY (`clazz_id`) REFERENCES `clazz` (`clazz_id`);
ALTER TABLE `test_history` ADD CONSTRAINT `fk_test_history_user_1` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`);
ALTER TABLE `test_history` ADD CONSTRAINT `fk_test_history_test_1` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`);
ALTER TABLE `user_clazz` ADD CONSTRAINT `fk_user_clazz_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
ALTER TABLE `user_clazz` ADD CONSTRAINT `fk_user_clazz_clazz_1` FOREIGN KEY (`clazz_id`) REFERENCES `clazz` (`clazz_id`);
ALTER TABLE `user_role` ADD CONSTRAINT `fk_user_role_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
ALTER TABLE `user_role` ADD CONSTRAINT `fk_user_role_role_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`);

