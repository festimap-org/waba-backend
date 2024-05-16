package com.halo.eventer.map.dto.map;


import com.halo.eventer.duration.dto.DurationDto;
import com.halo.eventer.map.Map;
import com.halo.eventer.map.enumtype.OperationTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class GetAllStoreResDto {

    private Long id;
    private String name;
    private double latitude; // 위도
    private double longitude; // 경도
    private String type;
    private List<DurationDto> durationDto;
    private OperationTime operationTime;

    public GetAllStoreResDto(Map map) {
        this.id = map.getId();
        this.name = map.getName();
        this.latitude = map.getLatitude();
        this.longitude = map.getLongitude();
        this.type = map.getMapCategory().getCategoryName();
        this.durationDto= map.getDurationMaps().stream().map(o->new DurationDto(o.getDuration())).collect(Collectors.toList());
        this.operationTime = map.getOperationType();
    }
}
