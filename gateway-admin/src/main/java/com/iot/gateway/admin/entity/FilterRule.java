package com.iot.gateway.admin.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "filter_rules")
@EntityListeners(AuditingEntityListener.class)
public class FilterRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_name", nullable = false, length = 128)
    private String ruleName;

    @Column(name = "description", length = 512)
    private String description;

    @Column(name = "device_id", length = 64)
    private String deviceId;

    @Column(name = "sensor_code", nullable = false, length = 64)
    private String sensorCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator", nullable = false, length = 16)
    private Operator operator;

    @Column(name = "threshold", nullable = false)
    private Double threshold;

    @Column(name = "action_type", length = 32)
    private String actionType;

    @Column(name = "action_target", length = 256)
    private String actionTarget;

    @Column(name = "transform_formula", length = 512)
    private String transformFormula;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum Operator {
        GT,
        LT,
        GTE,
        LTE,
        EQ,
        NEQ
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSensorCode() {
        return sensorCode;
    }

    public void setSensorCode(String sensorCode) {
        this.sensorCode = sensorCode;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionTarget() {
        return actionTarget;
    }

    public void setActionTarget(String actionTarget) {
        this.actionTarget = actionTarget;
    }

    public String getTransformFormula() {
        return transformFormula;
    }

    public void setTransformFormula(String transformFormula) {
        this.transformFormula = transformFormula;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
