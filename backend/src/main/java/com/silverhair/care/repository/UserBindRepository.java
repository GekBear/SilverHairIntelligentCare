package com.silverhair.care.repository;

import com.silverhair.care.model.UserBind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBindRepository extends JpaRepository<UserBind, Long> {
    List<UserBind> findByElderId(Long elderId);
    List<UserBind> findByChildId(Long childId);
}