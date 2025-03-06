package com.halo.eventer.domain.down_widget;

import com.halo.eventer.domain.down_widget.dto.DownWidgetDto;
import com.halo.eventer.domain.festival.Festival;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class DownWidget {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String url;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "festival_id")
  private Festival festival;

  public DownWidget(DownWidgetDto dto, Festival festival) {
    this.name = dto.getName();
    this.url = dto.getUrl();
    this.festival = festival;
  }

  private DownWidget(Festival festival) {
    this.festival = festival;
  }

  public static DownWidget from(Festival festival){
    return new DownWidget(festival);
  }

  public void updateDownWidget(DownWidgetDto dto) {
    this.name = dto.getName();
    this.url = dto.getUrl();
  }
}
