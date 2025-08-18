package com.halo.eventer.domain.stamp.dto.stamp.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.halo.eventer.domain.stamp.dto.stamp.enums.AuthMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampTourBasicUpdateReqDto {
    @NotBlank
    private String newTitle;

    @NotNull
    private boolean activation;

    @NotNull
    private AuthMethod authMethod;

    @NotBlank
    private String boothPassword;
}
