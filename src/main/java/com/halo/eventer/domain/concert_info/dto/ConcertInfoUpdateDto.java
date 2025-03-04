package com.halo.eventer.domain.concert_info.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ConcertInfoUpdateDto {
    private String summary;
    private List<String> images;
    private List<Long> deletedImages;
}
