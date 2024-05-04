package com.halo.eventer.festival.dto;


import com.halo.eventer.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ColorReqDto {
    private String mainColor;
    private String subColor;
    private String fontColor;

    public ColorReqDto(Festival festival) {
        this.mainColor = festival.getMainColor();
        this.subColor = festival.getSubColor();
        this.fontColor = festival.getFontColor();
    }
}
