package com.iot.gateway.admin.dto;

import com.iot.gateway.admin.entity.FilterRule;

import java.time.LocalDateTime;

public class FilterRuleResponse {

    private Long id;

    private String ruleName;

    private String description;

    private String deviceId;

    private String sensorCode;

    private FilterRule.Operator operator;

    private Double threshold;

    private String actionType;

    private String actionTarget;

    private Boolean enabled;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static FilterRuleResponse fromEntity(FilterRule entity) {
        FilterRuleResponse resp = new FilterRuleResponse();
        resp.setId(entity.getId());
        resp.setRuleName(entity.getRuleName());
        resp.setDescription(entity.getDescription());
        resp.setDeviceId(entity.getDeviceId());
        resp.setSensorCode(entity.getSensorCode());
        resp.setOperator(entity.getOperator());
        resp.setThreshold(entity.getThreshold());
        resp.setActionType(entity.getActionType());
        resp.setActionTarget(entity.getActionTarget());
        resp.setEnabled(entity.getEnabled());
        resp.setCreatedAt(entity.getCreatedAt());
        resp.setUpdatedAt(entity.getUpdatedAt());
        return resp;
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

    public FilterRule.Operator getOperator() {
        return operator;
    }

    public void setOperator(FilterRule.Operator operator) {
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
