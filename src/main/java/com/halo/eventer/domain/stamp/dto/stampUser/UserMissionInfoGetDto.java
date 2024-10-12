package com.halo.eventer.domain.stamp.dto.stampUser;

import lombok.Getter;

@Getter
public class UserMissionInfoGetDto {
    private Long id;
    private boolean isComplete;

    public UserMissionInfoGetDto(Long id, boolean isComplete) {
        this.id = id;
        this.isComplete = isComplete;
    }
}
