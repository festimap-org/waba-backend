package com.halo.eventer.domain.splash.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.splash.Splash;
import com.halo.eventer.domain.splash.dto.UploadImageDto;
import com.halo.eventer.domain.splash.repository.SplashRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SplashService {
    private final FestivalService festivalService;
    private final SplashRepository splashRepository;

    /** 이미지 업로드 + 수정 */
    @Transactional
    public String uploadSplashImage(Long festivalId, UploadImageDto uploadImageDto) {
        Festival festival = festivalService.getFestival(festivalId);

        Splash splash = splashRepository.findByFestivalId(festivalId).orElse(new Splash(festival));
        switch (uploadImageDto.getLayerType()) {
            case "background":
                splash.setBackgroundImage(uploadImageDto.getUrl());
                break;
            case "top":
                splash.setTopLayerImage(uploadImageDto.getUrl());
                break;
            case "center":
                splash.setCenterLayerImage(uploadImageDto.getUrl());
                break;
            case "bottom":
                splash.setBottomLayerImage(uploadImageDto.getUrl());
                break;
            default:
                throw new BaseException(ErrorCode.ELEMENT_NOT_FOUND);
        }

        splashRepository.save(splash);
        return uploadImageDto.getLayerType() + " 이미지 업로드 성공";
    }

    /** 배경 레이어 이미지 삭제 */
    @Transactional
    public String deleteBackgroundImage(Long festivalId) {
        Splash splash = splashRepository.findByFestivalId(festivalId).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
        if (splash.getBackgroundImage() == null) throw new BaseException(ErrorCode.ELEMENT_NOT_FOUND);
        else {
            splash.setBackgroundImage(null);
            splashRepository.save(splash);
            return "배경 레이어 이미지 삭제";
        }
    }

    /** 상단 레이어 이미지 삭제 */
    @Transactional
    public String deleteTopLayerImage(Long festivalId) {
        Splash splash = splashRepository.findByFestivalId(festivalId).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
        if (splash.getTopLayerImage() == null) throw new BaseException(ErrorCode.ELEMENT_NOT_FOUND);
        else {
            splash.setTopLayerImage(null);
            splashRepository.save(splash);
            return "상단 레이어 이미지 삭제";
        }
    }

    /** 중앙 레이어 이미지 삭제 */
    @Transactional
    public String deleteCenterLayerImage(Long festivalId) {
        Splash splash = splashRepository.findByFestivalId(festivalId).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
        if (splash.getCenterLayerImage() == null) throw new BaseException(ErrorCode.ELEMENT_NOT_FOUND);
        else {
            splash.setCenterLayerImage(null);
            splashRepository.save(splash);
            return "중앙 레이어 이미지 삭제";
        }
    }

    /** 하단 레이어 이미지 삭제 */
    @Transactional
    public String deleteBottomLayerImage(Long festivalId) {
        Splash splash = splashRepository.findByFestivalId(festivalId).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
        if (splash.getBottomLayerImage() == null) throw new BaseException(ErrorCode.ELEMENT_NOT_FOUND);
        else {
            splash.setBottomLayerImage(null);
            splashRepository.save(splash);
            return "하단 레이어 이미지 삭제";
        }
    }

    /** 전체 레이어 조회 */
    public Splash getSplash(Long festivalId) {
        Festival festival = festivalService.getFestival(festivalId);
        Splash splash = splashRepository.findByFestivalId(festivalId).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));

        return splash;
    }
}
