package com.halo.eventer.domain.map.dto.mapcategory;

import com.halo.eventer.domain.map.MapCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MapCategoryImageDto {
  private String pin;
  private String icon;

  public MapCategoryImageDto(MapCategory mapCategory) {
    this.pin = mapCategory.getPin();
    this.icon = mapCategory.getIcon();
  }
}
