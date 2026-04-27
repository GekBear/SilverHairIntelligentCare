package com.silverhair.care.controller;

import com.silverhair.care.common.Result;
import com.silverhair.care.model.SysUser;
import com.silverhair.care.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String phone, @RequestParam String password) {
        try {
            String token = authService.login(phone, password);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(Result.success("登录成功", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.error(e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SysUser user) {
        try {
            SysUser registeredUser = authService.register(user);
            return ResponseEntity.ok(Result.success("注册成功", registeredUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error(e.getMessage()));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            SysUser user = authService.getUserById(id);
            return ResponseEntity.ok(Result.success(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Result.error(e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // JWT是无状态的,客户端只需删除token即可
        return ResponseEntity.ok(Result.success("退出成功"));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam Long userId, 
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword) {
        try {
            authService.changePassword(userId, oldPassword, newPassword);
            return ResponseEntity.ok(Result.success("密码修改成功"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error(e.getMessage()));
        }
    }
}