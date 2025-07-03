package com.halo.eventer.domain.stamp.fixture;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.dto.FestivalCreateDto;
import com.halo.eventer.domain.stamp.Stamp;

import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class StampFixture {

    public static Long 존재하지_않는_스탬프 = 999L;

    public static Long 스탬프1 = 1L;
    public static Long 스탬프2 = 2L;
    public static Long 스탬프3 = 3L;

    public static Stamp 스탬프1_생성() {
        return Stamp.create(축제());
    }

    public static Stamp 스탬프2_생성() {
        return Stamp.create(축제());
    }

    public static Stamp 스탬프3_생성() {
        return Stamp.create(축제());
    }

    public static Festival 축제() {
        return Festival.from(new FestivalCreateDto("축제", "축제 주소"));
    }

    public static List<Stamp> 모든_스탬프() {
        return List.of(
                스탬프1_생성(),
                스탬프2_생성(),
                스탬프3_생성()
        );
    }

    public static Stamp 스탬프_엔티티_생성() {
        return Stamp.create(Festival.from(new FestivalCreateDto("축제", "축제 주소")));
    }
}
