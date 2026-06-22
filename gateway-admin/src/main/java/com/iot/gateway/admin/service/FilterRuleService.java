package com.iot.gateway.admin.service;

import com.iot.gateway.admin.dto.FilterRuleRequest;
import com.iot.gateway.admin.dto.FilterRuleResponse;
import com.iot.gateway.admin.dto.PageResponse;
import com.iot.gateway.admin.entity.FilterRule;
import com.iot.gateway.admin.exception.ResourceNotFoundException;
import com.iot.gateway.admin.repository.FilterRuleRepository;
import com.iot.gateway.core.event.RuleCacheRefreshEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilterRuleService {

    private final FilterRuleRepository filterRuleRepository;

    private final ApplicationEventPublisher eventPublisher;

    public FilterRuleService(FilterRuleRepository filterRuleRepository, ApplicationEventPublisher eventPublisher) {
        this.filterRuleRepository = filterRuleRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public FilterRuleResponse create(FilterRuleRequest request) {
        FilterRule entity = new FilterRule();
        copyFromRequest(request, entity);
        FilterRule saved = filterRuleRepository.save(entity);
        eventPublisher.publishEvent(new RuleCacheRefreshEvent(this, saved.getId(), RuleCacheRefreshEvent.Action.CREATE));
        return FilterRuleResponse.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public FilterRuleResponse getById(Long id) {
        FilterRule entity = filterRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FilterRule", "id", id));
        return FilterRuleResponse.fromEntity(entity);
    }

    @Transactional(readOnly = true)
    public PageResponse<FilterRuleResponse> list(String deviceId, String sensorCode, Boolean enabled,
                                                 int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<FilterRule> pageResult = filterRuleRepository.findByConditions(deviceId, sensorCode, enabled, pageable);
        return PageResponse.from(pageResult, FilterRuleResponse::fromEntity);
    }

    @Transactional
    public FilterRuleResponse update(Long id, FilterRuleRequest request) {
        FilterRule entity = filterRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FilterRule", "id", id));
        copyFromRequest(request, entity);
        FilterRule saved = filterRuleRepository.save(entity);
        eventPublisher.publishEvent(new RuleCacheRefreshEvent(this, saved.getId(), RuleCacheRefreshEvent.Action.UPDATE));
        return FilterRuleResponse.fromEntity(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!filterRuleRepository.existsById(id)) {
            throw new ResourceNotFoundException("FilterRule", "id", id);
        }
        filterRuleRepository.deleteById(id);
        eventPublisher.publishEvent(new RuleCacheRefreshEvent(this, id, RuleCacheRefreshEvent.Action.DELETE));
    }

    private void copyFromRequest(FilterRuleRequest request, FilterRule entity) {
        entity.setRuleName(request.getRuleName());
        entity.setDescription(request.getDescription());
        entity.setDeviceId(request.getDeviceId());
        entity.setSensorCode(request.getSensorCode());
        entity.setOperator(request.getOperator());
        entity.setThreshold(request.getThreshold());
        entity.setActionType(request.getActionType());
        entity.setActionTarget(request.getActionTarget());
        entity.setEnabled(request.getEnabled() != null ? request.getEnabled() : Boolean.TRUE);
    }
}
