package com.halo.eventer.domain.stamp.dto.stamp.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.halo.eventer.domain.stamp.dto.stamp.enums.MediaSpec;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampTourParticipateGuidePageReqDto {
    private long guideId;

    @NotBlank
    private String title;

    @NotNull
    private MediaSpec guideMediaSpec;

    private String mediaUrl;

    @NotBlank
    private String summary;

    @NotBlank
    private String details;

    @NotBlank
    private String additional;
}
