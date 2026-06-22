package com.iot.gateway.admin.dto;

import com.iot.gateway.admin.entity.FilterRule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FilterRuleRequest {

    @NotBlank(message = "规则名称不能为空")
    @Size(max = 128, message = "规则名称长度不能超过128个字符")
    private String ruleName;

    @Size(max = 512, message = "规则描述长度不能超过512个字符")
    private String description;

    @Size(max = 64, message = "设备ID长度不能超过64个字符")
    private String deviceId;

    @NotBlank(message = "传感器编码不能为空")
    @Size(max = 64, message = "传感器编码长度不能超过64个字符")
    private String sensorCode;

    @NotNull(message = "比较运算符不能为空")
    private FilterRule.Operator operator;

    @NotNull(message = "阈值不能为空")
    private Double threshold;

    @Size(max = 32, message = "动作类型长度不能超过32个字符")
    private String actionType;

    @Size(max = 256, message = "动作目标长度不能超过256个字符")
    private String actionTarget;

    private Boolean enabled = true;

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
}
