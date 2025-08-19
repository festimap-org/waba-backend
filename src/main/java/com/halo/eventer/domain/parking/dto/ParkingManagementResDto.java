package com.halo.eventer.domain.parking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingManagementResDto {
    private String headerName;
    private String parkingInfoType;
    private String title;
    private String description;
    private String buttonName;
    private String buttonTargetUrl;
    private String backgroundImage;
    private boolean visible;

    private ParkingManagementResDto(String headerName, String parkingInfoType, String title, String description, String buttonName, String buttonTargetUrl, String backgroundImage, boolean visible) {
        this.headerName = headerName;
        this.parkingInfoType = parkingInfoType;
        this.title = title;
        this.description = description;
        this.buttonName = buttonName;
        this.buttonTargetUrl = buttonTargetUrl;
        this.backgroundImage = backgroundImage;
        this.visible = visible;
    }

    public static ParkingManagementResDto of(String headerName, String parkingInfoType, String title, String description, String buttonName, String buttonTargetUrl, String backgroundImage, boolean visible) {
        return new ParkingManagementResDto(headerName, parkingInfoType, title, description, buttonName, buttonTargetUrl, backgroundImage, visible);
    }
}
