package com.halo.eventer.domain.stamp.dto.mission.request;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MissionCreateReqDto {
    @NotNull
    private String name;
}
