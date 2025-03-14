package com.halo.eventer.domain.content.dto;

import com.halo.eventer.domain.content.Content;
import com.halo.eventer.global.common.ArticleType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisteredBannerGetDto {
    private Long id;
    private Integer rank;
    private String thumbnail;
    private ArticleType type;

    public RegisteredBannerGetDto(Content n) {
        this.id = n.getId();
        this.thumbnail = n.getThumbnail();
        this.rank = n.getBannerRank();
        this.type = n.getType();
    }

    public static List<RegisteredBannerGetDto> fromRegisteredBannerList(List<Content> contentList) {
        return contentList.stream()
                .map(RegisteredBannerGetDto::new)
                .collect(Collectors.toList());
    }
}
