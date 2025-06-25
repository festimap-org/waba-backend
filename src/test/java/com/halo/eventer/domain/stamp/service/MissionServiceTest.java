package com.halo.eventer.domain.stamp.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.halo.eventer.domain.stamp.Mission;
import com.halo.eventer.domain.stamp.dto.mission.MissionDetailGetDto;
import com.halo.eventer.domain.stamp.dto.mission.MissionUpdateDto;
import com.halo.eventer.domain.stamp.exception.MissionNotFoundException;
import com.halo.eventer.domain.stamp.fixture.MissionFixture;
import com.halo.eventer.domain.stamp.repository.MissionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class MissionServiceTest {

    @Mock
    private MissionRepository missionRepository;

    @InjectMocks
    private MissionService missionService;

    private Mission mission;
    private MissionUpdateDto missionUpdateDto;

    @BeforeEach
    void setUp() {
        mission = MissionFixture.미션_엔티티_생성();
        missionUpdateDto = MissionFixture.미션_업데이트_DTO_생성();
        setField(mission, "id", 1L);
    }

    @Test
    void 미션조회_성공() {
        // given
        given(missionRepository.findById(1L)).willReturn(Optional.of(mission));

        // when
        MissionDetailGetDto result = missionService.getMission(1L);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(MissionDetailGetDto.from(mission));
    }

    @Test
    void 미션조회_실패() {
        // given
        given(missionRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> missionService.getMission(1L)).isInstanceOf(MissionNotFoundException.class);
    }

    @Test
    void 미션수정_성공() {
        // given
        given(missionRepository.findById(1L)).willReturn(Optional.of(mission));

        // when
        missionService.updateMission(mission.getId(), missionUpdateDto);

        // then
        assertThat(mission.getBoothId()).isEqualTo(missionUpdateDto.getBoothId());
        assertThat(mission.getTitle()).isEqualTo(missionUpdateDto.getTitle());
        assertThat(mission.getPlace()).isEqualTo(missionUpdateDto.getPlace());
        assertThat(mission.getTime()).isEqualTo(missionUpdateDto.getTime());
    }
}
