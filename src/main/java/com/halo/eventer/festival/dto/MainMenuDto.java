package com.halo.eventer.festival.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MainMenuDto {
    private String menuName1;
    private String menuName2;
    private String menuImage1;
    private String menuImage2;

    public MainMenuDto(String menuName1, String menuName2, String menuImage1, String menuImage2) {
        this.menuName1 = menuName1;
        this.menuName2 = menuName2;
        this.menuImage1 = menuImage1;
        this.menuImage2 = menuImage2;
    }
}
