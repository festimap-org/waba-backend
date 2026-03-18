package com.halo.eventer.domain.festival.dto;

import com.halo.eventer.domain.festival.Festival;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalResDto {

    private Long id;
    private String name;
    private String subDomain;
    private String logo;
    private ColorDto colors;
    private double latitude;
    private double longitude;

    public FestivalResDto(
            Long id, String name, String logo, ColorDto colors, double latitude, double longitude, String subDomain) {

        this.id = id;
        this.name = name;
        this.logo = logo;
        this.colors = colors;
        this.latitude = latitude;
        this.longitude = longitude;
        this.subDomain = subDomain;
    }

    public static FestivalResDto from(Festival festival) {
        return new FestivalResDto(
                festival.getId(),
                festival.getName(),
                festival.getLogo(),
                ColorDto.from(festival),
                festival.getLatitude(),
                festival.getLongitude(),
                festival.getSubAddress());
    }
}
