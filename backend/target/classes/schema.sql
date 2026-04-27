-- 创建数据库
CREATE DATABASE IF NOT EXISTS silverhaircare CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE silverhaircare;

-- 用户中心模块

-- 1. sys_user (用户基础表)
CREATE TABLE IF NOT EXISTS sys_user (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  username varchar(50) NOT NULL COMMENT '账号',
  password varchar(100) NOT NULL COMMENT '密码（加密）',
  phone varchar(20) NOT NULL COMMENT '手机号',
  real_name varchar(30) DEFAULT '' COMMENT '真实姓名',
  avatar varchar(255) DEFAULT '' COMMENT '头像',
  role tinyint NOT NULL DEFAULT 1 COMMENT '角色 1老人 2子女 9管理员',
  status tinyint DEFAULT 1 COMMENT '状态 0禁用 1正常',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基础表';

-- 2. user_elder (老人信息表)
CREATE TABLE IF NOT EXISTS user_elder (
  id bigint NOT NULL AUTO_INCREMENT,
  user_id bigint NOT NULL COMMENT '用户ID',
  age int DEFAULT 0 COMMENT '年龄',
  gender tinyint DEFAULT 0 COMMENT '0未知 1男 2女',
  address varchar(255) DEFAULT '' COMMENT '家庭住址',
  emergency_phone varchar(20) DEFAULT '' COMMENT '紧急联系电话',
  medical_history varchar(500) DEFAULT '' COMMENT '病史',
  PRIMARY KEY (id),
  UNIQUE KEY uk_user_id (user_id),
  FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老人信息表';

-- 3. user_child (子女信息表)
CREATE TABLE IF NOT EXISTS user_child (
  id bigint NOT NULL AUTO_INCREMENT,
  user_id bigint NOT NULL,
  relation varchar(20) DEFAULT '' COMMENT '与老人关系',
  PRIMARY KEY (id),
  UNIQUE KEY uk_user_id (user_id),
  FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='子女信息表';

-- 4. user_bind (老人-子女绑定关系表)
CREATE TABLE IF NOT EXISTS user_bind (
  id bigint NOT NULL AUTO_INCREMENT,
  elder_id bigint NOT NULL COMMENT '老人ID',
  child_id bigint NOT NULL COMMENT '子女ID',
  auth_type tinyint DEFAULT 1 COMMENT '权限 1查看 2管理',
  status tinyint DEFAULT 1 COMMENT '0待确认 1已绑定 2已解绑',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (elder_id) REFERENCES user_elder(user_id) ON DELETE CASCADE,
  FOREIGN KEY (child_id) REFERENCES user_child(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='亲属绑定关系表';

-- 安全告警模块

-- 5. alarm_sos (SOS求助记录表)
CREATE TABLE IF NOT EXISTS alarm_sos (
  id bigint NOT NULL AUTO_INCREMENT,
  elder_id bigint NOT NULL,
  longitude decimal(12,8) DEFAULT NULL COMMENT '经度',
  latitude decimal(12,8) DEFAULT NULL COMMENT '纬度',
  address varchar(255) DEFAULT '' COMMENT '求助地址',
  status tinyint DEFAULT 0 COMMENT '0待处理 1处理中 2已解决',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (elder_id) REFERENCES user_elder(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='SOS紧急求助表';

-- 6. alarm_location (定位记录表)
CREATE TABLE IF NOT EXISTS alarm_location (
  id bigint NOT NULL AUTO_INCREMENT,
  elder_id bigint NOT NULL,
  longitude decimal(12,8),
  latitude decimal(12,8),
  address varchar(255),
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (elder_id) REFERENCES user_elder(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老人定位记录表';

-- 7. alarm_fence (电子围栏表)
CREATE TABLE IF NOT EXISTS alarm_fence (
  id bigint NOT NULL AUTO_INCREMENT,
  elder_id bigint NOT NULL,
  name varchar(50) DEFAULT '家',
  longitude decimal(12,8),
  latitude decimal(12,8),
  radius int DEFAULT 200 COMMENT '半径米',
  status tinyint DEFAULT 1,
  PRIMARY KEY (id),
  FOREIGN KEY (elder_id) REFERENCES user_elder(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电子围栏表';

-- 8. alarm_notice (告警通知表)
CREATE TABLE IF NOT EXISTS alarm_notice (
  id bigint NOT NULL AUTO_INCREMENT,
  elder_id bigint NOT NULL,
  child_id bigint NOT NULL,
  notice_type tinyint DEFAULT 1 COMMENT '1SOS 2定位 3健康',
  content varchar(255) DEFAULT '',
  status tinyint DEFAULT 0 COMMENT '0未读 1已读',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (elder_id) REFERENCES user_elder(user_id) ON DELETE CASCADE,
  FOREIGN KEY (child_id) REFERENCES user_child(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警通知表';

-- 健康管理模块

-- 9. health_record (健康数据表)
CREATE TABLE IF NOT EXISTS health_record (
  id bigint NOT NULL AUTO_INCREMENT,
  elder_id bigint NOT NULL,
  blood_pressure_high int DEFAULT 0 COMMENT '收缩压',
  blood_pressure_low int DEFAULT 0 COMMENT '舒张压',
  blood_sugar decimal(4,1) DEFAULT 0 COMMENT '血糖',
  heart_rate int DEFAULT 0 COMMENT '心率',
  status tinyint DEFAULT 1 COMMENT '1正常 2偏高 3异常',
  record_time datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (elder_id) REFERENCES user_elder(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康记录表';

-- 10. health_report (健康报告表)
CREATE TABLE IF NOT EXISTS health_report (
  id bigint NOT NULL AUTO_INCREMENT,
  elder_id bigint NOT NULL,
  report_type tinyint DEFAULT 1 COMMENT '1日报 2周报 3月报',
  content text COMMENT '分析内容',
  file_url varchar(255) DEFAULT '' COMMENT 'PDF路径',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (elder_id) REFERENCES user_elder(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康报告表';

-- 11. health_standard (健康指标标准表)
CREATE TABLE IF NOT EXISTS health_standard (
  id bigint NOT NULL AUTO_INCREMENT,
  indicator varchar(50) NOT NULL COMMENT '指标名称',
  min_value decimal(10,2) DEFAULT 0 COMMENT '最小值',
  max_value decimal(10,2) DEFAULT 0 COMMENT '最大值',
  unit varchar(10) DEFAULT '' COMMENT '单位',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康指标标准表';

-- 互动服务模块

-- 12. interaction_msg (语音/文字留言表)
CREATE TABLE IF NOT EXISTS interaction_msg (
  id bigint NOT NULL AUTO_INCREMENT,
  from_id bigint NOT NULL,
  to_id bigint NOT NULL,
  content text COMMENT '文字内容',
  voice_url varchar(255) DEFAULT '' COMMENT '语音地址',
  msg_type tinyint DEFAULT 1 COMMENT '1文字 2语音',
  is_read tinyint DEFAULT 0,
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (from_id) REFERENCES sys_user(id) ON DELETE CASCADE,
  FOREIGN KEY (to_id) REFERENCES sys_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='子女老人互动表';

-- 13. interaction_remind (定时提醒表)
CREATE TABLE IF NOT EXISTS interaction_remind (
  id bigint NOT NULL AUTO_INCREMENT,
  elder_id bigint NOT NULL,
  title varchar(100) NOT NULL COMMENT '提醒标题',
  remind_time datetime NOT NULL,
  repeat_type tinyint DEFAULT 0 COMMENT '0不重复 1每天',
  status tinyint DEFAULT 1,
  PRIMARY KEY (id),
  FOREIGN KEY (elder_id) REFERENCES user_elder(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用药/复诊提醒表';

-- 14. service_order (服务工单表)
CREATE TABLE IF NOT EXISTS service_order (
  id bigint NOT NULL AUTO_INCREMENT,
  elder_id bigint NOT NULL,
  service_type tinyint NOT NULL COMMENT '1助餐 2家政 3维修 4咨询',
  content varchar(500) DEFAULT '',
  status tinyint DEFAULT 0 COMMENT '0待接单 1处理中 2已完成',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (elder_id) REFERENCES user_elder(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='养老服务工单表';

-- 15. service_dict (服务类型字典表)
CREATE TABLE IF NOT EXISTS service_dict (
  id bigint NOT NULL AUTO_INCREMENT,
  type tinyint NOT NULL COMMENT '服务类型',
  name varchar(50) NOT NULL COMMENT '服务名称',
  description varchar(255) DEFAULT '' COMMENT '服务描述',
  PRIMARY KEY (id),
  UNIQUE KEY uk_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务类型字典表';

-- 系统模块

-- 16. sys_config (系统参数表)
CREATE TABLE IF NOT EXISTS sys_config (
  id bigint NOT NULL AUTO_INCREMENT,
  key_name varchar(50) NOT NULL COMMENT '参数键',
  key_value varchar(255) DEFAULT '' COMMENT '参数值',
  description varchar(255) DEFAULT '' COMMENT '参数描述',
  PRIMARY KEY (id),
  UNIQUE KEY uk_key_name (key_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统参数表';

-- 17. sys_log (系统操作日志表)
CREATE TABLE IF NOT EXISTS sys_log (
  id bigint NOT NULL AUTO_INCREMENT,
  user_id bigint NOT NULL,
  module varchar(50) DEFAULT '' COMMENT '模块',
  content varchar(500) DEFAULT '' COMMENT '操作内容',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统操作日志表';

-- 18. policy (政策表)
CREATE TABLE IF NOT EXISTS policy (
  id bigint NOT NULL AUTO_INCREMENT,
  title varchar(200) NOT NULL COMMENT '政策标题',
  type tinyint NOT NULL COMMENT '政策类型 1养老政策 2医疗政策 3补贴政策',
  publish_date date COMMENT '发布日期',
  effective_date date COMMENT '生效日期',
  expire_date date COMMENT '失效日期',
  content text COMMENT '政策内容',
  status tinyint DEFAULT 1 COMMENT '状态 1生效中 2已过期',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='政策表';

-- 插入服务类型字典数据
INSERT INTO service_dict (type, name, description) VALUES
(1, '助餐', '为老人提供餐饮服务'),
(2, '家政', '为老人提供家政服务'),
(3, '维修', '为老人提供房屋维修服务'),
(4, '咨询', '为老人提供健康咨询服务');

-- 插入健康指标标准数据
INSERT INTO health_standard (indicator, min_value, max_value, unit) VALUES
('收缩压', 90, 140, 'mmHg'),
('舒张压', 60, 90, 'mmHg'),
('血糖', 3.9, 6.1, 'mmol/L'),
('心率', 60, 100, '次/分');

-- 插入系统参数数据
INSERT INTO sys_config (key_name, key_value, description) VALUES
('jwt.secret', 'silverhaircaresecretkey', 'JWT密钥'),
('jwt.expiration', '86400000', 'JWT过期时间（毫秒）'),
('app.name', '银发智护', '应用名称'),
('app.version', '1.0.0', '应用版本');

-- 插入管理员账号
INSERT INTO sys_user (username, password, phone, real_name, role, status) VALUES
('admin', '$2a$10$e6Z3rV1Kj7u7M5f8v7W6xOe8e7R9t8y7u6i5o4p3o2i1u0y9t8s7r6e5w4q3e2r1t', '13800138000', '管理员', 9, 1);

-- 插入测试老人账号
INSERT INTO sys_user (username, password, phone, real_name, role, status) VALUES
('elderly1', '$2a$10$e6Z3rV1Kj7u7M5f8v7W6xOe8e7R9t8y7u6i5o4p3o2i1u0y9t8s7r6e5w4q3e2r1t', '13800138001', '张大爷', 1, 1);

-- 插入测试子女账号
INSERT INTO sys_user (username, password, phone, real_name, role, status) VALUES
('family1', '$2a$10$e6Z3rV1Kj7u7M5f8v7W6xOe8e7R9t8y7u6i5o4p3o2i1u0y9t8s7r6e5w4q3e2r1t', '13800138002', '李明', 2, 1);
