package com.halo.eventer.domain.stamp.dto.stampUser;

import lombok.Getter;

import java.util.List;

@Getter
public class UserMissionInfoGetListDto {
    private List<UserMissionInfoGetDto> userMissionInfoGetDtos;

    public UserMissionInfoGetListDto(List<UserMissionInfoGetDto> userMissionInfoGetDtos) {
        this.userMissionInfoGetDtos = userMissionInfoGetDtos;
    }
}
