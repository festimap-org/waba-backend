package com.halo.eventer.domain.stamp.dto.stampUser;

import com.halo.eventer.domain.stamp.StampUser;
import com.halo.eventer.domain.stamp.dto.stamp.StampUsersGetDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class StampUserGetDto {

  private String uuid;
  private boolean finished;
  private int participantCount;
  private List<UserMissionInfoGetDto> userMissionInfoGetDtos;

  public static StampUserGetDto from(StampUser stampUser, List<UserMissionInfoGetDto> userMissionInfoGetDtos){
    return StampUserGetDto.builder()
            .uuid(stampUser.getUuid())
            .participantCount(stampUser.getParticipantCount())
            .finished(stampUser.isFinished())
            .userMissionInfoGetDtos(userMissionInfoGetDtos)
            .build();
  }
}
