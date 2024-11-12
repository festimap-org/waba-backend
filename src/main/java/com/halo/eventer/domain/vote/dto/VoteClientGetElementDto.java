package com.halo.eventer.domain.vote.dto;

import com.halo.eventer.domain.vote.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteClientGetElementDto {
    private Long id;
    private String title;
    private String content;
    private Long likeCnt;

    public VoteClientGetElementDto(Vote vote) {
        this.id = vote.getId();
        this.title = vote.getTitle();
        this.content = vote.getContent();
        this.likeCnt = vote.getLikeCnt();
    }
}
