package com.silverhair.care.service;

import com.silverhair.care.model.SystemSettings;
import com.silverhair.care.repository.SystemSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Collections;

@Service
public class SystemService {

    @Autowired
    private SystemSettingsRepository systemSettingsRepository;

    public SystemSettings getSystemSettings() {
        // 获取系统设置，如果不存在则创建默认设置
        List<SystemSettings> settingsList = systemSettingsRepository.findAll();
        if (settingsList.isEmpty()) {
            SystemSettings defaultSettings = new SystemSettings();
            defaultSettings.setSystemName("银发智护管理系统");
            defaultSettings.setSystemVersion("1.0.0");
            defaultSettings.setContactPhone("400-123-4567");
            defaultSettings.setContactEmail("contact@silverhair.com");
            defaultSettings.setSystemStatus(true);
            defaultSettings.setPasswordExpiry(90);
            defaultSettings.setLoginFailLimit(5);
            defaultSettings.setSessionTimeout(30);
            defaultSettings.setEmailServer("smtp.qq.com");
            defaultSettings.setEmailPort(465);
            defaultSettings.setEmailSender("noreply@silverhair.com");
            defaultSettings.setEmailPassword("password");
            defaultSettings.setCreatedAt(new Date());
            defaultSettings.setUpdatedAt(new Date());
            return systemSettingsRepository.save(defaultSettings);
        }
        return settingsList.get(0);
    }

    public SystemSettings updateSystemSettings(SystemSettings settings) {
        SystemSettings existingSettings = getSystemSettings();
        existingSettings.setSystemName(settings.getSystemName());
        existingSettings.setSystemVersion(settings.getSystemVersion());
        existingSettings.setContactPhone(settings.getContactPhone());
        existingSettings.setContactEmail(settings.getContactEmail());
        existingSettings.setSystemStatus(settings.getSystemStatus());
        existingSettings.setPasswordExpiry(settings.getPasswordExpiry());
        existingSettings.setLoginFailLimit(settings.getLoginFailLimit());
        existingSettings.setSessionTimeout(settings.getSessionTimeout());
        existingSettings.setEmailServer(settings.getEmailServer());
        existingSettings.setEmailPort(settings.getEmailPort());
        existingSettings.setEmailSender(settings.getEmailSender());
        existingSettings.setEmailPassword(settings.getEmailPassword());
        existingSettings.setUpdatedAt(new Date());
        return systemSettingsRepository.save(existingSettings);
    }

    public List<?> getSystemLogs(String type, String operator, String startDate, String endDate, int page, int size) {
        // 这里可以添加复杂的查询逻辑
        // 暂时返回空列表
        return Collections.emptyList();
    }
}
