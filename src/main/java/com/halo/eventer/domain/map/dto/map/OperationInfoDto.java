package com.halo.eventer.domain.map.dto.map;

import com.halo.eventer.domain.map.enumtype.OperationTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OperationInfoDto {
    private String hours;
    private OperationTime type;
}
