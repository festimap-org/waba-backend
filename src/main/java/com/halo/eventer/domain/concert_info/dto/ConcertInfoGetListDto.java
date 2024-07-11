package com.halo.eventer.domain.concert_info.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ConcertInfoGetListDto {
    private List<ConcertInfoGetDto> concertInfoGetDto;

    public ConcertInfoGetListDto(List<ConcertInfoGetDto> concertInfoGetDto) {
        this.concertInfoGetDto = concertInfoGetDto;
    }
}
