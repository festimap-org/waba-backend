package com.halo.eventer.domain.program_reservation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.halo.eventer.domain.program_reservation.ProgramReservation;
import com.halo.eventer.domain.program_reservation.ProgramReservationStatus;
import com.halo.eventer.domain.program_reservation.ProgramSlotType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminReservationResponse {
    private Long id;
    private String programName;

    private String bookerName;
    private String bookerPhone;
    private String visitorName;
    private String visitorPhone;

    private LocalDate slotDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime slotStartTime;

    private LocalDate confirmedAt;

    private int durationMinutes;
    private int peopleCount;
    private int fee; // 일단 0원으로 통일
    private ProgramReservationStatus status;
    private boolean past;

    public static AdminReservationResponse from(ProgramReservation r) {
        String visitorName = r.getVisitorName();
        String visitorPhone = r.getVisitorPhone();
        if (visitorName == null || visitorPhone == null) {
            visitorName = r.getBookerName();
            visitorPhone = r.getBookerPhone();
        }

        boolean past;
        if (r.getSlot().getTemplate().getSlotType() == ProgramSlotType.DATE) {
            LocalDate today = LocalDate.now();
            past = r.getSlot().getSlotDate().isBefore(today);
        } else {
            LocalDateTime endDateTime =
                    LocalDateTime.of(r.getSlot().getSlotDate(), r.getSlot().getStartTime());
            past = !endDateTime.isAfter(LocalDateTime.now());
        }

        return new AdminReservationResponse(
                r.getId(),
                r.getProgram().getName(),
                r.getBookerName(),
                r.getBookerPhone(),
                visitorName,
                visitorPhone,
                r.getSlot().getSlotDate(),
                r.getSlot().getStartTime(),
                r.getConfirmedAt() != null ? r.getConfirmedAt().toLocalDate() : null,
                r.getSlot().getDurationMinutes(),
                r.getPeopleCount(),
                0,
                r.getStatus(),
                past);
    }
}
