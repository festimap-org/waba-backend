package com.halo.eventer.domain.duration.repository;

import java.util.List;

import com.halo.eventer.domain.duration.DurationMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DurationMapRepository extends JpaRepository<DurationMap, Long> {
  void deleteByIdIn(List<Long> ids);

  List<DurationMap> findAllByDuration_IdIn(List<Long> ids);
}
