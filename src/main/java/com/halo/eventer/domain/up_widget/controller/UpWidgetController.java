package com.halo.eventer.domain.up_widget.controller;

import com.halo.eventer.domain.up_widget.dto.UpWidgetCreateDto;
import com.halo.eventer.domain.up_widget.dto.UpWidgetGetDto;
import com.halo.eventer.domain.up_widget.dto.UpWidgetGetListDto;
import com.halo.eventer.domain.up_widget.service.UpWidgetService;
import com.halo.eventer.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upWidgets")
public class UpWidgetController {

    private final UpWidgetService upWidgetService;

    /**
     * 상단 팝업 생성
     * */
    @PostMapping()
    public SuccessCode createUpWidget(@RequestParam(name = "festivalId") Long festivalId, @RequestBody UpWidgetCreateDto upWidgetCreateDto) {
        return upWidgetService.createUpWidget(festivalId, upWidgetCreateDto);
    }

    /**
     * 상단 팝업 리스트 조회
     * */
    @GetMapping
    public UpWidgetGetListDto getUpWidgetList(@RequestParam(name = "festivalId") Long festivalId) {
        return new UpWidgetGetListDto(upWidgetService.getUpWidgetList(festivalId));
    }

    /**
     * 상단 팝업 리스트 단일 조회
     */
    @GetMapping("/{upWidgetId}")
    public UpWidgetGetDto getUpWidget(@PathVariable("upWidgetId") Long upWidgetId) {
        return new UpWidgetGetDto(upWidgetService.getUpWidget(upWidgetId));
    }

    /**
     * 날짜로 메인 페이지에서 조회
     * */
    @GetMapping("/datetime")
    public UpWidgetGetListDto getUpWidgetListByDateTime(@RequestParam("festivalId") Long festivalId, @RequestParam("dateTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTime) {
        return new UpWidgetGetListDto(upWidgetService.getUpWidgetListByDateTime(festivalId, dateTime));
    }
}
