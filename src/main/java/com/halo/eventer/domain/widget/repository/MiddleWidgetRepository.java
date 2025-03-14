package com.halo.eventer.domain.widget.repository;

import com.halo.eventer.domain.widget.entity.MiddleWidget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MiddleWidgetRepository extends JpaRepository<MiddleWidget, Long> {

  @Query("SELECT m From MiddleWidget m WHERE m.festival.id = :festivalId ")
  List<MiddleWidget> findAllByFestivalId(@Param("festivalId") Long festivalId);
}
