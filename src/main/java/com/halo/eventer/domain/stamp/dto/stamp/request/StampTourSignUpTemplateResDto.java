package com.halo.eventer.domain.stamp.dto.stamp.request;

import com.halo.eventer.domain.stamp.dto.stamp.enums.JoinVerificationMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampTourSignUpTemplateResDto {
    private JoinVerificationMethod joinVerificationMethod;

    public static StampTourSignUpTemplateResDto from(JoinVerificationMethod method) {
        return new StampTourSignUpTemplateResDto(method);
    }
}
