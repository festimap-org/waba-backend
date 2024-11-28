package com.halo.eventer.domain.monitoring_data.dto.monitoring;

import lombok.Getter;

@Getter
public class AlertLevelGetDto {
    private Long id;
    private int alertLevel;

    public AlertLevelGetDto(Long id, int alertLevel) {
        this.id = id;
        this.alertLevel = alertLevel;
    }
}
