package com.halo.eventer.domain.festival.dto;


import com.halo.eventer.domain.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FestivalListDto {

    private Long id;
    private String festivalName;
    private String subAddress;

    public FestivalListDto(Festival festival) {
        this.festivalName = festival.getName();
        this.id = festival.getId();
        this.subAddress = festival.getSubAddress();
    }
}
