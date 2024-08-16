package com.halo.eventer.domain.stamp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StampInfoGetListDto {
    private List<StampInfoGetDto> stampInfoGetDtoList;

    public StampInfoGetListDto(List<StampInfoGetDto> stampInfoGetDtoList) {
        this.stampInfoGetDtoList = stampInfoGetDtoList;
    }
}
