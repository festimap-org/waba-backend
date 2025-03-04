package com.halo.eventer.domain.notice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BannerEditListDto {
    private List<BannerEditDto> bannerEditListDto;

    public BannerEditListDto(List<BannerEditDto> bannerEditListDto) {
        this.bannerEditListDto = bannerEditListDto;
    }
}
