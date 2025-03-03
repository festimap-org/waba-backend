package com.halo.eventer.domain.map.repository;

import com.halo.eventer.domain.map.Map;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map, Long> {

    List<Map> findAllByMapCategoryId(Long id);
}
