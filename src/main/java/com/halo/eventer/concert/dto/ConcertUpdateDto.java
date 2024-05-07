package com.halo.eventer.concert.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class ConcertUpdateDto {
    private Long durationId;

    private String thumbnail;

    private List<String> images;

    private List<Long> deletedImages;
}
