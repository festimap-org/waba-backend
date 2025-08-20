package com.halo.eventer.domain.parking.dto;

import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingMapImageReqDto {
    private @NotNull List<@Min(1) Long> imageIds;
}
