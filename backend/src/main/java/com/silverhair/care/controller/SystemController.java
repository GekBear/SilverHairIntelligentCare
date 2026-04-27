package com.silverhair.care.controller;

import com.silverhair.care.model.SystemSettings;
import com.silverhair.care.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @GetMapping("/settings")
    public ResponseEntity<?> getSystemSettings() {
        try {
            SystemSettings settings = systemService.getSystemSettings();
            return ResponseEntity.ok(settings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/settings")
    public ResponseEntity<?> updateSystemSettings(@RequestBody SystemSettings settings) {
        try {
            SystemSettings updatedSettings = systemService.updateSystemSettings(settings);
            return ResponseEntity.ok(updatedSettings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/logs")
    public ResponseEntity<?> getSystemLogs(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String operator,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<?> logs = systemService.getSystemLogs(type, operator, startDate, endDate, page, size);
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
