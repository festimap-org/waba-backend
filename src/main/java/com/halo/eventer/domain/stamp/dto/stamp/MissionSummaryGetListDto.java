package com.halo.eventer.domain.stamp.dto.stamp;

import lombok.Getter;

import java.util.List;

@Getter
public class MissionSummaryGetListDto {
    private List<MissionSummaryGetDto> missionSummaryGetDtos;

    public MissionSummaryGetListDto() {}

    public MissionSummaryGetListDto(List<MissionSummaryGetDto> missionSummaryGetDtos) {
        this.missionSummaryGetDtos = missionSummaryGetDtos;
    }
}
