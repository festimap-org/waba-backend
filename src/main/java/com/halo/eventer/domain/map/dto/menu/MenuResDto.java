package com.halo.eventer.domain.map.dto.menu;

import com.halo.eventer.domain.map.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuResDto {

    private Long id;
    private String name;
    private Integer price;
    private String image;
    private String summary;

    public MenuResDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.summary = menu.getSummary();
        this.image = menu.getImage();
    }

    public MenuResDto(Long id, String name, Integer price, String image, String summary) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.summary = summary;
    }
}
