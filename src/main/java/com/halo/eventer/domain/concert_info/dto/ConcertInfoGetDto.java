package com.halo.eventer.domain.concert_info.dto;


import com.halo.eventer.domain.concert_info.ConcertInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ConcertInfoGetDto {
    private Long concertInfoId;
    private String name;
    private String summary;

    public ConcertInfoGetDto(ConcertInfo concertInfo) {
        this.concertInfoId = concertInfo.getId();
        this.name = concertInfo.getName();
        this.summary = concertInfo.getSummary();
    }

    public static List<ConcertInfoGetDto> fromConcertInfoList(List<ConcertInfo> concertInfoList) {
        return concertInfoList.stream()
                .map(ConcertInfoGetDto::new)
                .collect(Collectors.toList());
    }
}
