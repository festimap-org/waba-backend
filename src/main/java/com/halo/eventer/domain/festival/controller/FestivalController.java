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
  public String createFestival(@RequestBody FestivalCreateDto festivalCreateDto) {
    return festivalService.create(festivalCreateDto);
  }

  @GetFestivalApi
  @GetMapping("/{id}")
  public FestivalResDto getFestival(@PathVariable("id") Long id) {
    return new FestivalResDto(festivalService.findById(id));
  }

  @GetMapping
  public List<FestivalListDto> getFestivals() {
    return festivalService.findAll();
  }

  @FestivalUpdateApi
  @PatchMapping("/{id}")
  public FestivalResDto updateFestival(
      @PathVariable("id") Long id, @RequestBody FestivalCreateDto festivalCreateDto) {
    return festivalService.update(id, festivalCreateDto);
  }

  @FestivalDeleteApi
  @DeleteMapping("/{id}")
  public String deleteFestival(@PathVariable("id") Long id) {
    return festivalService.delete(id);
  }

  @FestivalColorAddApi
  @PostMapping("/{festivalId}/color")
  public String addColor(
      @PathVariable("festivalId") Long id, @RequestBody ColorReqDto colorReqDto) {
    return festivalService.updateColor(id, colorReqDto);
  }

  @FestivalLogoAddApi
  @PostMapping("/{festivalId}/logo")
  public String addLogo(@PathVariable("festivalId") Long id, @RequestBody ImageDto imageDto) {
    return festivalService.updateLogo(id, imageDto);
  }

  @PostMapping("/{festivalId}/main-menu")
  public String addMainMenu(
      @PathVariable("festivalId") Long id, @RequestBody MainMenuDto mainMenuDto) {
    return festivalService.updateMainMenu(id, mainMenuDto);
  }

  @GetMapping("/{festivalId}/main-menu")
  public MainMenuDto getMainMenu(@PathVariable("festivalId") Long id) {
    return festivalService.getMainMenu(id);
  }

  @PostMapping("/{festivalId}/entry")
  public String addEntryInfo(
      @PathVariable("festivalId") Long festivalId,
      @RequestBody FestivalConcertMenuDto festivalConcertMenuDto) {
    return festivalService.updateEntryInfo(festivalId, festivalConcertMenuDto);
  }

  @PostMapping("/{festivalId}/view")
  public String addViewInfo(
      @PathVariable("festivalId") Long festivalId,
      @RequestBody FestivalConcertMenuDto festivalConcertMenuDto) {
    return festivalService.updateViewInfo(festivalId, festivalConcertMenuDto);
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
    return new FestivalResDto(
        festivalService.updateLocation(festivalId, festivalLocationDto));
  }
}
