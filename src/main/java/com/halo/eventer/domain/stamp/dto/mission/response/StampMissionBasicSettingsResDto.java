package com.halo.eventer.domain.stamp.dto.mission.response;

import java.util.List;

import com.halo.eventer.domain.stamp.StampMissionBasicSetting;
import com.halo.eventer.domain.stamp.dto.stamp.enums.MissionDetailsDesignLayout;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampMissionBasicSettingsResDto {
    private int missionCount;
    private MissionDetailsDesignLayout defaultDetailLayout;
    private List<MissionPrizeResDto> prizes;

    public static StampMissionBasicSettingsResDto from(StampMissionBasicSetting s) {
        return new StampMissionBasicSettingsResDto(
                s.getMissionCount(),
                s.getDefaultDetailLayout(),
                s.getPrizes().stream().map(MissionPrizeResDto::from).toList());
    }
}
