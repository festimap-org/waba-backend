package com.halo.eventer.domain.splash.repository;

import com.halo.eventer.domain.splash.Splash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SplashRepository extends JpaRepository<Splash, Long> {
    Optional<Splash> findByFestivalId(long festivalId);
}
