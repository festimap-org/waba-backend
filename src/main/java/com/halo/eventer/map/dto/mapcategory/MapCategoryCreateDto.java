package com.halo.eventer.map.dto.mapcategory;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MapCategoryCreateDto {
    private String categoryName;
    private String icon;

    public MapCategoryCreateDto(String categoryName, String icon) {
        this.categoryName = categoryName;
        this.icon = icon;
    }
}
