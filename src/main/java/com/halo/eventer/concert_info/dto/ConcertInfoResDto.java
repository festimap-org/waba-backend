package com.halo.eventer.concert_info.dto;


import com.halo.eventer.concert_info.ConcertInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcertInfoResDto {
    private Long concertInfoId;
    private String name;
    private String summary;

    public ConcertInfoResDto(ConcertInfo concertInfo) {
        this.concertInfoId = concertInfo.getId();
        this.name = concertInfo.getName();
        this.summary = concertInfo.getSummary();
    }
}
