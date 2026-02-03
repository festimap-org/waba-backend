package com.halo.eventer.domain.program_reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.halo.eventer.domain.program_reservation.ProgramReservation;
import com.halo.eventer.domain.program_reservation.ProgramReservationStatus;

public interface ProgramReservationRepository extends JpaRepository<ProgramReservation, Long> {
    @Query("SELECT COUNT(r) FROM ProgramReservation r WHERE r.slot.template.id = :templateId AND r.status IN :statuses")
    long countByTemplateIdAndStatusIn(
            @Param("templateId") Long templateId, @Param("statuses") List<ProgramReservationStatus> statuses);

    @Query(
            """
        select (count(r) > 0)
        from ProgramReservation r
        join r.slot s
        where s.template.id = :templateId
    """)
    boolean existsAnyByTemplateId(@Param("templateId") Long templateId);
}
