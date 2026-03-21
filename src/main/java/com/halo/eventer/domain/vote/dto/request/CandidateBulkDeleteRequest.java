package com.halo.eventer.domain.vote.dto.request;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidateBulkDeleteRequest {

    @NotEmpty
    private List<@NotNull Long> candidateIds;
}
