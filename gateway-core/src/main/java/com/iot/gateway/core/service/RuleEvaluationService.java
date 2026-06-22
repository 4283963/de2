package com.iot.gateway.core.service;

import com.iot.gateway.core.model.FilterRuleDefinition;
import com.iot.gateway.core.model.SensorData;
import com.iot.gateway.core.provider.RuleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleEvaluationService {

    private static final Logger log = LoggerFactory.getLogger(RuleEvaluationService.class);

    private final RuleProvider ruleProvider;

    public RuleEvaluationService(RuleProvider ruleProvider) {
        this.ruleProvider = ruleProvider;
    }

    public List<MatchedRule> evaluate(SensorData sensorData) {
        List<FilterRuleDefinition> activeRules = ruleProvider.getActiveRules();
        List<MatchedRule> matched = new ArrayList<>();

        for (FilterRuleDefinition rule : activeRules) {
            if (matches(rule, sensorData)) {
                log.info("Rule matched: ruleId={}, ruleName='{}', deviceId={}, sensorCode={}, value={}, operator={}, threshold={}",
                        rule.getId(), rule.getRuleName(), sensorData.getDeviceId(),
                        sensorData.getSensorCode(), sensorData.getValue(),
                        rule.getOperator(), rule.getThreshold());

                matched.add(new MatchedRule(rule, sensorData));
            }
        }

        if (matched.isEmpty()) {
            log.debug("No rules matched for deviceId={}, sensorCode={}, value={}",
                    sensorData.getDeviceId(), sensorData.getSensorCode(), sensorData.getValue());
        }

        return matched;
    }

    private boolean matches(FilterRuleDefinition rule, SensorData data) {
        if (rule.getDeviceId() != null && !rule.getDeviceId().equals(data.getDeviceId())) {
            return false;
        }
        if (!rule.getSensorCode().equals(data.getSensorCode())) {
            return false;
        }
        return rule.getOperator().evaluate(data.getValue(), rule.getThreshold());
    }

    public static class MatchedRule {

        private final FilterRuleDefinition rule;

        private final SensorData sensorData;

        public MatchedRule(FilterRuleDefinition rule, SensorData sensorData) {
            this.rule = rule;
            this.sensorData = sensorData;
        }

        public FilterRuleDefinition getRule() {
            return rule;
        }

        public SensorData getSensorData() {
            return sensorData;
        }
    }
}
