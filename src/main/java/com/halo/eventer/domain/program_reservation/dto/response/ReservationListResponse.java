package com.halo.eventer.domain.program_reservation.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReservationListResponse {
    private List<ReservationResponse> responses;

    public static ReservationListResponse of(List<ReservationResponse> responses) {
        ReservationListResponse res = new ReservationListResponse();
        res.responses = responses;
        return res;
    }
}