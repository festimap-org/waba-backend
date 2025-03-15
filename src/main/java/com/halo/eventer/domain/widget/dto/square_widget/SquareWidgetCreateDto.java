package com.halo.eventer.domain.widget.dto.square_widget;


import com.halo.eventer.domain.widget.entity.SquareWidget;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SquareWidgetCreateDto {
  private String name;
  private String description;
  private String image;
  private String url;

  @Builder
  private SquareWidgetCreateDto(String name, String description, String image, String url) {
    this.name = name;
    this.description = description;
    this.image = image;
    this.url = url;
  }

  public static SquareWidgetCreateDto from(SquareWidget squareWidget) {
    return SquareWidgetCreateDto.builder()
            .name(squareWidget.getName())
            .url(squareWidget.getUrl())
            .description(squareWidget.getDescriptionFeature().getDescription())
            .image(squareWidget.getImageFeature().getImage())
            .build();
  }
}
