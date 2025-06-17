package com.halo.eventer.domain.duration.dto;

import java.time.LocalDate;

import com.halo.eventer.domain.duration.Duration;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DurationCreateDto {

    private LocalDate date;
    private Integer dayNumber;

    @Builder
    public DurationCreateDto(LocalDate date, Integer day) {
        this.date = date;
        this.dayNumber = day;
    }
}
