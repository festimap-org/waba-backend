package com.halo.eventer.domain.program_reservation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationConfirmRequest {
    private String bookerName;
    private String bookerPhone;
    private String visitorName;
    private String visitorPhone;
}
