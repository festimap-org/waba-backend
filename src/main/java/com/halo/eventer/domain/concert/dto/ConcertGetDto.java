package com.halo.eventer.domain.concert.dto;

import com.halo.eventer.domain.concert.Concert;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ConcertGetDto {
    private Long concertId;
    private String thumbnail;
    private Integer day;

    public ConcertGetDto(Concert c) {
        this.concertId = c.getId();
        this.thumbnail = c.getThumbnail();
        this.day = c.getDuration().getDay();
    }

    public static List<ConcertGetDto> fromConcertList(List<Concert> concerts) {
        return concerts.stream()
                .map(ConcertGetDto::new)
                .collect(Collectors.toList());
    }
}
