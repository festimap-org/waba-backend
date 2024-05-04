package com.halo.eventer.map;



import com.halo.eventer.duration_map.DurationMap;
import com.halo.eventer.map.dto.map.MapCreateDto;
import com.halo.eventer.map.enumtype.OperationTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String summary;

    @Column(columnDefinition = "varchar(2000)")
    private String content;

    private String location;

    private double latitude; // 위도
    private double longitude; // 경도

    private String operationHours;

    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private OperationTime operationType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mapCategory_id")
    private MapCategory mapCategory;

    @OneToMany(mappedBy = "map", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "map",fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    private List<DurationMap> durationMaps = new ArrayList<>();

    @Builder
    public Map(MapCreateDto mapCreateDto, OperationTime operationType) {
        this.location = mapCreateDto.getLocation();
        this.content = mapCreateDto.getContent();
        this.name = mapCreateDto.getName();
        this.summary = mapCreateDto.getSummary();
        this.latitude = mapCreateDto.getLatitude();
        this.longitude = mapCreateDto.getLongitude();
        this.operationHours = mapCreateDto.getOperationHours();
        this.thumbnail = mapCreateDto.getThumbnail();
        this.operationType = operationType;
    }

    public void setMap(MapCreateDto mapCreateDto) {
        this.location = mapCreateDto.getLocation();
        this.content = mapCreateDto.getContent();
        this.name = mapCreateDto.getName();
        this.summary = mapCreateDto.getSummary();
        this.latitude = mapCreateDto.getLatitude();
        this.longitude = mapCreateDto.getLongitude();
        this.operationHours = mapCreateDto.getOperationHours();
        this.thumbnail = mapCreateDto.getThumbnail();
    }

    public void setMapCategory(MapCategory mapCategory){
        this.mapCategory = mapCategory;
    }



}
