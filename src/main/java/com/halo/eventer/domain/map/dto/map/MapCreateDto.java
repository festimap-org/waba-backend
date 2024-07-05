package com.halo.eventer.domain.map.dto.map;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MapCreateDto {

    private String name;

    private List<Long> durationIds;
    private List<Long> deleteIds;

    private String summary;
    private String content;


    private String operationHours;

    private String location;
    private double latitude; // 위도
    private double longitude; // 경도

    private String thumbnail;
    private String icon;

}
