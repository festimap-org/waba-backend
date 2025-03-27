package com.halo.eventer.domain.parking.dto.parking_place;

import com.halo.eventer.domain.parking.ParkingPlace;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingPlaceResDto {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    private String congestionLevel;
    private boolean parkingPlaceEnabled;
    private String area;

    public ParkingPlaceResDto(ParkingPlace parkingPlace) {
        this.id = parkingPlace.getId();
        this.name = parkingPlace.getName();
        this.latitude = parkingPlace.getLatitude();
        this.longitude = parkingPlace.getLongitude();
        this.congestionLevel = parkingPlace.getCongestionLevel().getDescription();
        this.parkingPlaceEnabled = parkingPlace.isParkingPlaceEnabled();
        this.area = parkingPlace.getArea();
    }
}
