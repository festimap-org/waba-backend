package com.halo.eventer.domain.parking.repository;

import com.halo.eventer.domain.parking.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking,Long> {

    @Query("SELECT p FROM Parking p where p.festival.id = :festivalId")
    Optional<Parking> findByFestivalId(@Param("festivalId") Long festivalId);
}
