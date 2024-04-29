package com.halo.eventer.concert.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ConcertCreateDto {

    private String tag;

    private String name;

    private String summary;
    private String content;

    private double latitude; // 위도
    private double longitude; // 경도
    private String location;
    private Boolean isOperation;
    private String operationHours;

    private String thumbnail;

    private List<String> images;
}
