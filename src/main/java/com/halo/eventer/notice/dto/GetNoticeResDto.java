package com.halo.eventer.notice.dto;

import com.halo.eventer.common.ArticleType;
import com.halo.eventer.image.Image;
import com.halo.eventer.image.dto.ImageUpdateDto;
import com.halo.eventer.notice.Notice;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class GetNoticeResDto {

    private String title;
    private String thumbnail;
    private String index;
    private String writer;
    private String content;
    private ArticleType type;

    private List<ImageUpdateDto> images;

    public GetNoticeResDto(Notice notice) {
        this.title = notice.getTitle();
        this.thumbnail = notice.getThumbnail();
        this.content = notice.getContent();
        this.type = notice.getType();
        this.index = notice.getTag();
        this.writer = notice.getWriter();
        this.images = notice.getImages().stream().map(ImageUpdateDto::new).collect(Collectors.toList());
    }
}

