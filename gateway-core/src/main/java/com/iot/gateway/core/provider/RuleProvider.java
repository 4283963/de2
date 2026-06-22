package com.iot.gateway.core.provider;

import com.iot.gateway.core.model.FilterRuleDefinition;

import java.util.List;

public interface RuleProvider {

    List<FilterRuleDefinition> getActiveRules();

    void refresh();
}
