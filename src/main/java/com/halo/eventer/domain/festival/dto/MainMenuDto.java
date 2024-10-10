package com.halo.eventer.domain.festival.dto;


import com.halo.eventer.domain.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MainMenuDto {
    private String menuName1;
    private String menuName2;
    private String menuSummary1;
    private String menuSummary2;
    private String menuImage1;
    private String menuImage2;
    private String menuUrl1;
    private String menuUrl2;


    public MainMenuDto(Festival festival) {
        this.menuName1 = festival.getMenuName1();
        this.menuName2 = festival.getMenuName2();
        this.menuImage1 = festival.getMenuImage1();
        this.menuImage2 = festival.getMenuImage2();
        this.menuSummary1 = festival.getMenuSummary1();
        this.menuSummary2 = festival.getMenuSummary2();
        this.menuUrl1 = festival.getMenuUrl1();
        this.menuUrl2 = festival.getMenuUrl2();
    }
}
