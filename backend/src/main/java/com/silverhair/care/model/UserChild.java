package com.silverhair.care.model;

import javax.persistence.*;

@Entity
@Table(name = "user_child")
public class UserChild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    // @Column(name = "name", length = 50)
    // private String name;

    // @Column(name = "phone", length = 20)
    // private String phone;

    // @Column(name = "relation", length = 20)
    // private String relation; // 与老人关系

    // @Column(name = "status", columnDefinition = "tinyint default 1")
    // private Integer status; // 0禁用 1正常

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

    // public String getRelation() {
    //     return relation;
    // }

    // public void setRelation(String relation) {
    //     this.relation = relation;
    // }

    // public Integer getStatus() {
    //     return status;
    // }

    // public void setStatus(Integer status) {
    //     this.status = status;
    // }
}