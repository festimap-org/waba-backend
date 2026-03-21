package com.halo.eventer.domain.vote.dto.request;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DisplayOrderUpdateRequest {

    @NotNull
    private Long candidateIdA;

    @NotNull
    private Long candidateIdB;
}
