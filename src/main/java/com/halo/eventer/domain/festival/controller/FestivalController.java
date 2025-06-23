package com.halo.eventer.domain.festival.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.festival.dto.*;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.image.dto.FileDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/festival")
public class FestivalController {

    private final FestivalService festivalService;

    @PostMapping()
    public void createFestival(@RequestBody FestivalCreateDto festivalCreateDto) {
        festivalService.create(festivalCreateDto);
    }

    @GetMapping("/{id}")
    public FestivalResDto getFestival(@PathVariable("id") Long id) {
        return festivalService.findById(id);
    }

    @GetMapping
    public List<FestivalListDto> getFestivals() {
        return festivalService.findAll();
    }

    @PatchMapping("/{id}")
    public FestivalResDto updateFestival(
            @PathVariable("id") Long id, @RequestBody FestivalCreateDto festivalCreateDto) {
        return festivalService.update(id, festivalCreateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteFestival(@PathVariable("id") Long id) {
        festivalService.delete(id);
    }

    @PostMapping("/{festivalId}/color")
    public void addColor(@PathVariable("festivalId") Long id, @RequestBody ColorDto colorDto) {
        festivalService.updateColor(id, colorDto);
    }

    @PostMapping("/{festivalId}/logo")
    public void addLogo(@PathVariable("festivalId") Long id, @RequestBody FileDto fileDto) {
        festivalService.updateLogo(id, fileDto);
    }

    @PostMapping("/{festivalId}/location")
    public FestivalResDto updateFestivalLocation(
            @PathVariable("festivalId") Long festivalId, @RequestBody FestivalLocationDto festivalLocationDto) {
        return festivalService.updateLocation(festivalId, festivalLocationDto);
    }
}
