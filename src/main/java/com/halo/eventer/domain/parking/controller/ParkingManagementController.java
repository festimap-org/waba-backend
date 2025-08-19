package com.halo.eventer.domain.parking.controller;

import com.halo.eventer.domain.image.dto.FileDto;
import com.halo.eventer.domain.parking.dto.*;
import com.halo.eventer.domain.parking.service.ParkingManagementService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class ParkingManagementController {

    private final ParkingManagementService parkingManagementService;

    @PostMapping("/admin/festivals/{festivalId}/parking-managements")
    public void create(@Min(1) @PathVariable("festivalId") Long festivalId,
                       @Valid @RequestBody ParkingManagementReqDto parkingManagementReqDto){
        parkingManagementService.create(festivalId, parkingManagementReqDto);
    }

    @GetMapping("/common/parking-managements/{id}")
    public ParkingManagementResDto getParkingManagement(@PathVariable("id") Long id){
        return parkingManagementService.getParkingManagement(id);
    }

    @PutMapping("/admin/parking-managements/{id}")
    public void update(@Min(1) @PathVariable("id") Long id,
                       @Valid @RequestBody ParkingManagementReqDto parkingManagementReqDto){
        parkingManagementService.update(id,parkingManagementReqDto);
    }

    @GetMapping("/admin/parking-managements/{id}/sub-page-info")
    public ParkingManagementSubPageResDto getParkingManagementByFestivalIdAndParkingInfoType(@PathVariable("id") Long id){
        return parkingManagementService.getParkingManagementSubPage(id);
    }

    @PatchMapping("/admin/parking-managements/{id}/sub-page-header-name")
    public void updateSubPageHeaderName(@Min(1) @PathVariable("id") Long id,
                                        @Valid @RequestBody ParkingSubPageReqDto parkingSubPageReqDto){
        parkingManagementService.updateSubPageHeaderName(id, parkingSubPageReqDto);
    }

    @PostMapping("/admin/parking-managements/{id}/parking-map-images")
    public void createParkingMapImage(@Min(1) @PathVariable("id") Long id, @RequestBody FileDto fileDto){
        parkingManagementService.createParkingMapImage(id, fileDto);
    }

    @PatchMapping("/admin/parking-managements/{id}/parking-map-images/display-order")
    public void updateParkingMapImageDisplayOrder(@Min(1) @PathVariable("id") Long id,
                                                  @Valid @RequestBody ParkingMapImageReqDto parkingMapImageReqDto){
        parkingManagementService.updateParkingMapImageDisplayOrder(id,parkingMapImageReqDto);
    }

    @DeleteMapping("/admin/parking-managements/{id}/parking-map-images")
    public void deleteParkingMapImages(@Min(1) @PathVariable("id") Long id, @Valid @RequestBody ParkingMapImageReqDto parkingMapImageReqDto){
        parkingManagementService.deleteParkingMapImages(id, parkingMapImageReqDto);
    }
}