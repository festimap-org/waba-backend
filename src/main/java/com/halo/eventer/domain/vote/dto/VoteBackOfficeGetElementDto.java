package com.halo.eventer.domain.vote.dto;

import com.halo.eventer.domain.vote.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteBackOfficeGetElementDto {
  private Long id;
  private String title;
  private Long likeCnt;

  public VoteBackOfficeGetElementDto(Vote vote) {
    this.id = vote.getId();
    this.title = vote.getTitle();
    this.likeCnt = vote.getLikeCnt();
  }
}
