package com.halo.eventer.domain.duration.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DurationCreateListDto {
    private List<DurationCreateDto> durationCreateDtos;

    public DurationCreateListDto(List<DurationCreateDto> durationCreateDtos) {
        this.durationCreateDtos = durationCreateDtos;
    }
}
