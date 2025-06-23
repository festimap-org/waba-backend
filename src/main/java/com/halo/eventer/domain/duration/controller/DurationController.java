package com.halo.eventer.domain.duration.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.duration.dto.DurationCreateDto;
import com.halo.eventer.domain.duration.dto.DurationResDto;
import com.halo.eventer.domain.duration.service.DurationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/duration")
public class DurationController {
    private final DurationService durationService;

    @PostMapping()
    public void createDurations(
            @RequestParam("festivalId") Long festivalId, @RequestBody List<DurationCreateDto> durationCreateDtos) {
        durationService.createDurations(festivalId, durationCreateDtos);
    }

    @GetMapping()
    public List<DurationResDto> getDurations(@RequestParam("festivalId") Long festivalId) {
        return durationService.getDurations(festivalId);
    }
}
