package com.halo.eventer.domain.monitoring_data.dto.timestream;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VisitorCountDto {
    private Integer total;
    private GenderCountDto genderCount;
    public AgeCountDto ageCount;

}
