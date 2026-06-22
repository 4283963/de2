package com.iot.gateway.admin.controller;

import com.iot.gateway.admin.dto.FilterRuleRequest;
import com.iot.gateway.admin.dto.FilterRuleResponse;
import com.iot.gateway.admin.dto.PageResponse;
import com.iot.gateway.admin.service.FilterRuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/filter-rules")
public class FilterRuleController {

    private final FilterRuleService filterRuleService;

    public FilterRuleController(FilterRuleService filterRuleService) {
        this.filterRuleService = filterRuleService;
    }

    @PostMapping
    public ResponseEntity<FilterRuleResponse> create(@Valid @RequestBody FilterRuleRequest request) {
        FilterRuleResponse response = filterRuleService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilterRuleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(filterRuleService.getById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<FilterRuleResponse>> list(
            @RequestParam(required = false) String deviceId,
            @RequestParam(required = false) String sensorCode,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(filterRuleService.list(deviceId, sensorCode, enabled, page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilterRuleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody FilterRuleRequest request) {
        return ResponseEntity.ok(filterRuleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        filterRuleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
