package com.silverhair.care.controller;

import com.silverhair.care.common.PageResult;
import com.silverhair.care.common.Result;
import com.silverhair.care.model.UserElder;
import com.silverhair.care.service.ElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elderly")
public class ElderlyController {

    @Autowired
    private ElderlyService elderlyService;

    @PostMapping
    public ResponseEntity<?> createElderly(@RequestBody UserElder elderly) {
        try {
            UserElder createdElderly = elderlyService.createElderly(elderly);
            return ResponseEntity.ok(Result.success("创建成功", createdElderly));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Result.error(e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getElderlyList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            PageResult<UserElder> result = elderlyService.getElderlyList(name, phone, status, page, size);
            return ResponseEntity.ok(Result.success(result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Result.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getElderlyById(@PathVariable Long id) {
        try {
            UserElder elderly = elderlyService.getElderlyById(id);
            return ResponseEntity.ok(Result.success(elderly));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Result.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateElderly(@PathVariable Long id, @RequestBody UserElder elderly) {
        try {
            UserElder updatedElderly = elderlyService.updateElderly(id, elderly);
            return ResponseEntity.ok(Result.success("更新成功", updatedElderly));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Result.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteElderly(@PathVariable Long id) {
        try {
            elderlyService.deleteElderly(id);
            return ResponseEntity.ok(Result.success("删除成功"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Result.error(e.getMessage()));
        }
    }
}
