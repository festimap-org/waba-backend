package com.halo.eventer.concert_info.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ConcertInfoUpdateDto {
    private List<String> images;
    private List<Long> deletedImages;
}
