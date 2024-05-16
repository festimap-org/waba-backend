package com.halo.eventer.festival.dto;


import com.halo.eventer.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FestivalConcertMenuDto {
    private String summary;
    private String icon;

    public FestivalConcertMenuDto(String summary, String icon) {
        this.summary = summary;
        this.icon = icon;
    }
}
