package com.silverhair.care.repository;

import com.silverhair.care.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    Optional<SysUser> findByPhone(String phone);
    Optional<SysUser> findByUsername(String username);
}