package com.halo.eventer.domain.vote.dto.response;

import com.halo.eventer.domain.vote.Vote;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteInfoResponse {

    private String title;
    private String voteImageUrl;
    private String candidateDefaultImageUrl;
    private boolean showRank;
    private boolean showVoteCount;
    private boolean allowCancel;

    public static VoteInfoResponse from(Vote vote) {
        VoteInfoResponse response = new VoteInfoResponse();
        response.title = vote.getTitle();
        response.voteImageUrl = vote.getVoteImageUrl();
        response.candidateDefaultImageUrl = vote.getCandidateDefaultImageUrl();
        response.showRank = vote.isShowRank();
        response.showVoteCount = vote.isShowVoteCount();
        response.allowCancel = vote.isAllowCancel();
        return response;
    }
}
