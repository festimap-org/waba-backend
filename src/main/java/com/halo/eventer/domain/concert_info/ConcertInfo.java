package com.halo.eventer.domain.concert_info;

import com.halo.eventer.domain.concert_info.dto.ConcertInfoUpdateDto;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.image.Image;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ConcertInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String summary;

  @Enumerated(EnumType.STRING)
  private ConcertInfoType type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "festivalId")
  private Festival festival;

  @OneToMany(
      mappedBy = "concertInfo",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
  private List<Image> images;

  public ConcertInfo(String name, ConcertInfoType type, Festival festival) {
    this.name = name;
    this.type = type;
    this.festival = festival;
  }

  public void setInfo(ConcertInfoUpdateDto dto) {
    this.summary = dto.getSummary();
    this.images = dto.getImages().stream().map(Image::new).collect(Collectors.toList());
    images.forEach(o -> o.setConcertInfo(this));
  }
}
