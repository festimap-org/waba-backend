package com.halo.eventer.domain.vote.dto.request;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidateEnableUpdateRequest {

    @NotEmpty
    private List<@NotNull @Positive Long> candidateIds;

    @JsonProperty("isEnabled")
    private boolean isEnabled;
}
