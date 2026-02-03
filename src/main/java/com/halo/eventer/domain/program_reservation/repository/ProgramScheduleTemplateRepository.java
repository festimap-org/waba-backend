package com.halo.eventer.domain.program_reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.halo.eventer.domain.program_reservation.ProgramScheduleTemplate;

public interface ProgramScheduleTemplateRepository extends JpaRepository<ProgramScheduleTemplate, Long> {

    List<ProgramScheduleTemplate> findAllByProgramIdOrderByStartDate(Long programId);
}
