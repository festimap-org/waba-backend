package com.halo.eventer.concert.repository;

import com.halo.eventer.concert.Concert;
import com.halo.eventer.festival.Festival;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findAllByFestival(Festival festival);

}
