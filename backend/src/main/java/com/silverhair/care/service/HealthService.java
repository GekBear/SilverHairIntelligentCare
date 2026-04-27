package com.silverhair.care.service;

import com.silverhair.care.model.HealthRecord;
import com.silverhair.care.repository.HealthRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthService {

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    public HealthRecord createHealthRecord(HealthRecord healthRecord) {
        // 计算健康状态
        calculateHealthStatus(healthRecord);
        return healthRecordRepository.save(healthRecord);
    }

    public List<HealthRecord> getHealthRecordsByElderId(Long elderId) {
        return healthRecordRepository.findByElderIdOrderByRecordTimeDesc(elderId);
    }

    public HealthRecord getHealthRecordById(Long id) {
        return healthRecordRepository.findById(id).orElseThrow(() -> new RuntimeException("Health record not found"));
    }

    private void calculateHealthStatus(HealthRecord healthRecord) {
        int status = 1; // 默认正常

        // 检查血压
        if (healthRecord.getBloodPressureHigh() != null) {
            if (healthRecord.getBloodPressureHigh() > 140 || healthRecord.getBloodPressureLow() > 90) {
                status = 2; // 偏高
            }
            if (healthRecord.getBloodPressureHigh() > 180 || healthRecord.getBloodPressureLow() > 120) {
                status = 3; // 异常
            }
        }

        // 检查血糖
        if (healthRecord.getBloodSugar() != null) {
            if (healthRecord.getBloodSugar() > 7.0) {
                status = 2; // 偏高
            }
            if (healthRecord.getBloodSugar() > 11.1) {
                status = 3; // 异常
            }
        }

        // 检查心率
        if (healthRecord.getHeartRate() != null) {
            if (healthRecord.getHeartRate() > 100 || healthRecord.getHeartRate() < 60) {
                status = 2; // 偏高
            }
            if (healthRecord.getHeartRate() > 120 || healthRecord.getHeartRate() < 50) {
                status = 3; // 异常
            }
        }

        healthRecord.setStatus(status);
    }
}