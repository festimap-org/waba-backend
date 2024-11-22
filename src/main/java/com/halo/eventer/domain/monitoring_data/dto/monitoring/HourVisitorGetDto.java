package com.halo.eventer.domain.monitoring_data.dto.monitoring;

import lombok.Getter;

@Getter
public class HourVisitorGetDto {
    private int hour;
    private int count;

    public HourVisitorGetDto(int hour, int count) {
        this.hour = hour;
        this.count = count;
    }
}
