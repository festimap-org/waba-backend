package com.halo.eventer.domain.parking.dto.parking;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingAnnouncementReqDto {
    private String announcement;
    private boolean announcementEnabled;
}
