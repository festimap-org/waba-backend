package com.halo.eventer.domain.stamp.dto.mission.response;

import com.halo.eventer.domain.stamp.StampMissionPrize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MissionPrizeResDto {
    private long id;
    private int requiredCount;
    private String description;

    public static MissionPrizeResDto from(StampMissionPrize prize) {
        return new MissionPrizeResDto(prize.getId(), prize.getRequiredCount(), prize.getDescription());
    }
}
