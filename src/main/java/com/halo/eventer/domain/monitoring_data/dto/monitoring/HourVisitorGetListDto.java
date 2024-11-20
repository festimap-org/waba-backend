package com.halo.eventer.domain.monitoring_data.dto.monitoring;

import lombok.Getter;

import java.util.List;

@Getter
public class HourVisitorGetListDto {
    private List<HourVisitorGetDto> hourVisitorGetDtos;

    public HourVisitorGetListDto(List<HourVisitorGetDto> hourVisitorGetDtos) { this.hourVisitorGetDtos = hourVisitorGetDtos; }
}
