package com.halo.eventer.domain.stamp.fixture;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.dto.FestivalCreateDto;
import com.halo.eventer.domain.stamp.Stamp;

@SuppressWarnings("NonAsciiCharacters")
public class StampFixture {

    public static Stamp 스탬프_엔티티_생성() {
        return Stamp.create(Festival.from(new FestivalCreateDto("test festival", "test address")));
    }
}
