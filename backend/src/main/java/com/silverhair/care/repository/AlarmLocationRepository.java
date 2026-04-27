package com.silverhair.care.repository;

import com.silverhair.care.model.AlarmLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmLocationRepository extends JpaRepository<AlarmLocation, Long> {
    List<AlarmLocation> findByElderIdOrderByCreateTimeDesc(Long elderId);
}