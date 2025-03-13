package com.halo.eventer.domain.widget.entity;

import com.halo.eventer.domain.widget.BaseWidget;
import com.halo.eventer.domain.widget.feature.DisplayOrderFeature;
import com.halo.eventer.domain.widget.feature.ImageFeature;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MIDDLE_BANNER")
public class MiddleWidget extends BaseWidget {

    @Embedded
    private ImageFeature imageFeature;

    @Embedded
    private DisplayOrderFeature orderFeature;
}
