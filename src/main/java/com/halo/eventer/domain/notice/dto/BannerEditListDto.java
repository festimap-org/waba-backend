package com.halo.eventer.domain.notice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BannerEditListDto {
    private List<BannerEditDto> BannerEditListDto;

    public BannerEditListDto(List<BannerEditDto> bannerEditListDto) {
        BannerEditListDto = bannerEditListDto;
    }
}
