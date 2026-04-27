package com.silverhair.care.repository;

import com.silverhair.care.model.AlarmFence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmFenceRepository extends JpaRepository<AlarmFence, Long> {
    List<AlarmFence> findByElderId(Long elderId);
}