package com.halo.eventer.home;


import com.halo.eventer.festival.dto.FestivalListDto;
import com.halo.eventer.festival.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final FestivalService festivalService;

    @GetMapping("/home")
    public ResponseEntity<?> getManInfo(){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(homeService.getMainPage());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/univ")
    public FestivalListDto getFestivalSubAddress(@RequestParam("subAddress") String name){
        return festivalService.getFestivalSubAddress(name);
    }

}
