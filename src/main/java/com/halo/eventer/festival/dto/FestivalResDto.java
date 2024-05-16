package com.halo.eventer.festival.dto;

import com.halo.eventer.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FestivalResDto {
    private Long id;

    private String name;

    private String logo;

    private ColorReqDto colors;

    public FestivalResDto(Festival festival, ColorReqDto colors) {
        this.id = festival.getId();
        this.name = festival.getName();
        this.logo = festival.getLogo();
        this.colors =colors;
    }
}
