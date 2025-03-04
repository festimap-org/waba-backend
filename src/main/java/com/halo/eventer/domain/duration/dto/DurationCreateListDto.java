package com.halo.eventer.domain.duration.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DurationCreateListDto {
    private List<DurationCreateDto> durationCreateDtos;

    public DurationCreateListDto(List<DurationCreateDto> durationCreateDtos) {
        this.durationCreateDtos = durationCreateDtos;
    }
}
