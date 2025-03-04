package com.halo.eventer.domain.notice.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisteredBannerGetListDto {
    private List<RegisteredBannerGetDto> registeredBannerGetListDto;


    public RegisteredBannerGetListDto(List<RegisteredBannerGetDto> registeredBannerGetListDto) {
        this.registeredBannerGetListDto = registeredBannerGetListDto;
    }
}
