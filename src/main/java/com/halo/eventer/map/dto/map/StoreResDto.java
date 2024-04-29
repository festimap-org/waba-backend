package com.halo.eventer.map.dto.map;

import com.halo.eventer.map.dto.menu.MenuResDto;
import com.halo.eventer.map.Menu;
import com.halo.eventer.map.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class StoreResDto {

    private String name;

    private String summary;
    private String content;

    private String location;
    private double latitude; // 위도
    private double longitude; // 경도

    private Boolean isOperation;

    private String operationHours;

    private String thumbnail;

    private List<MenuResDto> menus;

    private String type;

    public StoreResDto(Map map) {
        this.name = map.getName();
        this.summary = map.getSummary();
        this.content = map.getContent();
        this.location = map.getLocation();
        this.isOperation = map.getIsOperation();
        this.operationHours = map.getOperationHours();
        this.thumbnail = map.getThumbnail();
        this.latitude = map.getLatitude();
        this.longitude = map.getLongitude();
        this.type = map.getMapCategory().getCategoryName();
    }

    public void setMenus(List<Menu> menus){
        this.menus = menus.stream().map(o->new MenuResDto(o)).collect(Collectors.toList());
    }
}
