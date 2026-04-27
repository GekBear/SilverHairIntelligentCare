package com.silverhair.care.controller;

import com.silverhair.care.model.AlarmFence;
import com.silverhair.care.model.AlarmLocation;
import com.silverhair.care.model.AlarmSos;
import com.silverhair.care.service.SafetyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/safety")
public class SafetyController {

    @Autowired
    private SafetyService safetyService;

    // SOS求助相关
    @PostMapping("/sos")
    public ResponseEntity<?> createSos(@RequestBody AlarmSos sos) {
        try {
            AlarmSos createdSos = safetyService.createSos(sos);
            return ResponseEntity.ok(createdSos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/sos/{elderId}")
    public ResponseEntity<?> getSosRecords(@PathVariable Long elderId) {
        try {
            List<AlarmSos> sosRecords = safetyService.getSosByElderId(elderId);
            return ResponseEntity.ok(sosRecords);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/sos/{id}/status")
    public ResponseEntity<?> updateSosStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            AlarmSos updatedSos = safetyService.updateSosStatus(id, status);
            return ResponseEntity.ok(updatedSos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 定位相关
    @PostMapping("/location")
    public ResponseEntity<?> createLocation(@RequestBody AlarmLocation location) {
        try {
            AlarmLocation createdLocation = safetyService.createLocation(location);
            return ResponseEntity.ok(createdLocation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/locations/{elderId}")
    public ResponseEntity<?> getLocations(@PathVariable Long elderId) {
        try {
            List<AlarmLocation> locations = safetyService.getLocationsByElderId(elderId);
            return ResponseEntity.ok(locations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 电子围栏相关
    @PostMapping("/fence")
    public ResponseEntity<?> createFence(@RequestBody AlarmFence fence) {
        try {
            AlarmFence createdFence = safetyService.createFence(fence);
            return ResponseEntity.ok(createdFence);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/fences/{elderId}")
    public ResponseEntity<?> getFences(@PathVariable Long elderId) {
        try {
            List<AlarmFence> fences = safetyService.getFencesByElderId(elderId);
            return ResponseEntity.ok(fences);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/fence/{id}")
    public ResponseEntity<?> updateFence(@PathVariable Long id, @RequestBody AlarmFence fence) {
        try {
            AlarmFence updatedFence = safetyService.updateFence(id, fence);
            return ResponseEntity.ok(updatedFence);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/fence/{id}")
    public ResponseEntity<?> deleteFence(@PathVariable Long id) {
        try {
            safetyService.deleteFence(id);
            return ResponseEntity.ok("Fence deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}