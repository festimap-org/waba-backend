package com.halo.eventer.domain.program_reservation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.halo.eventer.domain.program_reservation.entity.additional.ProgramAdditionalFieldOption;

public interface ProgramAdditionalFieldOptionRepository extends JpaRepository<ProgramAdditionalFieldOption, Long> {

    List<ProgramAdditionalFieldOption> findAllByFieldId(Long fieldId);

    List<ProgramAdditionalFieldOption> findAllByFieldIdInOrderBySortOrder(List<Long> fieldIds);

    void deleteAllByFieldId(Long fieldId);

    @Query("select min(o.sortOrder) from ProgramAdditionalFieldOption o where o.field.id = :fieldId")
    Optional<Integer> findMinSortOrderByFieldId(@Param("fieldId") Long fieldId);

}
