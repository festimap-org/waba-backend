package com.halo.eventer.domain.festival.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FestivalCreateDto {

    @NotBlank
    private String name;

    @NotBlank
    private String subDomain;
}
