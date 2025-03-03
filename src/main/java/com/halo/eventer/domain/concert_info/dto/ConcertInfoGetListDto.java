package com.halo.eventer.domain.concert_info.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertInfoGetListDto {
  private List<ConcertInfoGetDto> concertInfoGetDto;

  public ConcertInfoGetListDto(List<ConcertInfoGetDto> concertInfoGetDto) {
    this.concertInfoGetDto = concertInfoGetDto;
  }
}
