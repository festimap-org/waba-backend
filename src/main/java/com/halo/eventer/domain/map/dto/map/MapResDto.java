package com.halo.eventer.domain.map.dto.map;

import com.halo.eventer.domain.duration.dto.DurationResDto;
import com.halo.eventer.domain.duration.DurationMap;
import com.halo.eventer.domain.map.Map;
import com.halo.eventer.domain.map.Menu;
import com.halo.eventer.domain.map.dto.menu.MenuResDto;
import com.halo.eventer.domain.map.enumtype.OperationTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String icon;

    private String buttonName;
    private String url;
    private String buttonImage;

    private List<DurationResDto> durations = new ArrayList<>();

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
        map.getDurationMaps().stream().map(DurationMap::getDuration).forEach(o->durations.add(new DurationResDto(o)));
        this.icon = map.getIcon();
        this.buttonName = map.getButtonName();
        this.buttonImage = map.getButtonImage();
        this.url = map.getUrl();
    }

    public void setMenus(List<Menu> menus){
        this.menus = menus.stream().map(o->new MenuResDto(o)).collect(Collectors.toList());
    }
}
