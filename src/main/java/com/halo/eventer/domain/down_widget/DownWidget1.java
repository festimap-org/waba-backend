package com.halo.eventer.domain.down_widget;

import com.halo.eventer.domain.down_widget.dto.DownWidgetDto;
import com.halo.eventer.domain.festival.Festival;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class DownWidget1 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String url;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "festival_id")
  private Festival festival;

  public DownWidget1(DownWidgetDto dto, Festival festival) {
    this.name = dto.getName();
    this.url = dto.getUrl();
    this.festival = festival;
  }

  private DownWidget1(Festival festival) {
    this.festival = festival;
  }

  public static DownWidget1 from(Festival festival){
    return new DownWidget1(festival);
  }

  public void updateDownWidget(DownWidgetDto dto) {
    this.name = dto.getName();
    this.url = dto.getUrl();
  }
}
