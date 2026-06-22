package com.iot.gateway.core.model;

import java.time.Instant;
import java.util.Map;

public class SensorData {

    private String deviceId;

    private String sensorCode;

    private Double value;

    private String unit;

    private Instant timestamp;

    private Map<String, Object> extra;

    public SensorData() {
    }

    public SensorData(String deviceId, String sensorCode, Double value, String unit, Instant timestamp, Map<String, Object> extra) {
        this.deviceId = deviceId;
        this.sensorCode = sensorCode;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
        this.extra = extra;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
}
