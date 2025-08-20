package com.halo.eventer.domain.parking.dto.parking_notice;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingNoticeReqDto {
    private String notice;
    private String content;

    public ParkingNoticeReqDto(String notice, String content) {
        this.notice = notice;
        this.content = content;
    }
}
