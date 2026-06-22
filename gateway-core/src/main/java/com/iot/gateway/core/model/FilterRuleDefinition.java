package com.iot.gateway.core.model;

public class FilterRuleDefinition {

    private Long id;

    private String ruleName;

    private String description;

    private String deviceId;

    private String sensorCode;

    private Operator operator;

    private Double threshold;

    private String actionType;

    private String actionTarget;

    private boolean enabled;

    public enum Operator {
        GT,
        LT,
        GTE,
        LTE,
        EQ,
        NEQ;

        public boolean evaluate(double actual, double threshold) {
            return switch (this) {
                case GT -> actual > threshold;
                case LT -> actual < threshold;
                case GTE -> actual >= threshold;
                case LTE -> actual <= threshold;
                case EQ -> Double.compare(actual, threshold) == 0;
                case NEQ -> Double.compare(actual, threshold) != 0;
            };
        }
    }

    public FilterRuleDefinition() {
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
