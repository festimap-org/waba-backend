package com.halo.eventer.domain.parking.dto.parking_place;

import com.halo.eventer.domain.parking.CongestionLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
public class ParkingPlaceReqDto {
    private String name;
    private String area;
    private double latitude;
    private double longitude;
}
