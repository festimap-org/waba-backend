package com.halo.eventer.domain.middle_banner.repository;

import com.halo.eventer.domain.middle_banner.MiddleBanner;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MiddleBannerRepository extends JpaRepository<MiddleBanner, Long> {

  @Query("SELECT m From MiddleBanner m WHERE m.festival.id = :festivalId ")
  List<MiddleBanner> findAllByFestivalId(@Param("festivalId") Long festivalId);
}
