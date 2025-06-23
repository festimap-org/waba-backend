package com.halo.eventer.domain.stamp.repository;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.FestivalFixture;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.stamp.Stamp;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SuppressWarnings("NonAsciiCharacters")
public class StampRepositoryTest {

    @Autowired
    private StampRepository stampRepository;

    @Autowired
    private FestivalRepository festivalRepository;

    private Festival festival;
    private Stamp stamp;

    @BeforeEach
    void setUp() {
        festival = Festival.from(FestivalFixture.축제_생성용_DTO());
        stamp = Stamp.create(festival);
        festivalRepository.save(festival);
        stampRepository.save(stamp);
    }

    @Test
    void 축제로_스탬프투어_찾기() {
        // when
        List<Stamp> stamps = stampRepository.findByFestival(festival);

        // then
        assertThat(stamps.size()).isEqualTo(1);
        assertThat(stamps.get(0).getFestival()).isEqualTo(festival);
    }
}
