package com.halo.eventer.festival.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FestivalCreateDto {

    private String name;

    private String content;

    private String image;

    private String location;

}
