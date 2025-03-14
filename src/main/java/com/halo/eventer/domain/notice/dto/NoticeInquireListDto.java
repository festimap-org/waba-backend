package com.halo.eventer.domain.content.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NoticeInquireListDto {
  private List<NoticeInquireDto> noticeInquireListDto;

  public NoticeInquireListDto(List<NoticeInquireDto> noticeInquireListDto) {
    this.noticeInquireListDto = noticeInquireListDto;
  }
}
