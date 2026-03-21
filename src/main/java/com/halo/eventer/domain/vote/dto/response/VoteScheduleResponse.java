package com.halo.eventer.domain.vote.dto.response;

import java.time.LocalDateTime;

import com.halo.eventer.domain.vote.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteScheduleResponse {

    private LocalDateTime displayStartAt;
    private LocalDateTime displayEndAt;
    private boolean displayEnabled;
    private LocalDateTime voteStartAt;
    private LocalDateTime voteEndAt;
    private boolean voteEnabled;

    public static VoteScheduleResponse from(Vote vote) {
        VoteScheduleResponse response = new VoteScheduleResponse();
        response.displayStartAt = vote.getDisplayStartAt();
        response.displayEndAt = vote.getDisplayEndAt();
        response.displayEnabled = vote.isDisplayEnabled();
        response.voteStartAt = vote.getVoteStartAt();
        response.voteEndAt = vote.getVoteEndAt();
        response.voteEnabled = vote.isVoteEnabled();
        return response;
    }
}
