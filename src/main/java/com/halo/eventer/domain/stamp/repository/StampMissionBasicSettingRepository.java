package com.halo.eventer.domain.stamp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.halo.eventer.domain.stamp.StampMissionBasicSetting;

public interface StampMissionBasicSettingRepository extends JpaRepository<StampMissionBasicSetting, Long> {
    @Query("select s from StampMissionBasicSetting s where s.stamp.id = :stampId")
    Optional<StampMissionBasicSetting> findByStampId(@Param("stampId") Long stampId);
}
