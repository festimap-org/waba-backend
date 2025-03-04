package com.halo.eventer.domain.concert;

import com.halo.eventer.domain.duration.Duration;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.image.Image;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Concert {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String thumbnail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "festival_id")
  private Festival festival;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "durationId")
  private Duration duration;

  @OneToMany(
      mappedBy = "concert",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
  private List<Image> images = new ArrayList<>();

  public Concert(String thumbnail, Festival festival, Duration duration) {

    this.thumbnail = thumbnail;
    this.festival = festival;
    this.duration = duration;
  }

  public void setAll(String thumbnail, Duration duration) {
    this.thumbnail = thumbnail;
    this.duration = duration;
  }

  public void setImages(List<Image> images) {
    this.images = images;
    images.forEach(o -> o.setConcert(this));
  }
}
