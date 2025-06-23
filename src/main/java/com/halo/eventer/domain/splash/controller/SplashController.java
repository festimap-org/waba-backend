package com.halo.eventer.domain.splash.controller;

import org.springframework.web.bind.annotation.*;

import com.halo.eventer.domain.splash.Splash;
import com.halo.eventer.domain.splash.dto.DeleteImageDto;
import com.halo.eventer.domain.splash.dto.SplashGetDto;
import com.halo.eventer.domain.splash.dto.UploadImageDto;
import com.halo.eventer.domain.splash.service.SplashService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/splash")
public class SplashController {
    private final SplashService splashService;

    @PostMapping
    public String uploadSplashImage(@RequestParam Long festivalId, @RequestBody UploadImageDto dto) {
        return splashService.uploadSplashImage(festivalId, dto);
    }

    @DeleteMapping
    public String deleteSplashImage(@RequestParam Long festivalId, @RequestBody DeleteImageDto dto) {
        return splashService.deleteSplashImage(festivalId, dto);
    }

    @GetMapping
    public SplashGetDto getSplash(@RequestParam Long festivalId) {
        Splash splash = splashService.getSplash(festivalId);
        return new SplashGetDto(
                splash.getBackgroundImage(),
                splash.getTopLayerImage(),
                splash.getCenterLayerImage(),
                splash.getBottomLayerImage());
    }
}
