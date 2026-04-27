package com.silverhair.care.controller;

import com.silverhair.care.model.HealthRecord;
import com.silverhair.care.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private HealthService healthService;

    @PostMapping("/record")
    public ResponseEntity<?> createHealthRecord(@RequestBody HealthRecord healthRecord) {
        try {
            HealthRecord createdRecord = healthService.createHealthRecord(healthRecord);
            return ResponseEntity.ok(createdRecord);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/records/{elderId}")
    public ResponseEntity<?> getHealthRecords(@PathVariable Long elderId) {
        try {
            List<HealthRecord> records = healthService.getHealthRecordsByElderId(elderId);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/record/{id}")
    public ResponseEntity<?> getHealthRecord(@PathVariable Long id) {
        try {
            HealthRecord record = healthService.getHealthRecordById(id);
            return ResponseEntity.ok(record);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}