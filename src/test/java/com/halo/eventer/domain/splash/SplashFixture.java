package com.halo.eventer.domain.splash;

import java.util.List;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.splash.dto.request.ImageLayerDto;

import static org.springframework.test.util.ReflectionTestUtils.setField;

@SuppressWarnings("NonAsciiCharacters")
public class SplashFixture {

    public static Long 스플래시1 = 1L;

    public static Splash 스플래시1_생성(Festival festival) {
        Splash splash = new Splash(festival);
        splash.setBackgroundImage("background image 1");
        splash.setTopLayerImage("top layer image 1");
        splash.setCenterLayerImage("center layer image 1");
        splash.setBottomLayerImage("bottom layer image 1");
        return splash;
    }

    public static ImageLayerDto 배경_이미지_업로드_DTO() {
        ImageLayerDto dto = new ImageLayerDto();
        setField(dto, "url", "background url 1");
        setField(dto, "layerType", "background");
        return dto;
    }

    public static ImageLayerDto 상단_이미지_업로드_DTO() {
        ImageLayerDto dto = new ImageLayerDto();
        setField(dto, "url", "top url 1");
        setField(dto, "layerType", "top");
        return dto;
    }

    public static ImageLayerDto 중앙_이미지_업로드_DTO() {
        ImageLayerDto dto = new ImageLayerDto();
        setField(dto, "url", "center url 1");
        setField(dto, "layerType", "center");
        return dto;
    }

    public static ImageLayerDto 하단_이미지_업로드_DTO() {
        ImageLayerDto dto = new ImageLayerDto();
        setField(dto, "url", "bottom url 1");
        setField(dto, "layerType", "bottom");
        return dto;
    }

    public static List<ImageLayerDto> 이미지_업로드_DTO_생성() {
        return List.of(배경_이미지_업로드_DTO(), 상단_이미지_업로드_DTO(), 중앙_이미지_업로드_DTO(), 하단_이미지_업로드_DTO());
    }

    public static List<String> 이미지_삭제_타입_리스트() {
        return List.of("background", "top", "center", "bottom");
    }
}
