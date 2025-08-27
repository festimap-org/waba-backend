package com.halo.eventer.domain.stamp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.halo.eventer.domain.stamp.StampMissionPrize;

public interface StampMissionPrizeRepository extends JpaRepository<StampMissionPrize, Long> {
    List<StampMissionPrize> findAllByStampIdOrderByRequiredCountAsc(Long stampId);
}
