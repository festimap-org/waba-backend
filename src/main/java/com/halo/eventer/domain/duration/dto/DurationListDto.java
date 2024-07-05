package com.halo.eventer.domain.duration.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DurationListDto {
    List<DurationDto> durations;

    public DurationListDto(List<DurationDto> durations) {
        this.durations = durations;
    }
}
