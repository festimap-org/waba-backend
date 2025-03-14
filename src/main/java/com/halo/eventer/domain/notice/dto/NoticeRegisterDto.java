package com.halo.eventer.domain.content.dto;


import com.halo.eventer.global.common.ArticleType;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoticeRegisterDto {

    private String title;
    private String content;
    private String index;
    private String writer;

    private String thumbnail;

    private ArticleType type;

    private List<String> images;

    private List<Long> deleteIds;

}

