package com.halo.eventer.domain.vote.dto.response;

import java.time.LocalDateTime;

import com.halo.eventer.domain.vote.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteScheduleResponse {

    private LocalDateTime displayStartAt;
    private LocalDateTime displayEndAt;
    private boolean displayEnabled;
    private LocalDateTime voteStartAt;
    private LocalDateTime voteEndAt;
    private boolean voteEnabled;

    public static VoteScheduleResponse from(Vote vote) {
        return new VoteScheduleResponse(
                vote.getDisplayStartAt(),
                vote.getDisplayEndAt(),
                vote.isDisplayEnabled(),
                vote.getVoteStartAt(),
                vote.getVoteEndAt(),
                vote.isVoteEnabled());
    }
}
