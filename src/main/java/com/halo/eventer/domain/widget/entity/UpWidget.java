package com.halo.eventer.domain.widget.entity;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.widget.BaseWidget;
import com.halo.eventer.domain.widget.dto.up_widget.UpWidgetCreateDto;
import com.halo.eventer.domain.widget.feature.PeriodFeature;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("UP")
@Getter
@NoArgsConstructor
@Table(indexes = {
                @Index(name = "idx_festival_period", columnList = "festival_id, period_start, period_end")
        }
)

public class UpWidget extends BaseWidget {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "periodStart", column = @Column(name = "period_start", nullable = false)),
            @AttributeOverride(name = "periodEnd", column = @Column(name = "period_end", nullable = false))
    })
    private PeriodFeature periodFeature;

    @Builder
    private UpWidget(Festival festival, String name, String url,
                    LocalDateTime periodStart, LocalDateTime periodEnd) {
        super(festival, name, url);
        this.periodFeature = PeriodFeature.of(periodStart, periodEnd);
    }

    public static UpWidget of(Festival festival, String name, String url,
                              LocalDateTime periodStart, LocalDateTime periodEnd){
        return UpWidget.builder()
                .festival(festival)
                .name(name)
                .url(url)
                .periodStart(periodStart)
                .periodEnd(periodEnd)
                .build();
    }

    public static UpWidget from(Festival festival, UpWidgetCreateDto upWidgetCreateDto){
        return UpWidget.builder()
                .festival(festival)
                .name(upWidgetCreateDto.getName())
                .url(upWidgetCreateDto.getUrl())
                .periodStart(upWidgetCreateDto.getPeriodStart())
                .periodEnd(upWidgetCreateDto.getPeriodEnd())
                .build();
    }

    public void updateUpWidget(UpWidgetCreateDto upWidgetCreateDto){
        updateBaseField(upWidgetCreateDto.getName(), upWidgetCreateDto.getUrl());

        this.periodFeature = PeriodFeature.of(upWidgetCreateDto.getPeriodStart(), upWidgetCreateDto.getPeriodEnd());
    }
}
