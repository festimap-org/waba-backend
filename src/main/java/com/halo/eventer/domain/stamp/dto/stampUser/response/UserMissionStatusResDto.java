package com.halo.eventer.domain.stamp.dto.stampUser.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMissionStatusResDto {
    private Long userMissionId;
    private Long missionId;
    private String missionTitle;
    private boolean complete;
}
