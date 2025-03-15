package com.halo.eventer.domain.notice.dto;

import com.halo.eventer.global.common.ArticleType;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@Getter
public class NoticeCreateDto {
  private String title;
  private String content;
  private String tag;
  private String writer;
  private String thumbnail;
  private ArticleType type;
  private List<String> images;
}
