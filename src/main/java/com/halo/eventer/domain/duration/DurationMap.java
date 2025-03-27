package com.halo.eventer.domain.duration;

import com.halo.eventer.domain.map.Map;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class DurationMap {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "duration_id")
  private Duration duration;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "map_id")
  private Map map;

  public DurationMap(Duration duration, Map map) {
    this.duration = duration;
    this.map = map;
  }
}
