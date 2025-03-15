package com.halo.eventer.domain.widget.feature;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
public class ImageFeature {

    @Column(name = "image")
    private String image;

    private ImageFeature(String image) {
        this.image = image;
    }

    public static ImageFeature of(String image) {
        return new ImageFeature(image);
    }
}
