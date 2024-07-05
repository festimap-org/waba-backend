package com.halo.eventer.domain.notice.dto;

import com.halo.eventer.domain.notice.Notice;
import com.halo.eventer.global.common.ArticleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisteredBannerGetListDto {
    private Long id;
    private Integer rank;
    private String thumbnail;
    private ArticleType type;

    public RegisteredBannerGetListDto(Notice n) {
        this.id = n.getId();
        this.thumbnail = n.getThumbnail();
        this.rank = n.getBannerRank();
        this.type = n.getType();
    }
}
