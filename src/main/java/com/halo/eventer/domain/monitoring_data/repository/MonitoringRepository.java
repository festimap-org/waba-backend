package com.halo.eventer.domain.monitoring_data.repository;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.monitoring_data.MonitoringData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonitoringRepository extends JpaRepository<MonitoringData, Long> {
    Optional<MonitoringData> findByFestival(Festival festival);
}
