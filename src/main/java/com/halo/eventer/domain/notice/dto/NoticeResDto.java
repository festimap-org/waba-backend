package com.halo.eventer.domain.notice.dto;

import com.halo.eventer.domain.image.dto.ImageDto;
import com.halo.eventer.domain.notice.Notice;
import com.halo.eventer.global.common.ArticleType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeDto {
    private String title;
    private String thumbnail;
    private String index;
    private String writer;
    private String content;
    private ArticleType type;
    private LocalDateTime createTime;

    private List<ImageDto> images;

    public NoticeDto(Notice notice) {
        this.title = notice.getTitle();
        this.thumbnail = notice.getThumbnail();
        this.content = notice.getContent();
        this.type = notice.getType();
        this.index = notice.getTag();
        this.writer = notice.getWriter();
        this.images = notice.getImages().stream().map(ImageDto::from).collect(Collectors.toList());
        this.createTime = notice.getUpdateTime();
    }
}
