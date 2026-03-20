package com.halo.eventer.domain.festival.dto;

import com.halo.eventer.domain.festival.Festival;
import lombok.Getter;

@Getter
public class FestivalCreateResponseDto {
    private final Long festivalId;
    private final String name;
    private final String subDomain;

    public FestivalCreateResponseDto(Festival festival) {
        this.festivalId = festival.getId();
        this.name = festival.getName();
        this.subDomain = festival.getSubDomain();
    }
}
