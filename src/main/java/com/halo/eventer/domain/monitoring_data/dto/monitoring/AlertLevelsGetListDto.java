package com.halo.eventer.domain.monitoring_data.dto.monitoring;

import lombok.Getter;

import java.util.List;

@Getter
public class AlertLevelsGetListDto {
    private List<AlertLevelGetDto> alertLevels;

    public AlertLevelsGetListDto(List<AlertLevelGetDto> alertLevels) { this.alertLevels = alertLevels; }
}
