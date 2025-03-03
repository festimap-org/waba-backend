package com.halo.eventer.domain.image.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileResDto {
  String url;

  public FileResDto(String url) {
    this.url = url;
  }
}
