package com.halo.eventer.domain.monitoring_data.dto.monitoring;

import lombok.Getter;

@Getter
public class AlertLevelGetDto {
    private Long id;
    private int threshold;

    public AlertLevelGetDto(Long id, int threshold) {
        this.id = id;
        this.threshold = threshold;
    }
}
