package com.halo.eventer.domain.parking.controller;

import com.halo.eventer.domain.parking.CongestionLevel;
import com.halo.eventer.domain.parking.dto.parking.ParkingAnnouncementReqDto;
import com.halo.eventer.domain.parking.dto.parking.ParkingResDto;
import com.halo.eventer.domain.parking.dto.parking_place.ParkingPlaceReqDto;
import com.halo.eventer.domain.parking.dto.parking_place.ParkingPlaceResDto;
import com.halo.eventer.domain.parking.service.ParkingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "주차장 관리")
@RequestMapping("/parking")
public class ParkingController {
    private final ParkingService parkingService;

    @PostMapping
    public ParkingResDto createParkingSystem(@RequestParam("festivalId") Long festivalId){
        return parkingService.createParkingSystem(festivalId);
    }

    @GetMapping()
    public ParkingResDto getParkingInfo(@RequestParam("festivalId") Long festivalId){
        return parkingService.getParkingInfo(festivalId);
    }

    @PostMapping("/announcement")
    public ParkingResDto updateAnnouncement(@RequestBody ParkingAnnouncementReqDto announcementReqDto,
                                            @RequestParam("parkingId") Long parkingId){
        return parkingService.updateAnnouncement(announcementReqDto, parkingId);
    }

    @PatchMapping
    public ParkingResDto updateParkingInfo(@RequestParam("parkingId") Long parkingId,
                                           @RequestParam("isCongestionEnabled") boolean isCongestionEnabled){
        return parkingService.updateParkingInfo(parkingId, isCongestionEnabled);
    }

    @PostMapping("/place")
    public ParkingResDto createParkingPlace(@RequestBody ParkingPlaceReqDto parkingPlaceReqDto,
                                            @RequestParam("parkingId") Long parkingId,
                                            @RequestParam CongestionLevel congestionLevel){
        return parkingService.createParkingPlace(parkingPlaceReqDto,parkingId,congestionLevel);
    }

    @PatchMapping("/place/congestion")
    public ParkingPlaceResDto updateParkingPlaceCongestion(@RequestParam("parkingPlaceId") Long parkingPlaceId,
                                                           @RequestParam CongestionLevel congestionLevel){
        return parkingService.updateParkingPlaceCongestion(parkingPlaceId,congestionLevel);
    }

    @PatchMapping("/place/state")
    public ParkingPlaceResDto updateParkingPlaceState(@RequestParam("parkingPlaceId") Long parkingPlaceId,
                                                      @RequestParam boolean parkingPlaceEnabled){
        return parkingService.updateParkingPlaceState(parkingPlaceId, parkingPlaceEnabled);
    }

    @PatchMapping("/place/location")
    public ParkingPlaceResDto updateParkingPlaceLocation(@RequestParam("parkingPlaceId") Long parkingPlaceId,
                                                      @RequestParam double latitude,
                                                      @RequestParam double longitude){
        return parkingService.updateParkingPlaceLocation(parkingPlaceId, latitude, longitude);
    }

    @DeleteMapping("/place")
    public void delete(@RequestParam("parkingPlaceId") Long parkingPlaceId){
        parkingService.deleteParkingPlace(parkingPlaceId);
    }
}
