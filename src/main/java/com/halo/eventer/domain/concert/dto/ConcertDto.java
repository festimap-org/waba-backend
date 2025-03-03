package com.halo.eventer.domain.concert.dto;

import com.halo.eventer.domain.concert.Concert;
import com.halo.eventer.domain.duration.dto.DurationGetDto;
import com.halo.eventer.domain.image.dto.ImageUpdateDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ConcertDto {
    private String thumbnail;

    private List<ImageUpdateDto> images;

    private DurationGetDto durationDto;

    public ConcertDto(Concert c) {

        this.durationDto = new DurationGetDto(c.getDuration());
        this.thumbnail = c.getThumbnail();
        this.images = c.getImages().stream().map(ImageUpdateDto::new).collect(Collectors.toList());
    }
}
