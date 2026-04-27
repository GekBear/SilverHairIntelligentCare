package com.silverhair.care.model;

import javax.persistence.*;

@Entity
@Table(name = "user_elder")
public class UserElder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    // @Column(name = "name", length = 50)
    // private String name;

    // @Column(name = "phone", length = 20)
    // private String phone;

    // @Column(name = "age")
    // private Integer age;

    // @Column(name = "gender", columnDefinition = "tinyint default 0")
    // private Integer gender; // 0未知 1男 2女

    // @Column(name = "address", length = 255)
    // private String address;

    // @Column(name = "emergency_phone", length = 20)
    // private String emergencyPhone;

    // @Column(name = "medical_history", length = 500)
    // private String medicalHistory;

    // @Column(name = "status", columnDefinition = "tinyint default 1")
    // private Integer status; // 0禁用 1正常

    // @Column(name = "remark", length = 500)
    // private String remark;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public String getPhone() {
    //     return phone;
    // }

    // public void setPhone(String phone) {
    //     this.phone = phone;
    // }

    // public Integer getAge() {
    //     return age;
    // }

    // public void setAge(Integer age) {
    //     this.age = age;
    // }

    // public Integer getGender() {
    //     return gender;
    // }

    // public void setGender(Integer gender) {
    //     this.gender = gender;
    // }

    // public String getAddress() {
    //     return address;
    // }

    // public void setAddress(String address) {
    //     this.address = address;
    // }

    // public String getEmergencyPhone() {
    //     return emergencyPhone;
    // }

    // public void setEmergencyPhone(String emergencyPhone) {
    //     this.emergencyPhone = emergencyPhone;
    // }

    // public String getMedicalHistory() {
    //     return medicalHistory;
    // }

    // public void setMedicalHistory(String medicalHistory) {
    //     this.medicalHistory = medicalHistory;
    // }

    // public Integer getStatus() {
    //     return status;
    // }

    // public void setStatus(Integer status) {
    //     this.status = status;
    // }

    // public String getRemark() {
    //     return remark;
    // }

    // public void setRemark(String remark) {
    //     this.remark = remark;
    // }
}