package com.halo.eventer.domain.widget.entity;

import com.halo.eventer.domain.widget.BaseWidget;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DOWN")
public class DownWidget extends BaseWidget {
}
