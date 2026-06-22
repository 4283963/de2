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

    private final FormulaEvaluator formulaEvaluator;

    public RuleEvaluationService(RuleProvider ruleProvider, FormulaEvaluator formulaEvaluator) {
        this.ruleProvider = ruleProvider;
        this.formulaEvaluator = formulaEvaluator;
    }

    public List<MatchedRule> evaluate(SensorData sensorData) {
        List<FilterRuleDefinition> activeRules = ruleProvider.getActiveRules();
        List<MatchedRule> matched = new ArrayList<>();

        for (FilterRuleDefinition rule : activeRules) {
            double effectiveValue = formulaEvaluator.evaluate(rule.getTransformFormula(), sensorData.getValue());

            if (matches(rule, sensorData, effectiveValue)) {
                log.info("Rule matched: ruleId={}, ruleName='{}', deviceId={}, sensorCode={}, originalValue={}, transformedValue={}, operator={}, threshold={}",
                        rule.getId(), rule.getRuleName(), sensorData.getDeviceId(),
                        sensorData.getSensorCode(), sensorData.getValue(), effectiveValue,
                        rule.getOperator(), rule.getThreshold());

                matched.add(new MatchedRule(rule, sensorData, effectiveValue));
            }
        }

        if (matched.isEmpty()) {
            log.debug("No rules matched for deviceId={}, sensorCode={}, value={}",
                    sensorData.getDeviceId(), sensorData.getSensorCode(), sensorData.getValue());
        }

        return matched;
    }

    private boolean matches(FilterRuleDefinition rule, SensorData data, double effectiveValue) {
        if (rule.getDeviceId() != null && !rule.getDeviceId().equals(data.getDeviceId())) {
            return false;
        }
        if (!rule.getSensorCode().equals(data.getSensorCode())) {
            return false;
        }
        return rule.getOperator().evaluate(effectiveValue, rule.getThreshold());
    }

    public static class MatchedRule {

        private final FilterRuleDefinition rule;

        private final SensorData sensorData;

        private final double transformedValue;

        public MatchedRule(FilterRuleDefinition rule, SensorData sensorData, double transformedValue) {
            this.rule = rule;
            this.sensorData = sensorData;
            this.transformedValue = transformedValue;
        }

        public FilterRuleDefinition getRule() {
            return rule;
        }

        public SensorData getSensorData() {
            return sensorData;
        }

        public double getTransformedValue() {
            return transformedValue;
        }
    }
}
