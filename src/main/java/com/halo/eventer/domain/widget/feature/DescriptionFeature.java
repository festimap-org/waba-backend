package com.halo.eventer.domain.widget.feature;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
public class DescriptionFeature {

    @Column(name = "description")
    private String description;

    public DescriptionFeature(String description) {
        this.description = description;
    }
}
