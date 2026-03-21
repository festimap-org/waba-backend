package com.halo.eventer.domain.vote.dto.request;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidateEnableUpdateRequest {

    @NotEmpty
    private List<Long> candidateIds;

    private boolean isEnabled;
}
