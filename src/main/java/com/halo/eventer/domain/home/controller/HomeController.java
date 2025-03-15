package com.halo.eventer.domain.home.controller;


import com.halo.eventer.domain.festival.dto.FestivalListDto;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.home.dto.HomeDto;
import com.halo.eventer.domain.home.service.HomeService;
import com.halo.eventer.domain.home.swagger.FestivalGetListApi;
import com.halo.eventer.domain.home.swagger.HomeGetApi;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final FestivalService festivalService;

    @HomeGetApi
    @GetMapping("/home/{festivalId}")
    public HomeDto getHomeInfo(@PathVariable Long festivalId,
                               @RequestParam("dateTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTime) {
        return homeService.getMainPage(festivalId,dateTime);
    }

    @FestivalGetListApi
    @GetMapping("/univ")
    public FestivalListDto getFestivalSubAddress(@RequestParam("subAddress") String name) {
        return festivalService.findBySubAddress(name);
    }

    @GetMapping("/")
    public void getOk(){
    }
}
