package com.halo.eventer.domain.program_reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.halo.eventer.domain.program_reservation.ProgramReservation;
import com.halo.eventer.domain.program_reservation.ProgramTag;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReservationResponse {
    private Long id;
    private String name;
    private List<ProgramDetailResponse.TagResponse> tags;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String durationTime;
    private int headCount;
    private int priceAmount;

    public static ReservationResponse from(ProgramReservation reservation, List<ProgramTag> programTags) {
        ReservationResponse response = new ReservationResponse();
        response.id = reservation.getId();
        response.name = reservation.getProgram().getName();
        response.tags = programTags.stream().map(ProgramDetailResponse.TagResponse::from).collect(Collectors.toList());
        response.date = reservation.getSlot().getSlotDate();
        response.durationTime = reservation.getProgram().getDurationTime();
        response.headCount = reservation.getPeopleCount();
        response.priceAmount = reservation.getProgram().getPriceAmount() * reservation.getPeopleCount();
        return response;
    }
}