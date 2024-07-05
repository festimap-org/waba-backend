package com.halo.eventer.domain.duration.controller;


import com.halo.eventer.domain.duration.dto.DurationCreateDto;
import com.halo.eventer.domain.duration.service.DurationService;
import com.halo.eventer.domain.duration.dto.DurationDto;
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

    @PostMapping("/{festivalId}")
    public String createDuration(@RequestBody List<DurationCreateDto> durationDto,
                                 @PathVariable("festivalId") Long festivalId){
        return durationService.createDuration(festivalId, durationDto);
    }

    @GetMapping("/{festivalId}")
    public List<DurationDto> getDurations(@PathVariable("festivalId") Long festivalId){
        return durationService.getDurations(festivalId);
    }
}
