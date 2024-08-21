package com.halo.eventer.domain.stamp.dto;

import com.halo.eventer.domain.stamp.StampUser;
import lombok.Getter;

@Getter
public class StampUserGetDto {
    private String uuid;

    private boolean mission1;
    private boolean mission2;
    private boolean mission3;
    private boolean mission4;
    private boolean mission5;
    private boolean mission6;

    private boolean finished;

    private int participantCount;

    public StampUserGetDto(StampUser stampUser) {
        this.uuid = stampUser.getUuid();
        this.mission1 = stampUser.isMission1();
        this.mission2 = stampUser.isMission2();
        this.mission3 = stampUser.isMission3();
        this.mission4 = stampUser.isMission4();
        this.mission5 = stampUser.isMission5();
        this.mission6 = stampUser.isMission6();
        this.finished = stampUser.isFinished();
        this.participantCount = stampUser.getParticipantCount();
    }
}
