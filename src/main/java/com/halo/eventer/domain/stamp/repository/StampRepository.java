package com.halo.eventer.domain.stamp.repository;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.stamp.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StampRepository extends JpaRepository<Stamp, Long> {
    List<Stamp> findByFestival(Festival festival);
}
