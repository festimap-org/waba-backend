package com.halo.eventer.domain.stamp.dto.stamp.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.halo.eventer.domain.stamp.dto.stamp.enums.ButtonAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ButtonReqDto {
    @NotNull
    private int sequenceIndex;

    @NotEmpty
    private String iconImg;

    @NotEmpty
    private String content;

    @NotNull
    private ButtonAction action;

    private String targetUrl;
}
