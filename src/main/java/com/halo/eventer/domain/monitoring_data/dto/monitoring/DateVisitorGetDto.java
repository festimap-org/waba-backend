package com.halo.eventer.domain.monitoring_data.dto.monitoring;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DateVisitorGetDto {
    private LocalDate date;
    private int count;

    public DateVisitorGetDto(LocalDate date, int count) {
        this.date = date;
        this.count = count;
    }
}
