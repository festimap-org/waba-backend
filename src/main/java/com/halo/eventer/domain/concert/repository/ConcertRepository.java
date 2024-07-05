package com.halo.eventer.domain.concert.repository;

import com.halo.eventer.domain.concert.Concert;
import com.halo.eventer.domain.festival.Festival;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findAllByFestival(Festival festival);

}
