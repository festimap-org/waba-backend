package com.halo.eventer.domain.festival.dto;

import com.halo.eventer.domain.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FestivalSummaryDto {

    private Long id;
    private String festivalName;
    private String subDomain;
    private double latitude;
    private double longitude;

    public FestivalSummaryDto(Festival festival) {
        this.id = festival.getId();
        this.festivalName = festival.getName();
        this.subDomain = festival.getSubDomain(); // 내부 → 외부 매핑
        this.latitude = festival.getLatitude();
        this.longitude = festival.getLongitude();
    }

    public FestivalSummaryDto(Long id, String festivalName, String subDomain, double latitude, double longitude) {
        this.id = id;
        this.festivalName = festivalName;
        this.subDomain = subDomain; // 여기서 변환
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
