package com.halo.eventer.duration_map;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DurationMapRepository extends JpaRepository<DurationMap, Long> {
    void deleteByIdIn(List<Long> ids);
}
