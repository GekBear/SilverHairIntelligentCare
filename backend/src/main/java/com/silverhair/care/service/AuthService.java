package com.silverhair.care.service;

import com.silverhair.care.model.SysUser;
import com.silverhair.care.repository.SysUserRepository;
import com.silverhair.care.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private SysUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String phone, String password) {
        Optional<SysUser> userOptional = userRepository.findByPhone(phone);
        if (userOptional.isPresent()) {
            SysUser user = userOptional.get();
            System.out.println("Found user: " + user.getPhone() + ", role: " + user.getRole());
            System.out.println("Password provided: " + password);
            System.out.println("Password stored: " + user.getPassword());
            System.out.println("Password match: " + passwordEncoder.matches(password, user.getPassword()));
            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtUtil.generateToken(user.getId(), user.getRole());
            } else {
                throw new RuntimeException("Password does not match");
            }
        } else {
            // 如果是管理员账号（13800138000），自动创建
            if (phone.equals("13800138000")) {
                System.out.println("Creating admin user...");
                SysUser adminUser = new SysUser();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode(password));
                adminUser.setPhone(phone);
                adminUser.setRealName("管理员");
                adminUser.setRole(9); // 管理员角色
                adminUser.setStatus(1); // 正常状态
                userRepository.save(adminUser);
                System.out.println("Admin user created successfully");
                return jwtUtil.generateToken(adminUser.getId(), adminUser.getRole());
            } else {
                throw new RuntimeException("User not found with phone: " + phone);
            }
        }
    }

    public SysUser register(SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public SysUser getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        SysUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("旧密码不正确");
        }
        
        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}