package com.halo.eventer.domain.stamp.dto;

import com.halo.eventer.domain.stamp.Stamp;
import lombok.Getter;

@Getter
public class StampInfoGetDto {
    private String uuid;
    private String name;
    private String phone;
    private boolean finished;
    private int participantCount;

    public StampInfoGetDto(Stamp stamp, String name, String phone) {
        this.uuid = stamp.getUuid();
        this.name = name;
        this.phone = phone;
        this.finished = stamp.isFinished();
        this.participantCount = stamp.getParticipantCount();
    }
}
