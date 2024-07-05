package com.halo.eventer.domain.notice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class NoticeInquireListDto {
    private List<NoticeInquireDto> noticeInquireListDto;

    public NoticeInquireListDto(List<NoticeInquireDto> noticeInquireListDto) {
        this.noticeInquireListDto = noticeInquireListDto;
    }
}
