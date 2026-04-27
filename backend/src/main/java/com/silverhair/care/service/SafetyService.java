package com.silverhair.care.service;

import com.silverhair.care.model.AlarmFence;
import com.silverhair.care.model.AlarmLocation;
import com.silverhair.care.model.AlarmSos;
import com.silverhair.care.repository.AlarmFenceRepository;
import com.silverhair.care.repository.AlarmLocationRepository;
import com.silverhair.care.repository.AlarmSosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SafetyService {

    @Autowired
    private AlarmSosRepository alarmSosRepository;

    @Autowired
    private AlarmLocationRepository alarmLocationRepository;

    @Autowired
    private AlarmFenceRepository alarmFenceRepository;

    // SOS求助相关
    public AlarmSos createSos(AlarmSos sos) {
        return alarmSosRepository.save(sos);
    }

    public List<AlarmSos> getSosByElderId(Long elderId) {
        return alarmSosRepository.findByElderIdOrderByCreateTimeDesc(elderId);
    }

    public AlarmSos updateSosStatus(Long id, Integer status) {
        AlarmSos sos = alarmSosRepository.findById(id).orElseThrow(() -> new RuntimeException("SOS record not found"));
        sos.setStatus(status);
        return alarmSosRepository.save(sos);
    }

    // 定位相关
    public AlarmLocation createLocation(AlarmLocation location) {
        return alarmLocationRepository.save(location);
    }

    public List<AlarmLocation> getLocationsByElderId(Long elderId) {
        return alarmLocationRepository.findByElderIdOrderByCreateTimeDesc(elderId);
    }

    // 电子围栏相关
    public AlarmFence createFence(AlarmFence fence) {
        return alarmFenceRepository.save(fence);
    }

    public List<AlarmFence> getFencesByElderId(Long elderId) {
        return alarmFenceRepository.findByElderId(elderId);
    }

    public AlarmFence updateFence(Long id, AlarmFence fence) {
        AlarmFence existingFence = alarmFenceRepository.findById(id).orElseThrow(() -> new RuntimeException("Fence not found"));
        existingFence.setName(fence.getName());
        existingFence.setLongitude(fence.getLongitude());
        existingFence.setLatitude(fence.getLatitude());
        existingFence.setRadius(fence.getRadius());
        existingFence.setStatus(fence.getStatus());
        return alarmFenceRepository.save(existingFence);
    }

    public void deleteFence(Long id) {
        alarmFenceRepository.deleteById(id);
    }
}