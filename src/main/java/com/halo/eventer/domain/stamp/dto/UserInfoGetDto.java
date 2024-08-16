package com.halo.eventer.domain.stamp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoGetDto {
    private String name;
    private String phone;
    private int participantCount;
}
