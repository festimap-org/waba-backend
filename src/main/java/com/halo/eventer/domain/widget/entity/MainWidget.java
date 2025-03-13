package com.halo.eventer.domain.widget.entity;

import com.halo.eventer.domain.widget.BaseWidget;
import com.halo.eventer.domain.widget.feature.ImageFeature;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MAIN")
@Getter
public class MainWidget extends BaseWidget {
    @Embedded
    private ImageFeature imageFeature;
}
