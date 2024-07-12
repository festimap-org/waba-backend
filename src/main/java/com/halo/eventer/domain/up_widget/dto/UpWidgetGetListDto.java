package com.halo.eventer.domain.up_widget.dto;

import com.halo.eventer.domain.up_widget.UpWidget;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class UpWidgetGetListDto {
    List<UpWidgetGetDto> list;

    public UpWidgetGetListDto(List<UpWidget> list) {
        this.list = list.stream()
                .map(UpWidgetGetDto::new)
                .collect(Collectors.toList());
    }
}
