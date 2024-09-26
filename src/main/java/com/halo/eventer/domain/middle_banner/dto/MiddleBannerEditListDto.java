package com.halo.eventer.domain.middle_banner.dto;

import com.halo.eventer.domain.notice.dto.BannerEditDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class MiddleBannerEditListDto {
    private List<MiddleBannerRankEditDto> bannerRankEditDtos;
}
