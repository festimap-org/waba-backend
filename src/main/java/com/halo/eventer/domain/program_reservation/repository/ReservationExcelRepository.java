package com.halo.eventer.domain.program_reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.halo.eventer.domain.program_reservation.entity.reservation.ProgramReservation;
import com.halo.eventer.domain.program_reservation.entity.reservation.ProgramReservationStatus;

public interface ReservationExcelRepository extends Repository<ProgramReservation, Long> {

    @Query(
            """
            SELECT
                r.bookerName       AS bookerName,
                r.bookerPhone      AS bookerPhone,
                r.visitorName      AS visitorName,
                r.visitorPhone     AS visitorPhone,
                p.name             AS programName,
                s.slotDate         AS reservationDate,
                s.startTime        AS startTime,
                p.durationTime     AS durationTime,
                r.peopleCount      AS peopleCount,
                p.priceAmount      AS price,
                r.status           AS status
            FROM ProgramReservation r
            JOIN r.program p
            JOIN r.slot s
            WHERE s.slotDate BETWEEN :from AND :to
            ORDER BY s.slotDate, s.startTime, r.createdAt
            """)
    Stream<ReservationExcelRowView> streamBySlotDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query(
            """
            SELECT f.name
            FROM ProgramReservation r
            JOIN r.program p
            JOIN p.festival f
            JOIN r.slot s
            WHERE s.slotDate BETWEEN :from AND :to
            """)
    List<String> findFestivalNameBySlotDateBetween(
            @Param("from") LocalDate from, @Param("to") LocalDate to, Pageable pageable);

    interface ReservationExcelRowView {
        String getBookerName();

        String getBookerPhone();

        String getVisitorName();

        String getVisitorPhone();

        String getProgramName();

        LocalDate getReservationDate();

        LocalTime getStartTime();

        String getDurationTime();

        Integer getPeopleCount();

        Integer getPrice();

        ProgramReservationStatus getStatus();
    }
}
