package com.halo.eventer.domain.duration.controller;


import com.halo.eventer.domain.duration.dto.DurationCreateDto;
import com.halo.eventer.domain.duration.dto.DurationCreateListDto;
import com.halo.eventer.domain.duration.dto.DurationGetListDto;
import com.halo.eventer.domain.duration.service.DurationService;
import com.halo.eventer.domain.duration.dto.DurationGetDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/duration")
@Tag(name = "축제 기간")
public class DurationController {
    private final DurationService durationService;

    /** 축제 기간 등록 */
    @PostMapping("/{festivalId}")
    public String createDuration(@RequestBody DurationCreateListDto durationCreateListDto, @PathVariable("festivalId") Long festivalId){
        return durationService.createDuration(festivalId, durationCreateListDto);
    }

    /** 축제 기간 조회 */
    @GetMapping("/{festivalId}")
    public DurationGetListDto getDurations(@PathVariable("festivalId") Long festivalId){
        return durationService.getDurations(festivalId);
    }
}
