package com.halo.eventer.domain.stamp.dto.mission.request;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDescReq {
    @NotBlank
    private String title;

    @NotBlank
    private String body;
}
