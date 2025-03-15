package com.halo.eventer.domain.stamp.dto.stamp;

import lombok.Getter;

@Getter
public class MissionSummaryGetDto {
  private Long missionId;
  private String title;
  private String clearedThumbnail;
  private String notClearedThumbnail;

  public MissionSummaryGetDto() {}

  public MissionSummaryGetDto(
      Long missionId, String title, String clearedThumbnail, String notClearedThumbnail) {
    this.missionId = missionId;
    this.title = title;
    this.clearedThumbnail = clearedThumbnail;
    this.notClearedThumbnail = notClearedThumbnail;
  }
}
