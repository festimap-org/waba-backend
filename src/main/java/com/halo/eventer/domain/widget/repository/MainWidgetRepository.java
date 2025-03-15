package com.halo.eventer.domain.widget.repository;

import com.halo.eventer.domain.widget.entity.MainWidget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MainWidgetRepository extends JpaRepository<MainWidget, Long> {
  @Query("select e from DownWidget e WHERE e.festival.id = :festivalId")
  List<MainWidget> findAllByFestivalId(@Param("festivalId") Long festivalId);
}
