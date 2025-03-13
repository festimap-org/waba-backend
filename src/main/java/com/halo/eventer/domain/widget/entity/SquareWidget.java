package com.halo.eventer.domain.widget.entity;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.widget.BaseWidget;
import com.halo.eventer.domain.widget.feature.DescriptionFeature;
import com.halo.eventer.domain.widget.feature.ImageFeature;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Square")
@NoArgsConstructor
public class SquareWidget extends BaseWidget {

    @Embedded
    private ImageFeature imageFeature;

    @Embedded
    private DescriptionFeature descriptionFeature;

    public SquareWidget(Festival festival, String name, String url, String image) {
        super(festival, name, url);
        this.imageFeature = ImageFeature.of(image);
    }
}
