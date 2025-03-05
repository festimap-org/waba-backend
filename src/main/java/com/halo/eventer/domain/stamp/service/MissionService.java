package com.halo.eventer.domain.stamp.service;

import com.halo.eventer.domain.stamp.Mission;
import com.halo.eventer.domain.stamp.dto.mission.MissionUpdateDto;
import com.halo.eventer.domain.stamp.exception.MissionNotFoundException;
import com.halo.eventer.domain.stamp.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionService {
  private final MissionRepository missionRepository;

  /** 미션 단일 조회 */
  public Mission getMission(Long missionId) {
    return missionRepository
        .findById(missionId)
        .orElseThrow(() -> new MissionNotFoundException(missionId));
  }

  /** 미션 단일 수정 */
  @Transactional
  public String updateMission(Long missionId, MissionUpdateDto missionUpdateDto) {
    Mission mission = getMission(missionId);

    mission.setBoothId(missionUpdateDto.getBoothId());
    mission.setTitle(missionUpdateDto.getTitle());
    mission.setContent(missionUpdateDto.getContent());
    mission.setPlace(missionUpdateDto.getPlace());
    mission.setTime(missionUpdateDto.getTime());
    mission.setClearedThumbnail(missionUpdateDto.getClearedThumbnail());
    mission.setNotClearedThumbnail(missionUpdateDto.getNotClearedThumbnail());

    missionRepository.save(mission);
    return "미션 수정 완료";
  }
}
