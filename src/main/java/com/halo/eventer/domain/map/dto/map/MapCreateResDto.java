package com.halo.eventer.domain.map.dto.map;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MapCreateResDto {
  private Long mapId;

  public MapCreateResDto(Long storeId) {
    this.mapId = storeId;
  }
}
