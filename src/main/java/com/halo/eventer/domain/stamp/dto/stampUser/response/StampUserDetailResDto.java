package com.halo.eventer.domain.stamp.dto.stampUser.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StampUserDetailResDto {
    private Long userId;
    private String name;
    private String phone;
    private String uuid;
    private boolean tourFinished;
    private List<UserMissionStatusResDto> missions;
    private String extraText;
    private int participantCount;
}
