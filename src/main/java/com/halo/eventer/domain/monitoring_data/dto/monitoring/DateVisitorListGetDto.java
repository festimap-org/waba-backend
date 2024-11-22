package com.halo.eventer.domain.monitoring_data.dto.monitoring;

import lombok.Getter;

import java.util.List;

@Getter
public class DateVisitorListGetDto {
    private int maxCapacity;
    private int maxCount;

    private List<DateVisitorGetDto> dateVisitorGetDtos;

    public DateVisitorListGetDto(int maxCapacity, int maxCount, List<DateVisitorGetDto> dateVisitorGetDtos) {
        this.dateVisitorGetDtos = dateVisitorGetDtos;
        this.maxCapacity = maxCapacity;
        this.maxCount = maxCount;
    }
}
