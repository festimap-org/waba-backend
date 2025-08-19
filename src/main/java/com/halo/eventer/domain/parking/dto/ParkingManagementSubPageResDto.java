package com.halo.eventer.domain.parking.dto;

import com.halo.eventer.domain.image.Image;
import com.halo.eventer.domain.image.dto.ImageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingManagementSubPageResDto {
    private String subPageHeaderName;
    private List<ImageDto> images;

    public ParkingManagementSubPageResDto(String subPageHeaderName, List<Image> images) {
        this.subPageHeaderName = subPageHeaderName;
        this.images = images.stream().map(ImageDto::from).toList();
    }
}
