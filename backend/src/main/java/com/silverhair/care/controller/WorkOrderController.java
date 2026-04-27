package com.silverhair.care.controller;

import com.silverhair.care.model.WorkOrder;
import com.silverhair.care.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workorder")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @PostMapping
    public ResponseEntity<?> createWorkOrder(@RequestBody WorkOrder workOrder) {
        try {
            WorkOrder createdWorkOrder = workOrderService.createWorkOrder(workOrder);
            return ResponseEntity.ok(createdWorkOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getWorkOrderList(
            @RequestParam(required = false) String elderlyName,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String serviceType,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<WorkOrder> workOrderList = workOrderService.getWorkOrderList(elderlyName, status, serviceType, page, size);
            return ResponseEntity.ok(workOrderList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkOrderById(@PathVariable Long id) {
        try {
            WorkOrder workOrder = workOrderService.getWorkOrderById(id);
            return ResponseEntity.ok(workOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkOrder(@PathVariable Long id, @RequestBody WorkOrder workOrder) {
        try {
            WorkOrder updatedWorkOrder = workOrderService.updateWorkOrder(id, workOrder);
            return ResponseEntity.ok(updatedWorkOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkOrder(@PathVariable Long id) {
        try {
            workOrderService.deleteWorkOrder(id);
            return ResponseEntity.ok("工单删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
