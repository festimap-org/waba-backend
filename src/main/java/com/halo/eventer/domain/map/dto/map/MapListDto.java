package com.halo.eventer.domain.map.dto.map;

import com.halo.eventer.domain.map.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class MapListDto {
    private Long mapId;
    private String mapName;

    public MapListDto(Map map) {
        this.mapId = map.getId();
        this.mapName = map.getName();
    }
}
