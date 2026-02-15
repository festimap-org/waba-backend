package com.halo.eventer.domain.program_reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.halo.eventer.domain.program_reservation.entity.slot.ProgramTimePattern;

public interface ProgramTimePatternRepository extends JpaRepository<ProgramTimePattern, Long> {

    List<ProgramTimePattern> findAllByTemplateIdOrderBySortOrder(Long templateId);

    void deleteAllByTemplateId(Long templateId);
}
