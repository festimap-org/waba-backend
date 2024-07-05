package com.halo.eventer.domain.duration_map;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DurationMapRepository extends JpaRepository<DurationMap, Long> {
    void deleteByIdIn(List<Long> ids);
    List<DurationMap> findAllByDuration_IdIn(List<Long> ids);
}
