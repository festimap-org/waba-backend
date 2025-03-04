package com.halo.eventer.domain.notice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RegisteredBannerGetListDto {
    private List<RegisteredBannerGetDto> registeredBannerGetListDto;


    public RegisteredBannerGetListDto(List<RegisteredBannerGetDto> registeredBannerGetListDto) {
        this.registeredBannerGetListDto = registeredBannerGetListDto;
    }
}
