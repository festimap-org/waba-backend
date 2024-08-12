package com.halo.eventer.domain.stamp.dto;

import com.halo.eventer.domain.stamp.Stamp;
import lombok.Getter;

@Getter
public class MissionInfoGetDto {
    private boolean mission1;
    private boolean mission2;
    private boolean mission3;
    private boolean mission4;
    private boolean mission5;
    private boolean mission6;

    public MissionInfoGetDto(Stamp stamp) {
        this.mission1 = stamp.isMission1();
        this.mission2 = stamp.isMission2();
        this.mission3 = stamp.isMission3();
        this.mission4 = stamp.isMission4();
        this.mission5 = stamp.isMission5();
        this.mission6 = stamp.isMission6();
    }
}
