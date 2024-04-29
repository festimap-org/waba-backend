package com.halo.eventer.map.dto.map;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreCreateResDto {
    private Long mapId;

    public StoreCreateResDto(Long storeId) {
        this.mapId = storeId;
    }
}
