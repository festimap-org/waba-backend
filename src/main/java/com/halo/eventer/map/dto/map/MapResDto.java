package com.halo.eventer.map.dto.map;

import com.halo.eventer.duration.dto.DurationDto;
import com.halo.eventer.duration_map.DurationMap;
import com.halo.eventer.map.dto.menu.MenuResDto;
import com.halo.eventer.map.Menu;
import com.halo.eventer.map.Map;
import com.halo.eventer.map.enumtype.OperationTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MapResDto {

    private String name;

    private String summary;
    private String content;

    private String location;
    private double latitude; // 위도
    private double longitude; // 경도

    private String operationHours;
    private OperationTime operationTime;

    private String thumbnail;

    private List<MenuResDto> menus;

    private String type;

    private List<DurationDto> durations = new ArrayList<>();

    public MapResDto(Map map) {
        this.name = map.getName();
        this.summary = map.getSummary();
        this.content = map.getContent();
        this.location = map.getLocation();
        this.operationHours = map.getOperationHours();
        this.thumbnail = map.getThumbnail();
        this.latitude = map.getLatitude();
        this.longitude = map.getLongitude();
        this.type = map.getMapCategory().getCategoryName();
        this.operationTime = map.getOperationType();
        map.getDurationMaps().stream().map(DurationMap::getDuration).forEach(o->durations.add(new DurationDto(o)));
    }

    public void setMenus(List<Menu> menus){
        this.menus = menus.stream().map(o->new MenuResDto(o)).collect(Collectors.toList());
    }
}
