package com.halo.eventer.domain.stamp.dto.stampUser;

import com.halo.eventer.domain.stamp.StampUser;
import lombok.Getter;

@Getter
public class StampUserGetDto {
    private String uuid;

    private boolean finished;

    private int participantCount;

    private UserMissionInfoGetListDto userMissionInfoGetListDto;

    public StampUserGetDto(StampUser stampUser, UserMissionInfoGetListDto userMissionInfoGetListDto) {
        this.uuid = stampUser.getUuid();
        this.finished = stampUser.isFinished();
        this.participantCount = stampUser.getParticipantCount();
        this.userMissionInfoGetListDto = userMissionInfoGetListDto;
    }
}
