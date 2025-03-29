package com.halo.eventer.domain.map.dto.map;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationInfoDto {
    private String address;
    private double latitude;
    private double longitude;
}
