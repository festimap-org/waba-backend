package com.halo.eventer.domain.stamp.dto.stamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StampGetListDto {
    private List<StampGetDto> stampGetDtos;

    public StampGetListDto(List<StampGetDto> stampGetDtos) {
        this.stampGetDtos = stampGetDtos;
    }
}
