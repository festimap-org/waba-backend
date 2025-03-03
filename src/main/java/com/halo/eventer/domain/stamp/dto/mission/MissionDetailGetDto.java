package com.halo.eventer.domain.stamp.dto.mission;

import com.halo.eventer.domain.stamp.Mission;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MissionDetailGetDto {
  private Long boothId;
  private String title;
  private String content;
  private String place;
  private String time;
  private String clearedThumbnail;
  private String notClearedThumbnail;

  public MissionDetailGetDto(Mission mission) {
    this.boothId = mission.getBoothId();
    this.title = mission.getTitle();
    this.content = mission.getContent();
    this.place = mission.getPlace();
    this.time = mission.getTime();
    this.clearedThumbnail = mission.getClearedThumbnail();
    this.notClearedThumbnail = mission.getNotClearedThumbnail();
  }
}
