package com.halo.eventer.domain.stamp.dto.stampUser.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampUserSignupReqDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;

    @Min(1)
    private int participantCount;

    @NotEmpty
    private String extraText;
}
