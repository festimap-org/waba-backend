package com.halo.eventer.domain.stamp.dto.stamp;

import lombok.Getter;

import java.util.List;

@Getter
public class MissionGetListDto {
    private List<MissionGetDto> missionGetDtos;

    public MissionGetListDto() {}

    public MissionGetListDto(List<MissionGetDto> missionGetDtos) {
        this.missionGetDtos = missionGetDtos;
    }
}
