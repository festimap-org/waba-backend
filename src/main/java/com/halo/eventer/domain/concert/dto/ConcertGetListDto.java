package com.halo.eventer.domain.concert.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ConcertGetListDto {
    private List<ConcertGetDto> concertGetListDto;

    public ConcertGetListDto(List<ConcertGetDto> concertGetListDto) {
        this.concertGetListDto = concertGetListDto;
    }
}
