package com.halo.eventer.domain.up_widget.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpWidgetCreateDto {
  private String title;
  private String url;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
}
