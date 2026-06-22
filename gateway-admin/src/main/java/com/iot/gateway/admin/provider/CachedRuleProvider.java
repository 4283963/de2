package com.iot.gateway.admin.provider;

import com.iot.gateway.admin.entity.FilterRule;
import com.iot.gateway.admin.repository.FilterRuleRepository;
import com.iot.gateway.core.event.RuleCacheRefreshEvent;
import com.iot.gateway.core.model.FilterRuleDefinition;
import com.iot.gateway.core.provider.RuleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CachedRuleProvider implements RuleProvider {

    private static final Logger log = LoggerFactory.getLogger(CachedRuleProvider.class);

    private final FilterRuleRepository filterRuleRepository;

    private final AtomicReference<List<FilterRuleDefinition>> cache = new AtomicReference<>(Collections.emptyList());

    private volatile long lastRefreshTime = 0;

    public CachedRuleProvider(FilterRuleRepository filterRuleRepository) {
        this.filterRuleRepository = filterRuleRepository;
    }

    @Override
    public List<FilterRuleDefinition> getActiveRules() {
        List<FilterRuleDefinition> rules = cache.get();
        if (rules.isEmpty()) {
            synchronized (this) {
                rules = cache.get();
                if (rules.isEmpty()) {
                    refresh();
                    rules = cache.get();
                }
            }
        }
        return rules;
    }

    @Override
    public void refresh() {
        try {
            List<FilterRule> entities = filterRuleRepository.findByEnabledTrue();
            List<FilterRuleDefinition> definitions = entities.stream()
                    .map(CachedRuleProvider::toDefinition)
                    .toList();
            cache.set(new CopyOnWriteArrayList<>(definitions));
            lastRefreshTime = System.currentTimeMillis();
            log.info("Rule cache refreshed, active rules count: {}", definitions.size());
        } catch (Exception e) {
            log.error("Failed to refresh rule cache: {}", e.getMessage(), e);
        }
    }

    @EventListener
    public void onRuleCacheRefreshEvent(RuleCacheRefreshEvent event) {
        log.info("Received rule cache refresh event: {}", event);
        refresh();
    }

    @Scheduled(fixedDelayString = "${gateway.rule.cache.refresh-interval:30000}")
    public void scheduledRefresh() {
        log.debug("Scheduled rule cache refresh, last refresh was {}ms ago",
                System.currentTimeMillis() - lastRefreshTime);
        refresh();
    }

    private static FilterRuleDefinition toDefinition(FilterRule entity) {
        FilterRuleDefinition def = new FilterRuleDefinition();
        def.setId(entity.getId());
        def.setRuleName(entity.getRuleName());
        def.setDescription(entity.getDescription());
        def.setDeviceId(entity.getDeviceId());
        def.setSensorCode(entity.getSensorCode());
        def.setOperator(FilterRuleDefinition.Operator.valueOf(entity.getOperator().name()));
        def.setThreshold(entity.getThreshold());
        def.setActionType(entity.getActionType());
        def.setActionTarget(entity.getActionTarget());
        def.setTransformFormula(entity.getTransformFormula());
        def.setEnabled(Boolean.TRUE.equals(entity.getEnabled()));
        return def;
    }
}
