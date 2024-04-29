package com.halo.eventer.map.repository;

import com.halo.eventer.festival.Festival;
import com.halo.eventer.map.Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapRepository extends JpaRepository<Map, Long> {

    List<Map> findAllByMapCategoryId(Long id);
}
