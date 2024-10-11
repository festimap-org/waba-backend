package com.halo.eventer.domain.stamp.dto.stamp;

import lombok.Getter;

@Getter
public class MissionGetDto {
    private Long missionId;
    private String title;

    public MissionGetDto() {}

    public MissionGetDto(Long missionId, String title) {
        this.missionId = missionId;
        this.title = title;
    }
}
