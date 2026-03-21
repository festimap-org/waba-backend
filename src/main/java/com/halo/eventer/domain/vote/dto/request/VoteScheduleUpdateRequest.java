package com.halo.eventer.domain.vote.dto.request;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteScheduleUpdateRequest {

    @NotNull
    private LocalDateTime displayStartAt;

    @NotNull
    private LocalDateTime displayEndAt;

    private boolean displayEnabled;

    @NotNull
    private LocalDateTime voteStartAt;

    @NotNull
    private LocalDateTime voteEndAt;

    private boolean voteEnabled;
}
