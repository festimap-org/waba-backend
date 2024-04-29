package com.halo.eventer.concert.dto;

import com.halo.eventer.concert.Concert;
import com.halo.eventer.image.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class ConcertResDto {


    private String tag;


    private String name;

    private String summary;

    private String content;

    private String location;

    private double latitude; // 위도
    private double longitude; // 경도

    private Boolean isOperation;

    private String operationHours;
    private String thumbnail;

    private List<String> images;

    public ConcertResDto(Concert c) {
        this.tag = c.getTag();
        this.name = c.getName();
        this.summary = c.getSummary();
        this.content = c.getContent();
        this.location = c.getLocation();
        this.isOperation = c.getIsOperation();
        this.operationHours = c.getOperationHours();
        this.thumbnail = c.getThumbnail();
        this.latitude =  c.getLatitude();
        this.longitude = c.getLongitude();
    }

    public void setImages(List<Image> images){
        this.images = images.stream().map(o->o.getImage_url()).collect(Collectors.toList());
    }
}
