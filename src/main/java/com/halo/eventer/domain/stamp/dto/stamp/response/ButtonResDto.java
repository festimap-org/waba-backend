package com.halo.eventer.domain.stamp.dto.stamp.response;

import java.util.Comparator;
import java.util.List;

import com.halo.eventer.domain.stamp.Button;
import com.halo.eventer.domain.stamp.dto.stamp.enums.ButtonAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ButtonResDto {
    private int sequenceIndex;
    private String iconImg;
    private String content;
    private ButtonAction action;
    private String targetUrl;

    public static ButtonResDto from(Button button) {
        return new ButtonResDto(
                button.getSequenceIndex(),
                button.getIconImg(),
                button.getContent(),
                button.getAction(),
                button.getTargetUrl());
    }

    public static List<ButtonResDto> fromEntities(List<Button> buttons) {
        if (buttons == null || buttons.isEmpty()) return List.of();
        return buttons.stream()
                .sorted(Comparator.comparingInt(Button::getSequenceIndex)) // 오름차순
                .map(ButtonResDto::from)
                .toList();
    }
}
