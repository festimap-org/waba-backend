package com.halo.eventer.domain.notice.dto;


import com.halo.eventer.global.common.ArticleType;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoticeReqDto {

    private String title;
    private String content;
    private String index;
    private String writer;

    private String thumbnail;

    private ArticleType type;

    private List<String> images;

    private List<Long> deleteIds;

}

