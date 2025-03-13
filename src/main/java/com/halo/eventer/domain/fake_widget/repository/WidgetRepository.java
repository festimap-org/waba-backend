package com.halo.eventer.domain.fake_widget.repository;


import com.halo.eventer.domain.fake_widget.Widget;
import com.halo.eventer.domain.festival.Festival;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WidgetRepository extends JpaRepository<Widget, Long> {
    List<Widget> findAllByFestival(Festival festival);
}
