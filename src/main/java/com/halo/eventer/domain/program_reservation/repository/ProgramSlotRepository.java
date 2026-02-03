package com.halo.eventer.domain.program_reservation.repository;

import java.util.List;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.halo.eventer.domain.program_reservation.ProgramSlot;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProgramSlotRepository extends JpaRepository<ProgramSlot, Long> {
    void deleteAllByTemplateId(Long templateId);

    // FOR UPDATE
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        select s
        from ProgramSlot s
        where s.pattern.id = :patternId
        order by s.id
    """)
    List<ProgramSlot> findAllByPatternIdForUpdate(@Param("patternId") Long patternId);

    List<ProgramSlot> findAllByProgramIdOrderBySlotDateAscStartTimeAsc(Long programId);

}
