package com.halo.eventer.domain.stamp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.halo.eventer.domain.stamp.ParticipateGuide;

public interface ParticipateGuideRepository extends JpaRepository<ParticipateGuide, Long> {
    Optional<ParticipateGuide> findFirstByStampId(Long stampId);
}
