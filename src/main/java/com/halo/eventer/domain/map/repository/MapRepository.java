package com.halo.eventer.domain.map.repository;

import com.halo.eventer.domain.map.Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapRepository extends JpaRepository<Map, Long> {

    List<Map> findAllByMapCategoryId(Long id);
}
