package com.halo.eventer.domain.widget.dto.square_widget;

import com.halo.eventer.domain.widget.entity.SquareWidget;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SquareWidgetResDto {
    private String name;
    private String description;
    private String icon;
    private String url;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Builder
    private SquareWidgetResDto(String name, String description, String icon,
                               String url, Integer displayOrder, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.url = url;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static SquareWidgetResDto from(SquareWidget squareWidget) {
        return SquareWidgetResDto.builder()
                .name(squareWidget.getName())
                .url(squareWidget.getUrl())
                .description(squareWidget.getDescriptionFeature().getDescription())
                .icon(squareWidget.getImageFeature().getImage())
                .displayOrder(squareWidget.getDisplayOrderFeature().getDisplayOrder())
                .createdAt(squareWidget.getCreatedAt())
                .updatedAt(squareWidget.getUpdatedAt())
                .build();
    }
}
