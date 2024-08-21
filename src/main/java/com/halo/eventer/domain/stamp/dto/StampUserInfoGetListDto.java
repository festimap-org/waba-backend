package com.halo.eventer.domain.stamp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StampUserInfoGetListDto {
    private List<StampUserInfoGetDto> stampUserInfoGetDtoList;

    public StampUserInfoGetListDto(List<StampUserInfoGetDto> stampUserInfoGetDtoList) {
        this.stampUserInfoGetDtoList = stampUserInfoGetDtoList;
    }
}
