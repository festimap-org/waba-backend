package com.halo.eventer.domain.stamp.controller;

import com.halo.eventer.domain.stamp.dto.stamp.MissionSummaryGetListDto;
import com.halo.eventer.domain.stamp.dto.stamp.MissionSetListDto;
import com.halo.eventer.domain.stamp.dto.stamp.StampGetListDto;
import com.halo.eventer.domain.stamp.dto.stamp.StampUsersGetListDto;
import com.halo.eventer.domain.stamp.service.StampService;
import com.halo.eventer.domain.stamp.swagger.MissionListGetApi;
import com.halo.eventer.domain.stamp.swagger.MissionListSetApi;
import com.halo.eventer.domain.stamp.swagger.StampOnUpdateApi;
import com.halo.eventer.domain.stamp.swagger.StampUsersListGetApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "스탬프 투어")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stamp")
public class StampController {
    private final StampService stampService;

    /** 축제 id로 스탬프 생성 */
    @PostMapping
    public StampGetListDto registerStamp(@RequestParam("festivalId") Long festivalId) {
        return stampService.registerStamp(festivalId);
    }

    /** 축제 id로 스탬프 조회 */
    @GetMapping
    public StampGetListDto getStampList(@RequestParam("festivalId") Long festivalId) {
        return stampService.getStampByFestivalId(festivalId);
    }

    /** 스탬프 상태 변경 */
    @StampOnUpdateApi
    @PatchMapping
    public String updateStampOn(@RequestParam("stampId") Long stampId) {
        return stampService.updateStampOn(stampId);
    }

    /** 스탬프 삭제 */
    @DeleteMapping
    public String deleteStamp(@RequestParam("stampId") Long stampId) {
        return stampService.deleteStamp(stampId);
    }

    /** 미션 생성 */
    @MissionListSetApi
    @PostMapping("/mission")
    public String setMissionList(@RequestParam("stampId") Long stampId, @RequestBody MissionSetListDto dto) {
        return stampService.setMission(stampId, dto);
    }

    /** 미션 리스트 조회 */
    @MissionListGetApi
    @GetMapping("/missions")
    public MissionSummaryGetListDto getMissionList(@RequestParam("stampId") Long stampId) {
        return stampService.getMissions(stampId);
    }

    /** 해당 스탬프 유저들 조회 */
    @StampUsersListGetApi
    @GetMapping("/users")
    public StampUsersGetListDto getStampUsers(@RequestParam("stampId") Long stampId) {
        return stampService.getStampUsers(stampId);
    }
}
