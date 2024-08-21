package com.halo.eventer.domain.stamp.dto;

import com.halo.eventer.domain.stamp.StampUser;
import lombok.Getter;

@Getter
public class StampUserInfoGetDto {
    private String uuid;
    private String name;
    private String phone;
    private boolean finished;
    private int participantCount;

    public StampUserInfoGetDto(StampUser stampUser, String name, String phone) {
        this.uuid = stampUser.getUuid();
        this.name = name;
        this.phone = phone;
        this.finished = stampUser.isFinished();
        this.participantCount = stampUser.getParticipantCount();
    }
}
