package com.halo.eventer.domain.vote.dto.response;

import java.time.LocalDateTime;

import com.halo.eventer.domain.vote.Candidate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidateListResponse {

    private long id;
    private String code;
    private String imageUrl;
    private String displayName;
    private String phone;
    private long voteCount;
    private String description;
    private LocalDateTime createdAt;
    private boolean isEnabled;

    private CandidateListResponse(Candidate candidate, String defaultImageUrl) {
        this.id = candidate.getId();
        this.code = candidate.getCode();
        this.imageUrl = candidate.getImageUrl() != null ? candidate.getImageUrl() : defaultImageUrl;
        this.displayName = candidate.getDisplayName();
        this.phone = candidate.getPhone();
        this.voteCount = candidate.getVoteCount();
        this.description = candidate.getDescription();
        this.createdAt = candidate.getCreatedAt();
        this.isEnabled = candidate.isEnabled();
    }

    public static CandidateListResponse from(Candidate candidate, String defaultImageUrl) {
        return new CandidateListResponse(candidate, defaultImageUrl);
    }
}
