package com.halo.eventer.domain.vote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class VoteCreateReqDto {
    private Long festivalId;
    private String title;
    private String content;
}
