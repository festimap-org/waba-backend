package com.halo.eventer.domain.widget.feature;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
public class DisplayOrderFeature {

    @Column(name = "display_order")
    private Integer displayOrder;  // bannerRank 같은 필드

    public DisplayOrderFeature(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
