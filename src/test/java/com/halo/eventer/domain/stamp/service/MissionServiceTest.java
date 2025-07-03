package com.halo.eventer.domain.stamp.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.halo.eventer.domain.stamp.Mission;
import com.halo.eventer.domain.stamp.dto.mission.MissionDetailGetDto;
import com.halo.eventer.domain.stamp.dto.mission.MissionUpdateDto;
import com.halo.eventer.domain.stamp.exception.MissionNotFoundException;
import com.halo.eventer.domain.stamp.fixture.MissionFixture;
import com.halo.eventer.domain.stamp.repository.MissionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
public class MissionServiceTest {

    @Mock
    private MissionRepository missionRepository;

    @InjectMocks
    private MissionService missionService;

    private Mission mission;

    @BeforeEach
    void setUp() {
        mission = MissionFixture.미션_엔티티_생성();
    }

    @Test
    void 미션조회_성공() {
        // given
        given(missionRepository.findById(anyLong())).willReturn(Optional.of(mission));

        // when
        MissionDetailGetDto result = missionService.getMission(1L);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(MissionDetailGetDto.from(mission));
    }

    @Test
    void 미션조회_실패() {
        // given
        given(missionRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> missionService.getMission(1L)).isInstanceOf(MissionNotFoundException.class);
    }

    @Test
    void 미션수정_성공() {
        // given
        MissionUpdateDto dto = MissionFixture.미션_업데이트_DTO_생성();
        given(missionRepository.findById(anyLong())).willReturn(Optional.of(mission));

        // when
        missionService.updateMission(1L, dto);

        // then
        assertThat(mission.getBoothId()).isEqualTo(dto.getBoothId());
        assertThat(mission.getTitle()).isEqualTo(dto.getTitle());
        assertThat(mission.getContent()).isEqualTo(dto.getContent());
        assertThat(mission.getPlace()).isEqualTo(dto.getPlace());
        assertThat(mission.getTime()).isEqualTo(dto.getTime());
        assertThat(mission.getClearedThumbnail()).isEqualTo(dto.getClearedThumbnail());
        assertThat(mission.getNotClearedThumbnail()).isEqualTo(dto.getNotClearedThumbnail());
    }

    @Test
    void 미션_일부수정_성공() {
        // given
        MissionUpdateDto dto = MissionFixture.미션_업데이트_DTO_일부수정_생성();
        given(missionRepository.findById(anyLong())).willReturn(Optional.of(mission));

        // when
        missionService.updateMission(1L, dto);

        // then - 수정된 필드
        assertThat(mission.getTitle()).isEqualTo("수정된 제목");
        assertThat(mission.getContent()).isEqualTo("수정된 내용");

        // then - 수정되지 않은 필드
        assertThat(mission.getBoothId()).isEqualTo(1L);
        assertThat(mission.getPlace()).isEqualTo("A관 1층");
        assertThat(mission.getTime()).isEqualTo("10:00 ~ 18:00");
        assertThat(mission.getClearedThumbnail()).isEqualTo("cleared.png");
        assertThat(mission.getNotClearedThumbnail()).isEqualTo("not_cleared.png");
    }
}
