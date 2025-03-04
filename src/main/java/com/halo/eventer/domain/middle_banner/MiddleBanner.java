package com.halo.eventer.domain.middle_banner;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.middle_banner.dto.MiddleBannerCreateDto;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MiddleBanner {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String url;
  private String image;
  private Integer bannerRank;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "festival_id")
  private Festival festival;

  public MiddleBanner(MiddleBannerCreateDto middleBannerCreateDto, Festival festival) {
    this.name = middleBannerCreateDto.getName();
    this.url = middleBannerCreateDto.getUrl();
    this.image = middleBannerCreateDto.getImage();
    this.bannerRank = 11;
    this.festival = festival;
  }

  public void update(MiddleBannerCreateDto middleBannerCreateDto) {
    this.name = middleBannerCreateDto.getName();
    this.url = middleBannerCreateDto.getUrl();
    this.image = middleBannerCreateDto.getImage();
  }

  public void setRank(Integer rank) {
    this.bannerRank = rank;
  }
}
