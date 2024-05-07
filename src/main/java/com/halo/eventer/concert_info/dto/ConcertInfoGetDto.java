package com.halo.eventer.concert_info.dto;

import com.halo.eventer.concert_info.ConcertInfo;
import com.halo.eventer.concert_info.ConcertInfoType;
import com.halo.eventer.image.dto.ImageConcertDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class ConcertInfoGetDto {

    private Long id;

    private String name;
    private ConcertInfoType type;
    private List<ImageConcertDto> images;

    public ConcertInfoGetDto(ConcertInfo concertInfo) {
        this.id = concertInfo.getId();
        this.name = concertInfo.getName();
        this.type = concertInfo.getType();
        this.images = concertInfo.getImages().stream().map(ImageConcertDto::new).collect(Collectors.toList());
    }
}
