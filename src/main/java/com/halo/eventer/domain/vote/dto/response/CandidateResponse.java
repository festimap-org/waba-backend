package com.halo.eventer.domain.vote.dto.response;

import com.halo.eventer.domain.vote.Candidate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidateResponse {

    private Long id;
    private String code;
    private String displayName;
    private String realName;
    private String description;
    private String imageUrl;
    private String phone;
    private long voteCount;
    private boolean isEnabled;
    private int displayOrder;

    private CandidateResponse(Candidate candidate, boolean showVoteCount) {
        this.id = candidate.getId();
        this.code = candidate.getCode();
        this.displayName = candidate.getDisplayName();
        this.realName = candidate.getRealName();
        this.description = candidate.getDescription();
        this.imageUrl = candidate.getImageUrl();
        this.phone = candidate.getPhone();
        this.voteCount = showVoteCount ? candidate.getVoteCount() : 0;
        this.isEnabled = candidate.isEnabled();
        this.displayOrder = candidate.getDisplayOrder();
    }

    public static CandidateResponse from(Candidate candidate, boolean showVoteCount) {
        return new CandidateResponse(candidate, showVoteCount);
    }

    public static CandidateResponse fromAdmin(Candidate candidate) {
        return new CandidateResponse(candidate, true);
    }
}
