package com.halo.eventer.domain.program_reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.halo.eventer.domain.program_reservation.Program;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    List<Program> findAllByFestivalId(Long festivalId);

    List<Program> findAllByFestivalIdAndNameContaining(Long festivalId, String name);
}
