package com.halo.eventer.map.dto.mapcategory;


import com.halo.eventer.map.MapCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MapCategoryResDto {
    private Long mapCategoryId;
    private String categoryName;
    private String pin;

    public MapCategoryResDto(MapCategory mapCategory) {
        this.mapCategoryId = mapCategory.getId();
        this.categoryName = mapCategory.getCategoryName();
        this.pin = mapCategory.getPin();
    }
}
