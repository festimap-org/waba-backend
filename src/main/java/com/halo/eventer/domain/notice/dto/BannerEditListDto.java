package com.halo.eventer.domain.notice.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BannerEditListDto {
    private List<BannerEditDto> bannerEditListDto;

    public BannerEditListDto(List<BannerEditDto> bannerEditListDto) {
        this.bannerEditListDto = bannerEditListDto;
    }
}
