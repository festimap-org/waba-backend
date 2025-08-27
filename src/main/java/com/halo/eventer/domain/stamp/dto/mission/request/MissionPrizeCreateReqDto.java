package com.halo.eventer.domain.stamp.dto.mission.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MissionPrizeCreateReqDto {
    @Min(1)
    private int requiredCount;

    @NotBlank
    private String prizeDescription;
}
