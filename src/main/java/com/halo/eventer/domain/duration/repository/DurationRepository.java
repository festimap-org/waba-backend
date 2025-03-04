package com.halo.eventer.domain.duration.repository;

import com.halo.eventer.domain.duration.Duration;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface DurationRepository extends CrudRepository<Duration, Long> {

    List<Duration> findAllByFestivalId(Long id);

    List<Duration> findByIdIn(List<Long> ids);

    List<Duration> findAllByIdIn(List<Long> ids);

}
