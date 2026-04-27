package com.silverhair.care.controller;

import com.silverhair.care.common.PageResult;
import com.silverhair.care.common.Result;
import com.silverhair.care.model.UserChild;
import com.silverhair.care.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @PostMapping
    public ResponseEntity<?> createFamily(@RequestBody UserChild family) {
        try {
            UserChild createdFamily = familyService.createFamily(family);
            return ResponseEntity.ok(Result.success("创建成功", createdFamily));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Result.error(e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getFamilyList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Long elderlyId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            PageResult<UserChild> result = familyService.getFamilyList(name, phone, elderlyId, page, size);
            return ResponseEntity.ok(Result.success(result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Result.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFamilyById(@PathVariable Long id) {
        try {
            UserChild family = familyService.getFamilyById(id);
            return ResponseEntity.ok(Result.success(family));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Result.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFamily(@PathVariable Long id, @RequestBody UserChild family) {
        try {
            UserChild updatedFamily = familyService.updateFamily(id, family);
            return ResponseEntity.ok(Result.success("更新成功", updatedFamily));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Result.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFamily(@PathVariable Long id) {
        try {
            familyService.deleteFamily(id);
            return ResponseEntity.ok(Result.success("删除成功"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Result.error(e.getMessage()));
        }
    }
}
