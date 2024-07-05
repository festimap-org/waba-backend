package com.halo.eventer.domain.concert.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ConcertCreateDto {

    private Long durationId;

    private String thumbnail;

    private List<String> images;
}
