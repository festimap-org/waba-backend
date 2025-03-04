package com.halo.eventer.domain.duration.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DurationGetListDto {
    private List<DurationGetDto> durations;

    public DurationGetListDto(List<DurationGetDto> durations) {
        this.durations = durations;
    }
}
