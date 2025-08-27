package com.halo.eventer.domain.stamp.dto.mission.response;

import java.util.List;

import com.halo.eventer.domain.stamp.Stamp;
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
    private MissionDetailsDesignLayout layout;
    private List<MissionPrizeResDto> prizes;

    public static StampMissionBasicSettingsResDto from(StampMissionBasicSetting setting, Stamp stamp) {
        return new StampMissionBasicSettingsResDto(
                setting.getMissionCount(),
                setting.getDefaultDetailLayout(),
                stamp.getPrizes().stream().map(MissionPrizeResDto::from).toList());
    }
}
