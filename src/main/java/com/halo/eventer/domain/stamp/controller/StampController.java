package com.halo.eventer.domain.stamp.controller;

import com.halo.eventer.domain.stamp.dto.stamp.*;
import com.halo.eventer.domain.stamp.service.StampService;
import com.halo.eventer.domain.stamp.swagger.MissionListGetApi;
import com.halo.eventer.domain.stamp.swagger.MissionListSetApi;
import com.halo.eventer.domain.stamp.swagger.StampOnUpdateApi;
import com.halo.eventer.domain.stamp.swagger.StampUsersListGetApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "스탬프 투어")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stamp")
public class StampController {
  private final StampService stampService;

  /** 축제 id로 스탬프 생성 */
  @PostMapping
  public List<StampGetDto> registerStamp(@RequestParam("festivalId") Long festivalId) {
    return stampService.registerStamp(festivalId);
  }

  /** 축제 id로 스탬프 조회 */
  @GetMapping
  public List<StampGetDto> getStampList(@RequestParam("festivalId") Long festivalId) {
    return stampService.getStampByFestivalId(festivalId);
  }

  /** 스탬프 상태 변경 */
  @StampOnUpdateApi
  @PatchMapping
  public void updateStampOn(@RequestParam("stampId") Long stampId) {
    stampService.updateStampOn(stampId);
  }

  /** 스탬프 삭제 */
  @DeleteMapping
  public void deleteStamp(@RequestParam("stampId") Long stampId) {
    stampService.deleteStamp(stampId);
  }

  /** 미션 생성 */
  @MissionListSetApi
  @PostMapping("/mission")
  public void setMissionList(
          @RequestParam("stampId") Long stampId, @RequestBody MissionSetListDto dto) {
    stampService.createMission(stampId, dto);
  }

  /** 미션 리스트 조회 */
  @MissionListGetApi
  @GetMapping("/missions")
  public List<MissionSummaryGetDto> getMissionList(@RequestParam("stampId") Long stampId) {
    return stampService.getMissions(stampId);
  }

  /** 해당 스탬프 유저들 조회 */
  @StampUsersListGetApi
  @GetMapping("/users")
  public List<StampUsersGetDto> getStampUsers(@RequestParam("stampId") Long stampId) {
    return stampService.getStampUsers(stampId);
  }

  /** 스탬프 완료 기준 설정 */
  @PostMapping("/finishCnt")
  public void setFinishCnt(
      @RequestParam("stampId") Long stampId, @RequestParam("cnt") Integer cnt) {
    stampService.setFinishCnt(stampId, cnt);
  }
}
