package com.halo.eventer.notice.dto;

import com.halo.eventer.notice.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class GetAllNoticeResDto {

    private Long id;
    private String title;
    private String subtitle;
    private String thumbnail;
    private boolean picked;
    private LocalDateTime time;

    public GetAllNoticeResDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.thumbnail = notice.getThumbnail();
        this.time = notice.getUpdateTime();
        this.subtitle = notice.getSubtitle();
        this.picked = notice.isPicked();
    }
}
