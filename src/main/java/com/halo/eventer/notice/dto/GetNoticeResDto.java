package com.halo.eventer.notice.dto;

import com.halo.eventer.common.ArticleType;
import com.halo.eventer.image.Image;
import com.halo.eventer.notice.Notice;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class GetNoticeResDto {

    private String title;
    private String thumbnail;
    private String subtitle;
    private String content;
    private ArticleType type;

    private List<String> images;

    public GetNoticeResDto(Notice notice) {
        this.title = notice.getTitle();
        this.thumbnail = notice.getThumbnail();
        this.subtitle = notice.getSubtitle();
        this.content = notice.getContent();
        this.type = notice.getType();
    }

    public void setImages(List<Image> images){
        this.images = images.stream().map(o->o.getImage_url()).collect(Collectors.toList());
    }
}

