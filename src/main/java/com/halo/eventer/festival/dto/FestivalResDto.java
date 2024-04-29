package com.halo.eventer.festival.dto;

import com.halo.eventer.festival.Festival;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FestivalResDto {
    private Long id;

    private String name;

    private String content;

    private String image;

    private String location;

    public FestivalResDto(Festival festival) {
        this.id = festival.getId();
        this.name = festival.getName();
        this.content = festival.getContent();
        this.image = festival.getImage();
        this.location = festival.getLocation();
    }
}
