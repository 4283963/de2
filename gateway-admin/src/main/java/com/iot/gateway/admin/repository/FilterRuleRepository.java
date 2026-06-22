package com.iot.gateway.admin.repository;

import com.iot.gateway.admin.entity.FilterRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilterRuleRepository extends JpaRepository<FilterRule, Long> {

    Optional<FilterRule> findByRuleName(String ruleName);

    List<FilterRule> findByEnabledTrue();

    List<FilterRule> findBySensorCodeAndEnabledTrue(String sensorCode);

    List<FilterRule> findByDeviceIdAndEnabledTrue(String deviceId);

    @Query("SELECT r FROM FilterRule r WHERE " +
           "(:deviceId IS NULL OR r.deviceId = :deviceId) AND " +
           "(:sensorCode IS NULL OR r.sensorCode = :sensorCode) AND " +
           "(:enabled IS NULL OR r.enabled = :enabled)")
    Page<FilterRule> findByConditions(
            @Param("deviceId") String deviceId,
            @Param("sensorCode") String sensorCode,
            @Param("enabled") Boolean enabled,
            Pageable pageable);
}
