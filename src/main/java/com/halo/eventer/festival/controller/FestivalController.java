package com.halo.eventer.festival.controller;


import com.halo.eventer.festival.dto.FestivalCreateDto;
import com.halo.eventer.festival.dto.FestivalResDto;
import com.halo.eventer.festival.service.FestivalService;
import com.halo.eventer.festival.swagger.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/festival")
@Tag(name = "축제")
public class FestivalController {

    private final FestivalService festivalService;
    @FestivalCreateApi
    @PostMapping()
    public String createFestival(@RequestBody FestivalCreateDto festivalCreateDto){
        return festivalService.createFestival(festivalCreateDto);
    }

    @GetFestivalApi
    @GetMapping("/{id}")
    public FestivalResDto getFestival(@PathVariable("id")Long id){
            return festivalService.getFestival(id);
    }

    @FestivalUpdateApi
    @PatchMapping("/{id}")
    public FestivalResDto updateFestival(@PathVariable("id") Long id, @RequestBody FestivalCreateDto festivalCreateDto){
            return festivalService.updateFestival(id,festivalCreateDto);
    }

    @FestivalDeleteApi
    @DeleteMapping("/{id}")
    public String deleteFestival(@PathVariable("id") Long id){
            return festivalService.deleteFestival(id);
    }
}
