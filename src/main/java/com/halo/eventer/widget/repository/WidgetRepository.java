package com.halo.eventer.widget.repository;


import com.halo.eventer.festival.Festival;
import com.halo.eventer.widget.Widget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WidgetRepository extends JpaRepository<Widget, Long> {
    List<Widget> findAllByFestival(Festival festival);
}
