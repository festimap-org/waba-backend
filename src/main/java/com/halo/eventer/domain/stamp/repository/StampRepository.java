package com.halo.eventer.domain.stamp.repository;

import com.halo.eventer.domain.stamp.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StampRepository extends JpaRepository<Stamp, Long> {
    Optional<Stamp> findByUserInfo(String userInfo);
}
