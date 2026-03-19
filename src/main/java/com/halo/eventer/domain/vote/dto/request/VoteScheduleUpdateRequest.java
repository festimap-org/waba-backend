package com.halo.eventer.domain.vote.dto.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteScheduleUpdateRequest {

    private LocalDateTime displayStartAt;
    private LocalDateTime displayEndAt;
    private LocalDateTime voteStartAt;
    private LocalDateTime voteEndAt;
}
