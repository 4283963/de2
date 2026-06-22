package com.iot.gateway.core.event;

import org.springframework.context.ApplicationEvent;

public class RuleCacheRefreshEvent extends ApplicationEvent {

    private final String source;

    private final Long ruleId;

    private final Action action;

    public enum Action {
        CREATE,
        UPDATE,
        DELETE
    }

    public RuleCacheRefreshEvent(Object source, Long ruleId, Action action) {
        super(source);
        this.source = source.getClass().getSimpleName();
        this.ruleId = ruleId;
        this.action = action;
    }

    public String getSourceName() {
        return source;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "RuleCacheRefreshEvent{source=" + source + ", ruleId=" + ruleId + ", action=" + action + "}";
    }
}
