package com.halo.eventer.domain.parking.repository;

import com.halo.eventer.domain.parking.ParkingNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingNoticeRepository extends JpaRepository<ParkingNotice, Long> {}
