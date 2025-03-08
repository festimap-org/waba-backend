package com.halo.eventer.domain.festival.controller;

import com.halo.eventer.domain.festival.dto.*;
import com.halo.eventer.domain.festival.dto.FestivalConcertMenuDto;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.festival.swagger.*;
import com.halo.eventer.global.common.ImageDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/festival")
@Tag(name = "축제")
public class FestivalController {

  private final FestivalService festivalService;

  @FestivalCreateApi
  @PostMapping()
  public void createFestival(@RequestBody FestivalCreateDto festivalCreateDto) {
    festivalService.create(festivalCreateDto);
  }

  @GetFestivalApi
  @GetMapping("/{id}")
  public FestivalResDto getFestival(@PathVariable("id") Long id) {
    return festivalService.findById(id);
  }

  @GetMapping
  public List<FestivalListDto> getFestivals() {
    return festivalService.findAll();
  }

  @FestivalUpdateApi
  @PatchMapping("/{id}")
  public FestivalResDto updateFestival(@PathVariable("id") Long id,
                                       @RequestBody FestivalCreateDto festivalCreateDto) {
    return festivalService.update(id, festivalCreateDto);
  }

  @FestivalDeleteApi
  @DeleteMapping("/{id}")
  public void deleteFestival(@PathVariable("id") Long id) {
    festivalService.delete(id);
  }

  @FestivalColorAddApi
  @PostMapping("/{festivalId}/color")
  public void addColor(
      @PathVariable("festivalId") Long id, @RequestBody ColorDto colorDto) {
    festivalService.updateColor(id, colorDto);
  }

  @FestivalLogoAddApi
  @PostMapping("/{festivalId}/logo")
  public void addLogo(@PathVariable("festivalId") Long id, @RequestBody ImageDto imageDto) {
    festivalService.updateLogo(id, imageDto);
  }

  @PostMapping("/{festivalId}/main-menu")
  public void addMainMenu(
      @PathVariable("festivalId") Long id, @RequestBody MainMenuDto mainMenuDto) {
    festivalService.updateMainMenu(id, mainMenuDto);
  }

  @GetMapping("/{festivalId}/main-menu")
  public MainMenuDto getMainMenu(@PathVariable("festivalId") Long id) {
    return festivalService.getMainMenu(id);
  }

  @PostMapping("/{festivalId}/entry")
  public void addEntryInfo(
      @PathVariable("festivalId") Long festivalId,
      @RequestBody FestivalConcertMenuDto festivalConcertMenuDto) {
    festivalService.updateEntryInfo(festivalId, festivalConcertMenuDto);
  }

  @PostMapping("/{festivalId}/view")
  public void addViewInfo(
      @PathVariable("festivalId") Long festivalId,
      @RequestBody FestivalConcertMenuDto festivalConcertMenuDto) {
    festivalService.updateViewInfo(festivalId, festivalConcertMenuDto);
  }

  @GetMapping("/{festivalId}/entry")
  public FestivalConcertMenuDto getEntryInfo(@PathVariable("festivalId") Long festivalId) {
    return festivalService.getEntryInfo(festivalId);
  }

  @GetMapping("/{festivalId}/view")
  public FestivalConcertMenuDto getViewInfo(@PathVariable("festivalId") Long festivalId) {
    return festivalService.getViewInfo(festivalId);
  }

  /** 축제 위치 등록 api */
  @PostMapping("/{festivalId}/location")
  public FestivalResDto updateFestivalLocation(
      @PathVariable("festivalId") Long festivalId,
      @RequestBody FestivalLocationDto festivalLocationDto) {
      return festivalService.updateLocation(festivalId, festivalLocationDto);
  }
}
