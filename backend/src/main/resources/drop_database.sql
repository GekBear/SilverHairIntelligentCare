-- 连接到lcap_warehousemanagementsys数据库
USE lcap_warehousemanagementsys;

-- 删除所有表（按照外键依赖顺序）
DROP TABLE IF EXISTS interaction_remind;
DROP TABLE IF EXISTS interaction_msg;
DROP TABLE IF EXISTS service_order;
DROP TABLE IF EXISTS service_dict;
DROP TABLE IF EXISTS health_report;
DROP TABLE IF EXISTS health_record;
DROP TABLE IF EXISTS health_standard;
DROP TABLE IF EXISTS alarm_notice;
DROP TABLE IF EXISTS alarm_fence;
DROP TABLE IF EXISTS alarm_location;
DROP TABLE IF EXISTS alarm_sos;
DROP TABLE IF EXISTS user_bind;
DROP TABLE IF EXISTS user_child;
DROP TABLE IF EXISTS user_elder;
DROP TABLE IF EXISTS sys_log;
DROP TABLE IF EXISTS sys_config;
DROP TABLE IF EXISTS policy;

-- 删除数据库
DROP DATABASE IF EXISTS lcap_warehousemanagementsys;