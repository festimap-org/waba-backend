package com.halo.eventer.domain.widget.repository;


import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.widget.Widget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WidgetRepository extends JpaRepository<Widget, Long> {
    List<Widget> findAllByFestival(Festival festival);
}
