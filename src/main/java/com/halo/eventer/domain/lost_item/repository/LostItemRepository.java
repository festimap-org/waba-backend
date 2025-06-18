package com.halo.eventer.domain.lost_item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.halo.eventer.domain.lost_item.LostItem;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {
    List<LostItem> findAllByFestivalId(@Param("festivalId") Long festivalId);
}
