package com.halo.eventer.domain.stamp.dto.stampUser.request;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MissionCompletionUpdateReq {
    @NotNull
    private boolean complete;
}
