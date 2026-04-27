package com.silverhair.care.repository;

import com.silverhair.care.model.AlarmSos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmSosRepository extends JpaRepository<AlarmSos, Long> {
    List<AlarmSos> findByElderIdOrderByCreateTimeDesc(Long elderId);
}