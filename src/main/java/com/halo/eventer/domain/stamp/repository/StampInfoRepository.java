package com.halo.eventer.domain.stamp.repository;

import com.halo.eventer.domain.stamp.StampInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StampInfoRepository extends JpaRepository<StampInfo, Long> {
    Optional<StampInfo> findByStampUuid(String uuid);
}
