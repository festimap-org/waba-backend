package com.halo.eventer.map.dto.map;


import com.halo.eventer.map.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAllStoreResDto {

    private Long id;
    private String name;
    private double latitude; // 위도
    private double longitude; // 경도

    public GetAllStoreResDto(Map map) {
        this.id = map.getId();
        this.name = map.getName();
        this.latitude = map.getLatitude();
        this.longitude = map.getLongitude();
    }
}
