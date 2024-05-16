package com.halo.eventer.festival.dto;


import com.halo.eventer.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MainMenuDto {
    private String menuName1;
    private String menuName2;
    private String menuImage1;
    private String menuImage2;

    public MainMenuDto(Festival festival) {
        this.menuName1 = festival.getMenuName1();
        this.menuName2 = festival.getMenuName2();
        this.menuImage1 = festival.getMenuImage1();
        this.menuImage2 = festival.getMenuImage2();
    }
}
