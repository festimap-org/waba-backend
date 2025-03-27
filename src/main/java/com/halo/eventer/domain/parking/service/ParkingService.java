package com.halo.eventer.domain.parking.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.parking.CongestionLevel;
import com.halo.eventer.domain.parking.Parking;
import com.halo.eventer.domain.parking.ParkingPlace;
import com.halo.eventer.domain.parking.dto.parking.ParkingAnnouncementReqDto;
import com.halo.eventer.domain.parking.dto.parking.ParkingResDto;
import com.halo.eventer.domain.parking.dto.parking_place.ParkingPlaceReqDto;
import com.halo.eventer.domain.parking.dto.parking_place.ParkingPlaceResDto;
import com.halo.eventer.domain.parking.repository.ParkingPlaceRepository;
import com.halo.eventer.domain.parking.repository.ParkingRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;
    private final FestivalRepository festivalRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;

    @Transactional
    public ParkingResDto createParkingSystem(Long festivalId){
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(()-> new BaseException("축제가 존재하지 않습니다", ErrorCode.ELEMENT_NOT_FOUND));

        Parking parking = new Parking(festival);
        parkingRepository.save(parking);
        return new ParkingResDto(parking);
    }

    @Transactional(readOnly = true)
    public ParkingResDto getParkingInfo(Long festivalId){
        Parking parking = parkingRepository.findByFestivalId(festivalId)
                .orElseThrow(() -> new BaseException("주차장 관리 시스템이 생성되지 않았습니다.",ErrorCode.ELEMENT_NOT_FOUND));

        return new ParkingResDto(parking);
    }

    @Transactional
    public ParkingResDto updateAnnouncement(ParkingAnnouncementReqDto parkingAnnouncementReqDto, Long parkingId){
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new BaseException("주차장 관리 시스템이 생성되지 않았습니다.",ErrorCode.ELEMENT_NOT_FOUND));

        parking.updateAnnouncement(parkingAnnouncementReqDto);
        return new ParkingResDto(parking);
    }

    @Transactional
    public ParkingResDto updateParkingInfo(Long parkingId, boolean isCongestionEnabled){
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new BaseException("주차장 관리 시스템이 생성되지 않았습니다.",ErrorCode.ELEMENT_NOT_FOUND));

        parking.updateIsCongestionEnabled(isCongestionEnabled);
        return new ParkingResDto(parking);
    }

    @Transactional
    public ParkingResDto createParkingPlace(ParkingPlaceReqDto parkingPlaceReqDto, Long parkingId, CongestionLevel congestionLevel){
        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new BaseException("주차장 관리 시스템이 생성되지 않았습니다.",ErrorCode.ELEMENT_NOT_FOUND));
        ;
        ParkingPlace parkingPlace = new ParkingPlace(parkingPlaceReqDto,parking, congestionLevel);
        parkingPlaceRepository.save(parkingPlace);
        return new ParkingResDto(parking);
    }

    @Transactional
    public ParkingPlaceResDto updateParkingPlaceCongestion(Long parkingPlaceId,CongestionLevel congestionLevel){
        ParkingPlace parkingPlace = parkingPlaceRepository.findById(parkingPlaceId)
                .orElseThrow(() -> new BaseException("해당 주차장이 존재하지 않습니다.",ErrorCode.ELEMENT_NOT_FOUND));
        parkingPlace.updateCongestionLevel(congestionLevel);
        return new ParkingPlaceResDto(parkingPlace);
    }

    @Transactional
    public ParkingPlaceResDto updateParkingPlaceState(Long parkingPlaceId, boolean parkingPlaceEnabled){
        ParkingPlace parkingPlace = parkingPlaceRepository.findById(parkingPlaceId)
                .orElseThrow(() -> new BaseException("해당 주차장이 존재하지 않습니다.",ErrorCode.ELEMENT_NOT_FOUND));
        parkingPlace.updateParkingPlaceEnabled(parkingPlaceEnabled);
        return new ParkingPlaceResDto(parkingPlace);
    }

    @Transactional
    public ParkingPlaceResDto updateParkingPlaceLocation(Long parkingPlaceId, double latitude, double longitude){
        ParkingPlace parkingPlace = parkingPlaceRepository.findById(parkingPlaceId)
                .orElseThrow(() -> new BaseException("해당 주차장이 존재하지 않습니다.",ErrorCode.ELEMENT_NOT_FOUND));
        parkingPlace.updateParkingPlaceLocation(latitude,longitude);
        return new ParkingPlaceResDto(parkingPlace);
    }

    @Transactional
    public void deleteParkingPlace(Long parkingPlaceId){
        parkingPlaceRepository.deleteById(parkingPlaceId);
    }
}
