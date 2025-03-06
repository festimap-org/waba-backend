package com.halo.eventer.domain.stamp.service;

import com.halo.eventer.domain.stamp.helper.MissionTestHelper;
import com.halo.eventer.domain.stamp.Mission;
import com.halo.eventer.domain.stamp.dto.mission.MissionUpdateDto;
import com.halo.eventer.domain.stamp.repository.MissionRepository;
import com.halo.eventer.global.error.exception.BaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class MissionServiceTest {

    @Mock
    private MissionRepository missionRepository;

    @InjectMocks
    private MissionService missionService;

    private Mission mission;

    @BeforeEach
    public void setUp() {
        mission = new Mission();
    }

    @Test
    void 미션조회_성공(){
        //given
        given(missionRepository.findById(1L)).willReturn(Optional.of(mission));

        //when
        Mission result = missionService.getMission(1L);

        //then
        assertThat(result).isEqualTo(mission);
    }

    @Test
    void 미션조회_실패(){
        //given
        given(missionRepository.findById(1L)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> missionService.getMission(1L)).isInstanceOf(BaseException.class);
    }

    @Test
    void 미션수정_성공(){
        //given
        MissionUpdateDto updateDto = new MissionUpdateDto();
        given(missionRepository.findById(1L)).willReturn(Optional.of(mission));

        //when
        String result = missionService.updateMission(mission.getId(), updateDto);

        //then
        assertThat(result).isEqualTo("미션 수정 완료");
    }
}
