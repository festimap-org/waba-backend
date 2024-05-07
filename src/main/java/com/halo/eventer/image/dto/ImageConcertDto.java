package com.halo.eventer.image.dto;


import com.halo.eventer.image.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageConcertDto {
    private Long imageId;
    private String image;

    public ImageConcertDto(Image image) {
        this.imageId = image.getId();
        this.image = image.getImage_url();
    }
}
