package com.halo.eventer.domain.widget.controller;


import com.halo.eventer.domain.widget.dto.WidgetDto;
import com.halo.eventer.domain.widget.Widget;
import com.halo.eventer.domain.widget.service.WidgetService;
import com.halo.eventer.global.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/widget")
@Tag(name = "서브 메뉴 API")
public class WidgetController {

    private final WidgetService widgetService;

    @PostMapping("/{festivalId}")
    public SuccessResponse crateWidget(@PathVariable Long festivalId, @RequestBody WidgetDto widgetDto) {
        return SuccessResponse.of(widgetService.createWidget(festivalId,widgetDto));
    }

    @GetMapping("/{festivalId}")
    public List<Widget> getWidgets(@PathVariable Long festivalId) {
        return widgetService.getWidgets(festivalId);
    }

    @GetMapping
    public Widget getWidget(@RequestParam("widgetId") Long widgetId) {
        return widgetService.getWidget(widgetId);
    }

    @PatchMapping
    public String updateWidget(@RequestParam("widgetId") Long widgetId,
                               @RequestBody WidgetDto widgetDto){
        return widgetService.updateWidget(widgetId,widgetDto);
    }

    @DeleteMapping
    public String deleteWidget(@RequestParam("widgetId") Long widgetId) {
        return widgetService.deleteWidget(widgetId);
    }
}
