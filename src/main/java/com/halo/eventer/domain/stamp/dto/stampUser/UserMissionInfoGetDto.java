package com.halo.eventer.domain.stamp.dto.stampUser;

import lombok.Getter;

@Getter
public class UserMissionInfoGetDto {
  private Long userMissionId;
  private Long missionId;
  private boolean clear;

  public UserMissionInfoGetDto(Long userMissionId, Long missionId, boolean clear) {
    this.userMissionId = userMissionId;
    this.missionId = missionId;
    this.clear = clear;
  }
}
