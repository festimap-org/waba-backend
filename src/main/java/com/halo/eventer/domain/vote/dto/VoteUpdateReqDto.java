package com.halo.eventer.domain.vote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteUpdateReqDto {
  private String title;
  private String content;
}
