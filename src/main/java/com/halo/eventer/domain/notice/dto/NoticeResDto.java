package com.halo.eventer.domain.notice.dto;

import com.halo.eventer.domain.notice.Notice;
import com.halo.eventer.global.common.ArticleType;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
public class NoticeResDto {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime updateTime;

    private ArticleType type;

    private String image;

    public NoticeResDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.updateTime = notice.getUpdateTime();
        this.image = notice.getImages().get(0).getImage_url();
        this.type = notice.getType();
    }
}

