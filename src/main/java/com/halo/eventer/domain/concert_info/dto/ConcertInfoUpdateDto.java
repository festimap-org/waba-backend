package com.halo.eventer.domain.concert_info.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertInfoUpdateDto {
  private String summary;
  private List<String> images;
  private List<Long> deletedImages;
}
