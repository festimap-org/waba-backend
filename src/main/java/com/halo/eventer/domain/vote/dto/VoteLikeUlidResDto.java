package com.halo.eventer.domain.vote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteLikeUlidResDto {
    private String ulid;

    public VoteLikeUlidResDto(String ulid) {
        this.ulid = ulid;
    }
}
