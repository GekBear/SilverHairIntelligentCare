package com.silverhair.care.service;

import com.silverhair.care.model.WorkOrder;
import com.silverhair.care.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    public WorkOrder createWorkOrder(WorkOrder workOrder) {
        workOrder.setCreatedAt(new Date());
        workOrder.setUpdatedAt(new Date());
        return workOrderRepository.save(workOrder);
    }

    public List<WorkOrder> getWorkOrderList(String elderlyName, String status, String serviceType, int page, int size) {
        // 这里可以添加复杂的查询逻辑
        return workOrderRepository.findAll();
    }

    public WorkOrder getWorkOrderById(Long id) {
        return workOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("工单不存在"));
    }

    public WorkOrder updateWorkOrder(Long id, WorkOrder workOrder) {
        WorkOrder existingWorkOrder = workOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("工单不存在"));
        existingWorkOrder.setElderlyName(workOrder.getElderlyName());
        existingWorkOrder.setElderlyId(workOrder.getElderlyId());
        existingWorkOrder.setServiceType(workOrder.getServiceType());
        existingWorkOrder.setDescription(workOrder.getDescription());
        existingWorkOrder.setStatus(workOrder.getStatus());
        existingWorkOrder.setHandler(workOrder.getHandler());
        existingWorkOrder.setRemark(workOrder.getRemark());
        existingWorkOrder.setUpdatedAt(new Date());
        return workOrderRepository.save(existingWorkOrder);
    }

    public void deleteWorkOrder(Long id) {
        WorkOrder existingWorkOrder = workOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("工单不存在"));
        workOrderRepository.delete(existingWorkOrder);
    }
}
