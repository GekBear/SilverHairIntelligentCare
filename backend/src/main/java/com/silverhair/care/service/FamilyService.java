package com.silverhair.care.service;

import com.silverhair.care.common.PageResult;
import com.silverhair.care.common.Result;
import com.silverhair.care.model.UserChild;
import com.silverhair.care.repository.UserChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FamilyService {

    @Autowired
    private UserChildRepository familyRepository;

    public UserChild createFamily(UserChild family) {
        return familyRepository.save(family);
    }

    public PageResult<UserChild> getFamilyList(String name, String phone, Long elderlyId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createTime"));
        
        Page<UserChild> pageResult;
        if (StringUtils.hasText(name)) {
            pageResult = familyRepository.findByNameContaining(name, pageable);
        } else if (StringUtils.hasText(phone)) {
            pageResult = familyRepository.findByPhoneContaining(phone, pageable);
        } else {
            pageResult = familyRepository.findAll(pageable);
        }
        
        return PageResult.of(pageResult.getContent(), pageResult.getTotalElements(), page, size);
    }

    public UserChild getFamilyById(Long id) {
        return familyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("子女信息不存在"));
    }

    public UserChild updateFamily(Long id, UserChild family) {
        UserChild existingFamily = familyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("子女信息不存在"));
        
        if (family.getName() != null) {
            existingFamily.setName(family.getName());
        }
        if (family.getPhone() != null) {
            existingFamily.setPhone(family.getPhone());
        }
        if (family.getRelation() != null) {
            existingFamily.setRelation(family.getRelation());
        }
        if (family.getStatus() != null) {
            existingFamily.setStatus(family.getStatus());
        }
        
        return familyRepository.save(existingFamily);
    }

    public void deleteFamily(Long id) {
        if (!familyRepository.existsById(id)) {
            throw new RuntimeException("子女信息不存在");
        }
        familyRepository.deleteById(id);
    }
}
