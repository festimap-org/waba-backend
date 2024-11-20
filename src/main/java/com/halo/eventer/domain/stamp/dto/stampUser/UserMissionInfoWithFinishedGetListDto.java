package com.halo.eventer.domain.stamp.dto.stampUser;

import lombok.Getter;


@Getter
public class UserMissionInfoWithFinishedGetListDto {
    private boolean finished;
    private UserMissionInfoGetListDto userMissionInfoGetListDto;

    public UserMissionInfoWithFinishedGetListDto(boolean finished, UserMissionInfoGetListDto userMissionInfoGetDtos) {
        this.finished = finished;
        this.userMissionInfoGetListDto = userMissionInfoGetDtos;
    }
}
