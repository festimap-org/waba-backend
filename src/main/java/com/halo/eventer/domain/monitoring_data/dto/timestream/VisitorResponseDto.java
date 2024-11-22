package com.halo.eventer.domain.monitoring_data.dto.timestream;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VisitorResponseDto {
    private String timestamp;
    private VisitorCountDto cumulativeVisitors;
    private VisitorCountDto realTimeVisitors;

}
