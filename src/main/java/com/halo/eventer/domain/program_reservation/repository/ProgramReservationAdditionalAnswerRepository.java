package com.halo.eventer.domain.program_reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.halo.eventer.domain.program_reservation.entity.additional.ProgramReservationAdditionalAnswer;

public interface ProgramReservationAdditionalAnswerRepository
        extends JpaRepository<ProgramReservationAdditionalAnswer, Long> {

    boolean existsByFieldId(Long fieldId);

    boolean existsByOptionId(Long optionId);
}
