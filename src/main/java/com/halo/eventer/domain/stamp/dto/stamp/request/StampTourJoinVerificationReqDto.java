package com.halo.eventer.domain.stamp.dto.stamp.request;

import jakarta.validation.constraints.NotNull;

import com.halo.eventer.domain.stamp.dto.stamp.enums.JoinVerificationMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampTourJoinVerificationReqDto {
    @NotNull
    private JoinVerificationMethod joinVerificationMethod;
}
