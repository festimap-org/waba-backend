package com.halo.eventer.domain.program_reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProgramListResponse {
    private final List<ProgramResponse> responses;

    public static ProgramListResponse of(List<ProgramResponse> responses) {
        return new ProgramListResponse(responses);
    }
}
