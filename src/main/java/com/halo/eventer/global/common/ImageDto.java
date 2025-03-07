package com.halo.eventer.global.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageDto {
  private String image;

  public ImageDto(String image) {
    this.image = image;
  }
}
