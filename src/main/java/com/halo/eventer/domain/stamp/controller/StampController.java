package com.halo.eventer.domain.stamp.controller;

import com.halo.eventer.domain.stamp.dto.*;
import com.halo.eventer.domain.stamp.service.StampService;
import com.halo.eventer.domain.stamp.swagger.StampOnUpdateApi;
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
    public String registerStamp(@RequestParam("festivalId") Long festivalId) {
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
}
