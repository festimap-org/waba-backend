package com.halo.eventer.domain.monitoring_data.repository;

import com.halo.eventer.domain.monitoring_data.AlertThreshold;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertThresholdRepository extends JpaRepository<AlertThreshold, Long> {
}
