package com.halo.eventer.domain.vote.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteInfoUpdateRequest {

    @NotBlank
    @Size(max = 50)
    private String title;

    @Pattern(regexp = "^https?://.+", message = "URL은 http 또는 https로 시작해야 합니다.")
    @Size(max = 500)
    private String voteImageUrl;

    @Pattern(regexp = "^https?://.+", message = "URL은 http 또는 https로 시작해야 합니다.")
    @Size(max = 500)
    private String candidateDefaultImageUrl;

    private Boolean showRank;
    private Boolean showVoteCount;
    private Boolean allowCancel;
}
