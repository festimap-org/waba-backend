package com.halo.eventer.domain.stamp.dto.stampUser;

import java.util.List;
import lombok.Getter;

@Getter
public class UserMissionInfoGetListDto {
    private List<UserMissionInfoGetDto> userMissionInfoGetDtos;

    public UserMissionInfoGetListDto(List<UserMissionInfoGetDto> userMissionInfoGetDtos) {
        this.userMissionInfoGetDtos = userMissionInfoGetDtos;
    }
}
