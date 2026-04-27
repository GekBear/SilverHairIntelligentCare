package com.silverhair.care;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.silverhair.care.model.*;
import com.silverhair.care.repository.*;

import java.util.Date;

@SpringBootApplication
public class SilverHairCareApplication {
        public static void main(String[] args) {
            p("  ██████   ", " ████████  ", " ██      ██ ", "  ████████  ", "██      ██  ", " ██    ██  ", " ██    ██  ");
            p(" ██    ██  ", " ██    ██  ", "   ██  ██   ", "     ██    ", "   ██  ██   ", "  ██    ██  ", "  ██  ██     ");
            p(" ██        ", " ██    ██  ", "    ████    ", "     ██    ", "    ████   ", "   ████████  ", "    ██     ");
            p(" ██        ", " ██    ██  ", "    ████    ", "     ██    ", "    ████    ", "  ████████  ", "    ██     ");
            p(" ██    ██  ", " ██    ██████", "  ████  ", "       ██    ", "   ██  ██    ", " ██    ██  ", "    ██     ");
            p("  ██████   ", " ████████  ", "    ████  ", "       ██    ", " ██      ██  ", " ██    ██  ", "    ██  ");
            SpringApplication.run(SilverHairCareApplication.class, args);
        }
        static void p(String... s){
            for(String a : s) System.out.print(a+" ");
            System.out.println();
        }
        
        @Bean
        CommandLineRunner initData(
            SysUserRepository sysUserRepository,
            UserElderRepository userElderRepository,
            UserChildRepository userChildRepository,
            UserBindRepository userBindRepository,
            HealthRecordRepository healthRecordRepository,
            AlarmSosRepository alarmSosRepository,
            AlarmLocationRepository alarmLocationRepository
        ) {
            return args -> {
                // 清空所有表数据
                userBindRepository.deleteAll();
                userChildRepository.deleteAll();
                userElderRepository.deleteAll();
                healthRecordRepository.deleteAll();
                alarmSosRepository.deleteAll();
                alarmLocationRepository.deleteAll();
                sysUserRepository.deleteAll();
                
                // 注入PasswordEncoder
                org.springframework.security.crypto.password.PasswordEncoder passwordEncoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
                
                // 插入系统用户数据
                SysUser admin = new SysUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setPhone("13800138000");
                admin.setRealName("Admin");
                admin.setRole(9);
                admin.setStatus(1);
                admin.setCreateTime(new Date());
                admin.setUpdateTime(new Date());
                sysUserRepository.save(admin);
                
                SysUser elderly1 = new SysUser();
                elderly1.setUsername("elderly1");
                elderly1.setPassword(passwordEncoder.encode("123456"));
                elderly1.setPhone("13800138001");
                elderly1.setRealName("Zhang");
                elderly1.setRole(1);
                elderly1.setStatus(1);
                elderly1.setCreateTime(new Date());
                elderly1.setUpdateTime(new Date());
                sysUserRepository.save(elderly1);
                
                SysUser family1 = new SysUser();
                family1.setUsername("family1");
                family1.setPassword(passwordEncoder.encode("123456"));
                family1.setPhone("13800138002");
                family1.setRealName("ZhangSon");
                family1.setRole(2);
                family1.setStatus(1);
                family1.setCreateTime(new Date());
                family1.setUpdateTime(new Date());
                sysUserRepository.save(family1);
                
                // 插入老人用户数据
                UserElder elder1 = new UserElder();
                elder1.setUserId(elderly1.getId());
                // elder1.setName("张大爷");
                // elder1.setPhone("13800138001");
                // elder1.setAge(75);
                // elder1.setGender(1);
                // elder1.setAddress("北京市朝阳区幸福小区1号楼101室");
                // elder1.setEmergencyPhone("13800138002");
                // elder1.setMedicalHistory("高血压、糖尿病");
                // elder1.setStatus(1);
                // elder1.setRemark("需要定期监测血压和血糖");
                userElderRepository.save(elder1);
                
                // 插入子女用户数据
                UserChild child1 = new UserChild();
                child1.setUserId(family1.getId());
                // child1.setName("张三");
                // child1.setPhone("13800138002");
                // child1.setRelation("儿子");
                // child1.setStatus(1);
                userChildRepository.save(child1);
                
                // 插入用户绑定数据
                UserBind bind1 = new UserBind();
                bind1.setElderId(elder1.getUserId());
                bind1.setChildId(child1.getUserId());
                bind1.setStatus(1);
                bind1.setCreateTime(new Date());
                userBindRepository.save(bind1);
                
                // 插入健康记录数据
                HealthRecord healthRecord = new HealthRecord();
                healthRecord.setElderId(elder1.getUserId());
                healthRecord.setBloodPressureHigh(120);
                healthRecord.setBloodPressureLow(80);
                healthRecord.setBloodSugar(5.6);
                healthRecord.setHeartRate(72);
                healthRecord.setStatus(1);
                healthRecord.setRecordTime(new Date());
                healthRecordRepository.save(healthRecord);
                
                // 插入SOS告警数据
                AlarmSos sos = new AlarmSos();
                sos.setElderId(elder1.getUserId());
                sos.setLongitude(116.4074);
                sos.setLatitude(39.9042);
                sos.setAddress("北京市朝阳区幸福小区1号楼101室");
                sos.setStatus(1);
                sos.setCreateTime(new Date());
                alarmSosRepository.save(sos);
                
                // 插入位置告警数据
                AlarmLocation location = new AlarmLocation();
                location.setElderId(elder1.getUserId());
                location.setLongitude(116.4074);
                location.setLatitude(39.9042);
                location.setAddress("北京市朝阳区幸福小区1号楼101室");
                location.setCreateTime(new Date());
                alarmLocationRepository.save(location);
                
                System.out.println("示例数据插入完成！");
            };
        }
}