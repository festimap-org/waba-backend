package com.halo.eventer.domain.down_widget.repository;

import com.halo.eventer.domain.down_widget.DownWidget1;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DownWidgetRepository extends JpaRepository<DownWidget1, Long> {

  @Query("select e from DownWidget1 e WHERE e.festival.id = :id")
  List<DownWidget1> findAllByFestivalId(@Param("id") Long id);
}
