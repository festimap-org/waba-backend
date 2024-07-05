package com.halo.eventer.domain.festival.repository;

import com.halo.eventer.domain.festival.Festival;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FestivalRepository extends JpaRepository<Festival,Long> {

    Optional<Festival> findByName(String name);
    Optional<Festival> findBySubAddress(String subAddress);
}
