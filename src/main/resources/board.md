CREATE TABLE `board` (
  `board_id` varchar(100) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `body` varchar(2000) DEFAULT NULL,
  `file_nm` varchar(1000) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp 
  PRIMARY KEY (`id`),
  KEY `idx_board_id` (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;





