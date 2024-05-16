package com.halo.eventer.notice.dto;


import com.halo.eventer.common.ArticleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
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

