package com.halo.eventer.domain.stamp.repository;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.dto.FestivalCreateDto;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.StampUser;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SuppressWarnings("NonAsciiCharacters")
@Transactional
public class StampUserRepositoryTest {

    @Autowired
    private StampUserRepository stampUserRepository;

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private StampRepository stampRepository;

    private StampUser stampUser;
    private Stamp stamp;
    private Festival festival;

    @BeforeEach
    void setUp() {
        FestivalCreateDto dto = new FestivalCreateDto("축제", "univ");
        festival = festivalRepository.save(Festival.from(dto));
        stamp = Stamp.create(festival);
        stampRepository.save(stamp);

        stampUser = new StampUser(stamp, "encryptedPhone", "encryptedName", 1);
        stampUserRepository.save(stampUser);
    }

    @Test
    void uuid로_스탬프유저_찾기() {
        // when
        Optional<StampUser> result = stampUserRepository.findByUuid(stampUser.getUuid());
        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(stampUser.getId());
        assertThat(result.get().getUuid()).isEqualTo(stampUser.getUuid());
    }

    @Test
    void 스탬프ID와_전화번호로_스탬프유저_존재확인() {
        // when
        boolean result = stampUserRepository.existsByStampIdAndPhone(stamp.getId(), "encryptedPhone");

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 스탬프ID와_전화번호로_스탬프유저_존재X() {
        // when
        boolean result = stampUserRepository.existsByStampIdAndPhone(stamp.getId(), "wrongPhoneNum");

        // then
        assertThat(result).isFalse();
    }

    @Test
    void 스탬프ID_전화번호_이름으로_스탬프유저_조회() {
        // when
        Optional<StampUser> result =
                stampUserRepository.findByStampIdAndPhoneAndName(stamp.getId(), "encryptedPhone", "encryptedName");

        // then
        assertThat(result).isPresent();
    }

    @Test
    void 스탬프ID_전화번호_이름으로_스탬프유저_조회X() {
        // when
        Optional<StampUser> result =
                stampUserRepository.findByStampIdAndPhoneAndName(stamp.getId(), "aa", "encryptedName");

        // then
        assertThat(result).isEmpty();
    }
}
