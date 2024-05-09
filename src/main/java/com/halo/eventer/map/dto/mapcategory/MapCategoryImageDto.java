package com.halo.eventer.map.dto.mapcategory;


import com.halo.eventer.map.MapCategory;
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
