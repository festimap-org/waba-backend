package com.halo.eventer.domain.notice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RegisteredBannerGetListDto {
    private List<RegisteredBannerGetDto> registeredBannerGetListDto;


    public RegisteredBannerGetListDto(List<RegisteredBannerGetDto> registeredBannerGetListDto) {
        this.registeredBannerGetListDto = registeredBannerGetListDto;
    }
}
