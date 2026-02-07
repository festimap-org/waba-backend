package com.halo.eventer.domain.program_reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AvailableProgramListResponse {
    private List<AvailableProgramResponse> responses;

    public static AvailableProgramListResponse of(List<AvailableProgramResponse> responses) {
        return new AvailableProgramListResponse(responses);
    }
}
