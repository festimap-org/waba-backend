package com.halo.eventer.domain.parking;

import com.halo.eventer.domain.parking.dto.parking_place.ParkingPlaceReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Getter
public class ParkingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "congestion_level",nullable = false)
    private CongestionLevel congestionLevel;

    @Column(name = "parking_place_enabled", nullable = false)
    private boolean parkingPlaceEnabled = true;

    @Column(name = "area", nullable = false, length = 100)
    private String area;

    private double latitude;
    private double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id")
    private Parking parking;

    public ParkingPlace(ParkingPlaceReqDto parkingPlaceReqDto, Parking parking,CongestionLevel congestionLevel) {
        this.name = parkingPlaceReqDto.getName();
        this.congestionLevel = congestionLevel;
        this.area = parkingPlaceReqDto.getArea();
        this.latitude = parkingPlaceReqDto.getLatitude();
        this.longitude = parkingPlaceReqDto.getLongitude();
        this.parking = parking;
    }

    public void updateCongestionLevel(CongestionLevel congestionLevel){
        this.congestionLevel = congestionLevel;
    }

    public void updateParkingPlaceEnabled(boolean parkingPlaceEnabled){
        this.parkingPlaceEnabled = parkingPlaceEnabled;
    }

    public void updateParkingPlaceLocation(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
