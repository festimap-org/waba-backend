package com.halo.eventer.domain.up_widget;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.up_widget.dto.UpWidgetCreateDto;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UpWidget {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String url;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "festival_id")
  private Festival festival;

  public UpWidget(UpWidgetCreateDto upWidgetCreateDto, Festival festival) {
    this.title = upWidgetCreateDto.getTitle();
    this.url = upWidgetCreateDto.getUrl();
    this.startDateTime = upWidgetCreateDto.getStartDateTime();
    this.endDateTime = upWidgetCreateDto.getEndDateTime();
    this.festival = festival;
  }

  public void update(UpWidgetCreateDto dto) {
    this.title = dto.getTitle();
    this.url = dto.getUrl();
    this.startDateTime = dto.getStartDateTime();
    this.endDateTime = dto.getEndDateTime();
  }
}
