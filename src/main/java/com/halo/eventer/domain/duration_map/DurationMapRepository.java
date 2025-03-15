package com.halo.eventer.domain.duration_map;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DurationMapRepository extends JpaRepository<DurationMap, Long> {
  void deleteByIdIn(List<Long> ids);

  List<DurationMap> findAllByDuration_IdIn(List<Long> ids);
}
