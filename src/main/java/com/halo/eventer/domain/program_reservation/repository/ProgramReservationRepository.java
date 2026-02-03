package com.halo.eventer.domain.program_reservation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.halo.eventer.domain.program_reservation.ProgramReservation;
import com.halo.eventer.domain.program_reservation.ProgramReservationStatus;
import io.micrometer.common.lang.Nullable;

public interface ProgramReservationRepository
        extends JpaRepository<ProgramReservation, Long>, JpaSpecificationExecutor<ProgramReservation> {
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

    @Override
    @EntityGraph(attributePaths = {"program", "slot"})
    Page<ProgramReservation> findAll(@Nullable Specification<ProgramReservation> spec, Pageable pageable);
}
