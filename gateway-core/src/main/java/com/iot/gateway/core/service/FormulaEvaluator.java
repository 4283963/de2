package com.iot.gateway.core.service;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FormulaEvaluator {

    private static final Logger log = LoggerFactory.getLogger(FormulaEvaluator.class);

    private static final String VARIABLE_NAME = "value";

    public double evaluate(String formula, double originalValue) {
        if (formula == null || formula.isBlank()) {
            return originalValue;
        }

        String trimmed = formula.trim();
        try {
            Expression expression = new ExpressionBuilder(trimmed)
                    .variables(VARIABLE_NAME)
                    .build()
                    .setVariable(VARIABLE_NAME, originalValue);

            double result = expression.evaluate();

            if (Double.isNaN(result) || Double.isInfinite(result)) {
                log.warn("Formula evaluation produced invalid result (NaN/Infinite). formula='{}', value={}",
                        trimmed, originalValue);
                return originalValue;
            }

            log.debug("Formula evaluated: '{}' with value={} -> {}", trimmed, originalValue, result);
            return result;
        } catch (Exception e) {
            log.error("Failed to evaluate formula '{}' with value={}: {}", trimmed, originalValue, e.getMessage());
            return originalValue;
        }
    }

    public FormulaValidationResult validate(String formula) {
        if (formula == null || formula.isBlank()) {
            return FormulaValidationResult.success();
        }

        String trimmed = formula.trim();
        try {
            Expression expression = new ExpressionBuilder(trimmed)
                    .variables(VARIABLE_NAME)
                    .build()
                    .setVariable(VARIABLE_NAME, 1.0);
            double result = expression.evaluate();
            if (Double.isNaN(result) || Double.isInfinite(result)) {
                return FormulaValidationResult.failure("Formula produced NaN or Infinite result with test value=1.0");
            }
            return FormulaValidationResult.success();
        } catch (Exception e) {
            return FormulaValidationResult.failure(e.getMessage());
        }
    }

    public static class FormulaValidationResult {

        private final boolean valid;

        private final String errorMessage;

        private FormulaValidationResult(boolean valid, String errorMessage) {
            this.valid = valid;
            this.errorMessage = errorMessage;
        }

        public static FormulaValidationResult success() {
            return new FormulaValidationResult(true, null);
        }

        public static FormulaValidationResult failure(String errorMessage) {
            return new FormulaValidationResult(false, errorMessage);
        }

        public boolean isValid() {
            return valid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
