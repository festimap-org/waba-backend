package com.halo.eventer.domain.vote.dto.request;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidateEnableUpdateRequest {

    @NotEmpty
    private List<@NotNull @Positive Long> candidateIds;

    private boolean isEnabled;
}
