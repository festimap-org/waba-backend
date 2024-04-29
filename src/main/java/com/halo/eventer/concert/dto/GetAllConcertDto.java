package com.halo.eventer.concert.dto;

import com.halo.eventer.concert.Concert;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class GetAllConcertDto {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;

    public GetAllConcertDto(Concert c) {
        this.id = c.getId();
        this.name = c.getName();
        this.latitude = c.getLatitude();
        this.longitude = c.getLongitude();
    }
}
