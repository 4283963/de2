package com.iot.gateway.core.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.gateway.core.model.SensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MqttMessageService {

    private static final Logger log = LoggerFactory.getLogger(MqttMessageService.class);

    private final ObjectMapper objectMapper;

    private final RuleEvaluationService ruleEvaluationService;

    public MqttMessageService(ObjectMapper objectMapper, RuleEvaluationService ruleEvaluationService) {
        this.objectMapper = objectMapper;
        this.ruleEvaluationService = ruleEvaluationService;
    }

    public Optional<SensorData> parseMessage(String topic, byte[] payload) {
        try {
            String json = new String(payload);
            Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});

            SensorData data = new SensorData();

            if (topic != null && !topic.isBlank()) {
                String[] parts = topic.split("/");
                if (parts.length >= 3) {
                    data.setDeviceId(parts[1]);
                    data.setSensorCode(parts[2]);
                }
            }

            if (map.containsKey("deviceId") && map.get("deviceId") != null) {
                data.setDeviceId(map.get("deviceId").toString());
            }
            if (map.containsKey("sensorCode") && map.get("sensorCode") != null) {
                data.setSensorCode(map.get("sensorCode").toString());
            }
            if (map.containsKey("value") && map.get("value") != null) {
                data.setValue(Double.parseDouble(map.get("value").toString()));
            }
            if (map.containsKey("unit") && map.get("unit") != null) {
                data.setUnit(map.get("unit").toString());
            }
            if (map.containsKey("timestamp") && map.get("timestamp") != null) {
                String ts = map.get("timestamp").toString();
                try {
                    data.setTimestamp(Instant.parse(ts));
                } catch (Exception e) {
                    data.setTimestamp(Instant.ofEpochMilli(Long.parseLong(ts)));
                }
            } else {
                data.setTimestamp(Instant.now());
            }
            if (map.containsKey("extra") && map.get("extra") != null) {
                data.setExtra(objectMapper.convertValue(map.get("extra"), new TypeReference<Map<String, Object>>() {}));
            }

            if (data.getDeviceId() == null || data.getSensorCode() == null || data.getValue() == null) {
                log.warn("Incomplete sensor data from topic {}: {}", topic, json);
                return Optional.empty();
            }

            List<RuleEvaluationService.MatchedRule> matchedRules = ruleEvaluationService.evaluate(data);
            if (!matchedRules.isEmpty()) {
                log.info("Sensor data from topic {} triggered {} rule(s)", topic, matchedRules.size());
                for (RuleEvaluationService.MatchedRule mr : matchedRules) {
                    log.info("Triggered rule: id={}, name={}, actionType={}, actionTarget={}",
                            mr.getRule().getId(), mr.getRule().getRuleName(),
                            mr.getRule().getActionType(), mr.getRule().getActionTarget());
                }
            }

            return Optional.of(data);
        } catch (Exception e) {
            log.error("Failed to parse MQTT message from topic {}: {}", topic, e.getMessage());
            return Optional.empty();
        }
    }
}
