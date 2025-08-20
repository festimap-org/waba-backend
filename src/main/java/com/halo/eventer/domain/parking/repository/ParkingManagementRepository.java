package com.halo.eventer.domain.parking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.halo.eventer.domain.parking.ParkingManagement;

public interface ParkingManagementRepository extends JpaRepository<ParkingManagement, Long> {

    @Query("SELECT pm FROM ParkingManagement pm JOIN FETCH pm.images i WHERE pm.id = :id ")
    Optional<ParkingManagement> findByIdWithImages(@Param("id") Long id);
}
