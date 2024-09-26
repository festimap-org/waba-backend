package com.halo.eventer.domain.middle_banner.dto;


import com.halo.eventer.domain.middle_banner.MiddleBanner;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MiddleBannerListDto {
    private List<MiddleBannerElementDto> middleBannerList;

    public MiddleBannerListDto(List<MiddleBanner> middleBannerList) {
        this.middleBannerList = middleBannerList.stream().map(MiddleBannerElementDto::new).collect(Collectors.toList());
    }
}
