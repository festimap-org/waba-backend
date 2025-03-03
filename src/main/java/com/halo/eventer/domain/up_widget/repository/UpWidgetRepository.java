package com.halo.eventer.domain.up_widget.repository;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.up_widget.UpWidget;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UpWidgetRepository extends JpaRepository<UpWidget, Long> {
  List<UpWidget> findAllByFestival(Festival festival);

  @Query(
      "SELECT e FROM UpWidget e WHERE e.festival = :festival and :a BETWEEN e.startDateTime AND e.endDateTime")
  List<UpWidget> findAllByFestivalWithDateTime(Festival festival, LocalDateTime a);
}
