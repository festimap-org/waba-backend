package com.halo.eventer.image.dto;


import com.halo.eventer.image.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageUpdateDto {
    private Long imageId;
    private String image;

    public ImageUpdateDto(Image image) {
        this.imageId = image.getId();
        this.image = image.getImage_url();
    }
}
