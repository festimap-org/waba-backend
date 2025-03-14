package com.halo.eventer.domain.content.dto;

import com.halo.eventer.domain.image.dto.ImageUpdateDto;
import com.halo.eventer.domain.content.Content;
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

    private List<ImageUpdateDto> images;

    public NoticeDto(Content content) {
        this.title = content.getTitle();
        this.thumbnail = content.getThumbnail();
        this.content = content.getContent();
        this.type = content.getType();
        this.index = content.getTag();
        this.writer = content.getWriter();
        this.images = content.getImages().stream().map(ImageUpdateDto::new).collect(Collectors.toList());
        this.createTime = content.getUpdateTime();
    }
}
