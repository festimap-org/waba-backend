package com.halo.eventer.domain.notice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class BannerEditReqDto {

    private Long noticeId;
    private Integer rank;

}
