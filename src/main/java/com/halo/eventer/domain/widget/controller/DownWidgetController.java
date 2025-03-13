package com.halo.eventer.domain.widget.controller;

import com.halo.eventer.domain.widget.dto.WidgetOrderUpdateRequest;
import com.halo.eventer.domain.widget.dto.down_widget.DownWidgetCreateDto;
import com.halo.eventer.domain.widget.dto.down_widget.DownWidgetResDto;
import com.halo.eventer.domain.widget.service.DownWidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/downWidget")
public class DownWidgetController {

  private final DownWidgetService downWidgetService;

  @PostMapping()
  public DownWidgetResDto create(@RequestParam Long festivalId,
                                 @RequestBody DownWidgetCreateDto downWidgetCreateDto) {
    return downWidgetService.create(festivalId,downWidgetCreateDto);
  }

  @GetMapping()
  public List<DownWidgetResDto> getDownWidgets(@RequestParam("festivalId") Long festivalId) {
    return downWidgetService.getAllDownWidgets(festivalId);
  }

  @PatchMapping()
  public DownWidgetResDto update(@RequestParam Long id,
                                 @RequestBody DownWidgetCreateDto downWidgetCreateDto) {
    return downWidgetService.update(id,downWidgetCreateDto);
  }

  @DeleteMapping()
  public void delete(@RequestParam Long id){
    downWidgetService.delete(id);
  }

  @PatchMapping("/displayOrder")
  public List<DownWidgetResDto> updateDisplayOrder(@RequestParam long festivalId,
                                                   @RequestBody List<WidgetOrderUpdateRequest> orderRequests){
    return downWidgetService.updateDisplayOrder(festivalId, orderRequests);
  }
}
