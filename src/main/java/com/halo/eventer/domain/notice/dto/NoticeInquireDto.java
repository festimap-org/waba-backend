package com.halo.eventer.domain.content.dto;

import com.halo.eventer.domain.content.Content;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class NoticeInquireDto {

  private Long id;
  private String title;
  private String index;
  private String writer;
  private String thumbnail;
  private boolean picked;
  private LocalDateTime time;

  public NoticeInquireDto(Content content) {
    this.id = content.getId();
    this.title = content.getTitle();
    this.thumbnail = content.getThumbnail();
    this.index = content.getTag();
    this.writer = content.getWriter();
    this.time = content.getUpdateTime();
    this.picked = content.isPicked();
  }
}
