-- 创建附件表
-- DROP TABLE IF EXISTS `base_attachment`;
CREATE TABLE `base_attachment` (
  `id` bigint(20) unsigned NOT NULL,
  `ext_name` varchar(5) DEFAULT NULL COMMENT '扩展名',
  `src_name` varchar(255) DEFAULT NULL COMMENT '源文件名',
  `description` varchar(255) DEFAULT NULL COMMENT '附件描述',
  `relative_path` varchar(64) DEFAULT NULL COMMENT '外网访问相对路径',
  `type` tinyint(1) unsigned DEFAULT NULL COMMENT '附件类型[试卷, 其它]',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `state` tinyint(1) unsigned DEFAULT NULL COMMENT '状态',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
