package com.silverhair.care.repository;

import com.silverhair.care.model.UserChild;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChildRepository extends JpaRepository<UserChild, Long> {
    UserChild findByUserId(Long userId);
    
    Page<UserChild> findByNameContaining(String name, Pageable pageable);
    
    Page<UserChild> findByPhoneContaining(String phone, Pageable pageable);
    
    Page<UserChild> findByStatus(Integer status, Pageable pageable);
}