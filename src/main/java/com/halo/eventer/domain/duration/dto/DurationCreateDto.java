package com.halo.eventer.domain.duration.dto;

import com.halo.eventer.domain.duration.Duration;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
