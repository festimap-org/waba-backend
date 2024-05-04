package com.halo.eventer.festival.dto;


import com.halo.eventer.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FestivalListDto {

    private Long id;
    private String festivalName;

    public FestivalListDto(Festival festival) {
        this.festivalName = festival.getName();
        this.id = festival.getId();
    }
}
