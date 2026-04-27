package com.silverhair.care.repository;

import com.silverhair.care.model.UserElder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserElderRepository extends JpaRepository<UserElder, Long> {
    UserElder findByUserId(Long userId);
    
    Page<UserElder> findByNameContaining(String name, Pageable pageable);
    
    Page<UserElder> findByPhoneContaining(String phone, Pageable pageable);
    
    Page<UserElder> findByStatus(Integer status, Pageable pageable);
}