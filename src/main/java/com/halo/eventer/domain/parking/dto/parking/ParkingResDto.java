package com.halo.eventer.domain.parking.dto.parking;


import com.halo.eventer.domain.parking.Parking;
import com.halo.eventer.domain.parking.dto.parking_place.ParkingPlaceResDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ParkingResDto {
    private Long parkingId;
    private String announcement;
    private boolean isCongestionEnabled;
    private boolean isAnnouncementEnabled;
    private List<ParkingPlaceResDto> parkingPlaces;

    public ParkingResDto(Parking parking) {
        this.parkingId = parking.getId();
        this.announcement = parking.getAnnouncement();
        this.isCongestionEnabled = parking.isCongestionEnabled();
        this.isAnnouncementEnabled = parking.isAnnouncementEnabled();
        this.parkingPlaces = parking.getParkingPlaces().stream()
                .map(ParkingPlaceResDto::new)
                .collect(Collectors.toList());
    }
}
