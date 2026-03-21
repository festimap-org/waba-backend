package com.halo.eventer.domain.vote.dto.response;

import java.time.LocalDateTime;

import com.halo.eventer.domain.vote.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteDetailResponse {

    private Long id;
    private Long festivalId;
    private String title;
    private String description;
    private String voteImageUrl;
    private String candidateDefaultImageUrl;
    private boolean isActive;
    private boolean displayEnabled;
    private boolean voteEnabled;
    private boolean showRank;
    private boolean showVoteCount;
    private boolean allowCancel;
    private LocalDateTime displayStartAt;
    private LocalDateTime displayEndAt;
    private LocalDateTime voteStartAt;
    private LocalDateTime voteEndAt;
    private Long myVotedCandidateId;

    private VoteDetailResponse(Vote vote, Long myVotedCandidateId) {
        this.id = vote.getId();
        this.festivalId = vote.getFestival().getId();
        this.title = vote.getTitle();
        this.description = vote.getDescription();
        this.voteImageUrl = vote.getVoteImageUrl();
        this.candidateDefaultImageUrl = vote.getCandidateDefaultImageUrl();
        this.isActive = vote.isActive();
        this.displayEnabled = vote.isDisplayEnabled();
        this.voteEnabled = vote.isVoteEnabled();
        this.showRank = vote.isShowRank();
        this.showVoteCount = vote.isShowVoteCount();
        this.allowCancel = vote.isAllowCancel();
        this.displayStartAt = vote.getDisplayStartAt();
        this.displayEndAt = vote.getDisplayEndAt();
        this.voteStartAt = vote.getVoteStartAt();
        this.voteEndAt = vote.getVoteEndAt();
        this.myVotedCandidateId = myVotedCandidateId;
    }

    public static VoteDetailResponse of(Vote vote, Long myVotedCandidateId) {
        return new VoteDetailResponse(vote, myVotedCandidateId);
    }

    public static VoteDetailResponse forAdmin(Vote vote) {
        return new VoteDetailResponse(vote, null);
    }
}
