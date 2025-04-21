package com.halo.eventer.domain.manager.repository;

import com.halo.eventer.domain.manager.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    @Query("select m from Manager m where m.festival.id = :festivalId ")
    public List<Manager> searchManagerByFestivalId(@Param("festivalId") Long festivalId);
}
