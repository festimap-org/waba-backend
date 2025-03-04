package com.halo.eventer.domain.map.repository;

import com.halo.eventer.domain.map.MapCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MapCategoryRepository extends JpaRepository<MapCategory, Long> {


    Optional<MapCategory> findByCategoryName(String categoryName);
    List<MapCategory> findAllByFestival_Id(Long festivalId);
}
