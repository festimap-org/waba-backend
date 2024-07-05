package com.halo.eventer.domain.notice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BannerEditListDto {
    private List<BannerEditDto> BannerEditListDto;

    public BannerEditListDto(List<BannerEditDto> bannerEditListDto) {
        BannerEditListDto = bannerEditListDto;
    }
}
