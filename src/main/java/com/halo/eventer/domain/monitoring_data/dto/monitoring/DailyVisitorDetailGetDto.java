package com.halo.eventer.domain.monitoring_data.dto.monitoring;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public class DailyVisitorDetailGetDto {
    private int cumulative;
    private int maxCapacity;
    private JsonNode age;
    private JsonNode gender;

    public DailyVisitorDetailGetDto(int cumulative, int maxCapacity, JsonNode age, JsonNode gender) {
        this.cumulative = cumulative;
        this.maxCapacity = maxCapacity;
        this.age = age;
        this.gender = gender;
    }
}
