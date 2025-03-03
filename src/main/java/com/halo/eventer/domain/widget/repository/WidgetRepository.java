package com.halo.eventer.domain.widget.repository;


import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.widget.Widget;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WidgetRepository extends JpaRepository<Widget, Long> {
    List<Widget> findAllByFestival(Festival festival);
}
