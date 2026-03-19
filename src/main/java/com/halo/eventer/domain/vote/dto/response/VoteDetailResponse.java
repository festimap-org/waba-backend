package com.halo.eventer.domain.vote.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<CandidateResponse> candidates;
    private Long myVotedCandidateId;

    private VoteDetailResponse(Vote vote, boolean showVoteCount, Long myVotedCandidateId) {
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
        this.candidates = vote.getCandidates().stream()
                .filter(c -> c.isEnabled())
                .map(c -> CandidateResponse.from(c, showVoteCount))
                .collect(Collectors.toList());
        this.myVotedCandidateId = myVotedCandidateId;
    }

    public static VoteDetailResponse of(Vote vote, boolean showVoteCount, Long myVotedCandidateId) {
        return new VoteDetailResponse(vote, showVoteCount, myVotedCandidateId);
    }

    public static VoteDetailResponse forAdmin(Vote vote) {
        VoteDetailResponse response = new VoteDetailResponse();
        response.id = vote.getId();
        response.festivalId = vote.getFestival().getId();
        response.title = vote.getTitle();
        response.description = vote.getDescription();
        response.voteImageUrl = vote.getVoteImageUrl();
        response.candidateDefaultImageUrl = vote.getCandidateDefaultImageUrl();
        response.isActive = vote.isActive();
        response.displayEnabled = vote.isDisplayEnabled();
        response.voteEnabled = vote.isVoteEnabled();
        response.showRank = vote.isShowRank();
        response.showVoteCount = vote.isShowVoteCount();
        response.allowCancel = vote.isAllowCancel();
        response.displayStartAt = vote.getDisplayStartAt();
        response.displayEndAt = vote.getDisplayEndAt();
        response.voteStartAt = vote.getVoteStartAt();
        response.voteEndAt = vote.getVoteEndAt();
        response.candidates = vote.getCandidates().stream()
                .map(CandidateResponse::fromAdmin)
                .collect(Collectors.toList());
        return response;
    }
}
