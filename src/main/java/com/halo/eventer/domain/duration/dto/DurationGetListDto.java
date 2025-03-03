package com.halo.eventer.domain.duration.dto;


import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DurationGetListDto {
    private List<DurationGetDto> durations;

    public DurationGetListDto(List<DurationGetDto> durations) {
        this.durations = durations;
    }
}
