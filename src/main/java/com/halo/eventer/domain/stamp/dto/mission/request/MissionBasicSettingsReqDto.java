package com.halo.eventer.domain.stamp.dto.mission.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import com.halo.eventer.domain.stamp.dto.stamp.enums.MissionDetailsDesignLayout;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MissionBasicSettingsReqDto {
    @Min(2)
    @Max(16)
    private int missionCount;

    @NotNull
    private MissionDetailsDesignLayout defaultDetailLayout;
}
