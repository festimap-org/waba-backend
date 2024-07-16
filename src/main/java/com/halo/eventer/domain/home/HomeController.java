package com.halo.eventer.domain.home;


import com.halo.eventer.domain.festival.dto.FestivalListDto;
import com.halo.eventer.domain.festival.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final FestivalService festivalService;

    @GetMapping("/home/{festivalId}")
    public HomeDto getManInfo(@PathVariable Long festivalId,
                              @RequestParam("dateTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTime) {
        return homeService.getMainPage(festivalId,dateTime);
    }

    @GetMapping("/univ")
    public FestivalListDto getFestivalSubAddress(@RequestParam("subAddress") String name){
        return festivalService.getFestivalSubAddress(name);
    }

    @GetMapping("/")
    public void getOk(){
    }

}
