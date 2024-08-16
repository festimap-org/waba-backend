package com.halo.eventer.domain.stamp.repository;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.stamp.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StampRepository extends JpaRepository<Stamp, Long> {
    Optional<Stamp> findByFestivalIdAndUserInfo(Long festivalId, String userInfo);

    boolean existsByFestivalIdAndUserInfo(Long festivalId, String userInfo);

    Optional<Stamp> findByUuid(String uuid);

    List<Stamp> findByFestival(Festival festival);

    void deleteByFestival(Festival festival);
}
