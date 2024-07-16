package com.halo.eventer.domain.down_widget.controller;


import com.halo.eventer.domain.down_widget.dto.DownWidgetCreateDto;
import com.halo.eventer.domain.down_widget.dto.DownWidgetGetListDto;
import com.halo.eventer.domain.down_widget.service.DownWidgetService;
import com.halo.eventer.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/downWidget")
public class DownWidgetController {

    private final DownWidgetService downWidgetService;

    /**
     * 값을 업데이트 or 삭제 -- >생성, 수정, 삭제 모두 이걸로 함
     * */
    @PostMapping()
    public SuccessCode updateDownWidget(@RequestBody DownWidgetCreateDto downWidgetCreateDto) {
        return downWidgetService.update(downWidgetCreateDto);
    }


    /**
     * 등록된 하단 위젯 모두 조회
     */
    @GetMapping()
    public DownWidgetGetListDto getDownWidgetList(@RequestParam("festivalId") Long festivalId) {
        return new DownWidgetGetListDto(downWidgetService.getAllDownWidget(festivalId));
    }
}
