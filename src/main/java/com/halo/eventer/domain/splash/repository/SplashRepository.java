package com.halo.eventer.domain.splash.repository;

import com.halo.eventer.domain.splash.Splash;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SplashRepository extends JpaRepository<Splash, Long> {
    Optional<Splash> findByFestivalId(long festivalId);
}
