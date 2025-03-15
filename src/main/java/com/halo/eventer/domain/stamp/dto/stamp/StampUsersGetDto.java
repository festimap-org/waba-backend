package com.halo.eventer.domain.stamp.dto.stamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StampUsersGetDto {
  private String uuid;
  private String name;
  private String phone;
  private boolean finished;
  private int participantCount;

  public StampUsersGetDto(
      String uuid, String name, String phone, boolean finished, int participantCount) {
    this.uuid = uuid;
    this.name = name;
    this.phone = phone;
    this.finished = finished;
    this.participantCount = participantCount;
  }
}
