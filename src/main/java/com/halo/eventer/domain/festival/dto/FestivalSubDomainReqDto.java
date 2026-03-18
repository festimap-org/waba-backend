package com.halo.eventer.domain.festival.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FestivalSubDomainReqDto {
    @NotBlank
    private String subDomain;
}
