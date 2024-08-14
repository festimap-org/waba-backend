package com.halo.eventer.domain.stamp.dto;

import com.halo.eventer.domain.stamp.StampInfo;
import lombok.Getter;

@Getter
public class MissionInfoGetDto {
    private boolean mission1;
    private boolean mission2;
    private boolean mission3;
    private boolean mission4;
    private boolean mission5;
    private boolean mission6;

    public MissionInfoGetDto(StampInfo stampInfo) {
        this.mission1 = stampInfo.isMission1();
        this.mission2 = stampInfo.isMission2();
        this.mission3 = stampInfo.isMission3();
        this.mission4 = stampInfo.isMission4();
        this.mission5 = stampInfo.isMission5();
        this.mission6 = stampInfo.isMission6();
    }
}
