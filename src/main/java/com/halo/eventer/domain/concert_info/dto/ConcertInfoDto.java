package com.halo.eventer.domain.concert_info.dto;

import com.halo.eventer.domain.concert_info.ConcertInfo;
import com.halo.eventer.domain.concert_info.ConcertInfoType;
import com.halo.eventer.domain.image.dto.ImageUpdateDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertInfoDto {

  private Long id;

  private String name;
  private String summary;

  private ConcertInfoType type;
  private List<ImageUpdateDto> images;

  public ConcertInfoDto(ConcertInfo concertInfo) {
    this.id = concertInfo.getId();
    this.name = concertInfo.getName();
    this.type = concertInfo.getType();
    this.summary = concertInfo.getSummary();
    this.images =
        concertInfo.getImages().stream().map(ImageUpdateDto::new).collect(Collectors.toList());
  }
}
