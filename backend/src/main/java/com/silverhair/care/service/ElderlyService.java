package com.silverhair.care.service;

import com.silverhair.care.common.PageResult;
import com.silverhair.care.model.UserElder;
import com.silverhair.care.repository.UserElderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ElderlyService {

    @Autowired
    private UserElderRepository elderlyRepository;

    public UserElder createElderly(UserElder elderly) {
        return elderlyRepository.save(elderly);
    }

    public PageResult<UserElder> getElderlyList(String name, String phone, Integer status, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createTime"));
        
        // 根据条件查询
        Page<UserElder> pageResult;
        if (StringUtils.hasText(name)) {
            pageResult = elderlyRepository.findByNameContaining(name, pageable);
        } else if (StringUtils.hasText(phone)) {
            pageResult = elderlyRepository.findByPhoneContaining(phone, pageable);
        } else if (status != null) {
            pageResult = elderlyRepository.findByStatus(status, pageable);
        } else {
            pageResult = elderlyRepository.findAll(pageable);
        }
        
        return PageResult.of(pageResult.getContent(), pageResult.getTotalElements(), page, size);
    }

    public UserElder getElderlyById(Long id) {
        return elderlyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("老人信息不存在"));
    }

    public UserElder updateElderly(Long id, UserElder elderly) {
        UserElder existingElderly = elderlyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("老人信息不存在"));
        
        // 更新字段
        if (elderly.getName() != null) {
            existingElderly.setName(elderly.getName());
        }
        if (elderly.getPhone() != null) {
            existingElderly.setPhone(elderly.getPhone());
        }
        if (elderly.getAddress() != null) {
            existingElderly.setAddress(elderly.getAddress());
        }
        if (elderly.getStatus() != null) {
            existingElderly.setStatus(elderly.getStatus());
        }
        if (elderly.getRemark() != null) {
            existingElderly.setRemark(elderly.getRemark());
        }
        
        return elderlyRepository.save(existingElderly);
    }

    public void deleteElderly(Long id) {
        if (!elderlyRepository.existsById(id)) {
            throw new RuntimeException("老人信息不存在");
        }
        elderlyRepository.deleteById(id);
    }
}
