package com.halo.eventer.domain.widget.controller;


import com.halo.eventer.domain.widget.dto.WidgetDto;
import com.halo.eventer.domain.widget.Widget;
import com.halo.eventer.domain.widget.dto.WidgetGetListDto;
import com.halo.eventer.domain.widget.service.WidgetService;
import com.halo.eventer.domain.widget.swagger.WidgetGetApi;
import com.halo.eventer.domain.widget.swagger.WidgetGetListApi;
import com.halo.eventer.global.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/widget")
@Tag(name = "서브 메뉴 API")
public class WidgetController {

    private final WidgetService widgetService;

    /** 위젯 생성 */
    @PostMapping("/{festivalId}")
    public SuccessResponse crateWidget(@PathVariable Long festivalId, @RequestBody WidgetDto widgetDto) {
        return SuccessResponse.of(widgetService.createWidget(festivalId,widgetDto));
    }

    /** 위젯 전체 조회 */
    @WidgetGetListApi
    @GetMapping("/{festivalId}")
    public WidgetGetListDto getWidgets(@PathVariable Long festivalId) {
        return widgetService.getWidgets(festivalId);
    }

    /** 단일 위젯 조회 */
    @WidgetGetApi
    @GetMapping
    public Widget getWidget(@RequestParam("widgetId") Long widgetId) {
        return widgetService.getWidget(widgetId);
    }

    /** 위젯 업데이트 */
    @PatchMapping
    public String updateWidget(@RequestParam("widgetId") Long widgetId, @RequestBody WidgetDto widgetDto){
        return widgetService.updateWidget(widgetId,widgetDto);
    }

    /** 위젯 삭제 */
    @DeleteMapping
    public String deleteWidget(@RequestParam("widgetId") Long widgetId) {
        return widgetService.deleteWidget(widgetId);
    }
}
