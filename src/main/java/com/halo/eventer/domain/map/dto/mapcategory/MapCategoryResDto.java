package com.halo.eventer.domain.map.dto.mapcategory;

import com.halo.eventer.domain.map.MapCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MapCategoryResDto {
  private Long mapCategoryId;
  private String categoryName;
  private String pin;
  private String icon;
  private int rank;

  public MapCategoryResDto(MapCategory mapCategory) {
    this.mapCategoryId = mapCategory.getId();
    this.categoryName = mapCategory.getCategoryName();
    this.pin = mapCategory.getPin();
    this.icon = mapCategory.getIcon();
    this.rank = mapCategory.getCategory_rank();
  }
}
