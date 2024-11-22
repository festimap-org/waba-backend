package com.halo.eventer.domain.monitoring_data.dto.timestream;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AgeCountDto {
    private Integer teenager;
    private Integer twenties_and_thirties;
    private Integer forties_and_fifties;
    private Integer sixties_and_above;

}
