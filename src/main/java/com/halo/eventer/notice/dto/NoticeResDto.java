package com.halo.eventer.notice.dto;

import com.halo.eventer.common.ArticleType;
import com.halo.eventer.notice.Notice;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
public class NoticeResDto {

    private Long id;

    private String title;

    private String subtitle;

    private String content;

    private LocalDateTime updateTime;

    private ArticleType type;

    private String image;

    public NoticeResDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.subtitle = notice.getSubtitle();
        this.content = notice.getContent();
        this.updateTime = notice.getUpdateTime();
        this.image = notice.getImages().get(0).getImage_url();
        this.type = notice.getType();
    }
}

