package com.halo.eventer.domain.duration.dto;


import com.halo.eventer.domain.duration.Duration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@ToString
public class DurationDto {

    private Long durationId;
    private LocalDate date;
    private Integer day;

    public DurationDto(Duration duration) {
        this.durationId = duration.getId();
        this.date = duration.getDate();
        this.day = duration.getDay();
    }
}

