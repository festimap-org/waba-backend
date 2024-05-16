package com.halo.eventer.concert.dto;

import com.halo.eventer.concert.Concert;
import com.halo.eventer.duration.dto.DurationDto;
import com.halo.eventer.image.dto.ImageUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class ConcertResDto {


    private String thumbnail;

    private List<ImageUpdateDto> images;

    private DurationDto durationDto;

    public ConcertResDto(Concert c) {

        this.durationDto = new DurationDto(c.getDuration());
        this.thumbnail = c.getThumbnail();
        this.images = c.getImages().stream().map(ImageUpdateDto::new).collect(Collectors.toList());
    }

}
