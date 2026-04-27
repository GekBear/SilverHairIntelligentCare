package com.silverhair.care.service;

import com.silverhair.care.model.Policy;
import com.silverhair.care.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public Policy createPolicy(Policy policy) {
        policy.setCreatedAt(new Date());
        policy.setUpdatedAt(new Date());
        return policyRepository.save(policy);
    }

    public List<Policy> getPolicyList(String title, String type, String status, int page, int size) {
        // 这里可以添加复杂的查询逻辑
        return policyRepository.findAll();
    }

    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id).orElseThrow(() -> new RuntimeException("政策不存在"));
    }

    public Policy updatePolicy(Long id, Policy policy) {
        Policy existingPolicy = policyRepository.findById(id).orElseThrow(() -> new RuntimeException("政策不存在"));
        existingPolicy.setTitle(policy.getTitle());
        existingPolicy.setType(policy.getType());
        existingPolicy.setPublishDate(policy.getPublishDate());
        existingPolicy.setEffectiveDate(policy.getEffectiveDate());
        existingPolicy.setExpireDate(policy.getExpireDate());
        existingPolicy.setContent(policy.getContent());
        existingPolicy.setStatus(policy.getStatus());
        existingPolicy.setUpdatedAt(new Date());
        return policyRepository.save(existingPolicy);
    }

    public void deletePolicy(Long id) {
        Policy existingPolicy = policyRepository.findById(id).orElseThrow(() -> new RuntimeException("政策不存在"));
        policyRepository.delete(existingPolicy);
    }
}
