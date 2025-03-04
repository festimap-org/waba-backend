package com.halo.eventer.domain.duration.dto;

import com.halo.eventer.domain.duration.Duration;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class DurationCreateDto {

    private LocalDate date;
    private Integer day;

    public DurationCreateDto(Duration duration) {
        this.date = duration.getDate();
        this.day = duration.getDay();
    }
}
