package com.halo.eventer.home;


import com.halo.eventer.festival.dto.FestivalListDto;
import com.halo.eventer.festival.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final FestivalService festivalService;

    @GetMapping("/home/{festivalId}")
    public HomeDto getManInfo(@PathVariable Long festivalId) {
        return homeService.getMainPage(festivalId);
    }

    @GetMapping("/univ")
    public FestivalListDto getFestivalSubAddress(@RequestParam("subAddress") String name){
        return festivalService.getFestivalSubAddress(name);
    }

    @GetMapping("/")
    public void getOk(){
    }

}
