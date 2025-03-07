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

  public MainMenuDto(String menuName1, String menuName2, String menuSummary1, String menuSummary2,
                     String menuImage1, String menuImage2, String menuUrl1, String menuUrl2) {
    this.menuName1 = menuName1;
    this.menuName2 = menuName2;
    this.menuSummary1 = menuSummary1;
    this.menuSummary2 = menuSummary2;
    this.menuImage1 = menuImage1;
    this.menuImage2 = menuImage2;
    this.menuUrl1 = menuUrl1;
    this.menuUrl2 = menuUrl2;
  }

  public static MainMenuDto from(Festival festival) {
    return new MainMenuDto(festival.getMenuName1(),festival.getMenuName2(),festival.getMenuSummary1(),
            festival.getMenuSummary2(), festival.getMenuImage1(), festival.getMenuImage2(),
            festival.getMenuUrl1(), festival.getMenuUrl2());
  }
}
