package com.halo.eventer.domain.notice.dto;

import com.halo.eventer.domain.notice.Notice;
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

  public NoticeInquireDto(Notice notice) {
    this.id = notice.getId();
    this.title = notice.getTitle();
    this.thumbnail = notice.getThumbnail();
    this.index = notice.getTag();
    this.writer = notice.getWriter();
    this.time = notice.getUpdateTime();
    this.picked = notice.isPicked();
  }
}
