package com.silverhair.care.model;

import javax.persistence.*;

@Entity
@Table(name = "alarm_fence")
public class AlarmFence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "elder_id", nullable = false)
    private Long elderId;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "longitude", precision = 12, scale = 8)
    private Double longitude;

    @Column(name = "latitude", precision = 12, scale = 8)
    private Double latitude;

    @Column(name = "radius", columnDefinition = "int default 200")
    private Integer radius; // 半径米

    @Column(name = "status", columnDefinition = "tinyint default 1")
    private Integer status;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getElderId() {
        return elderId;
    }

    public void setElderId(Long elderId) {
        this.elderId = elderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}