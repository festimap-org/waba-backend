package com.halo.eventer.domain.stamp.service;

import com.halo.eventer.domain.stamp.exception.MissionNotFoundException;
import com.halo.eventer.domain.stamp.helper.MissionTestHelper;
import com.halo.eventer.domain.stamp.Mission;
import com.halo.eventer.domain.stamp.dto.mission.MissionUpdateDto;
import com.halo.eventer.domain.stamp.repository.MissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MissionServiceTest {

    @Mock
    private MissionRepository missionRepository;

    @InjectMocks
    private MissionService missionService;

    private MissionUpdateDto missionUpdateDto;
    private Mission mission;
    private MissionTestHelper helper;

    @BeforeEach
    public void setUp() {
        mission = helper.setUpMission();
    }

    @Test
    void 미션조회_성공(){
        //given
        given(missionRepository.findById(1L)).willReturn(Optional.of(mission));

        //when
        Mission mission = missionService.getMission(1L);

        //then
        assertThat(mission).isEqualTo(this.mission);
    }

    @Test
    void 미션조회_실패(){
        //given
        given(missionRepository.findById(1L)).willReturn(Optional.of(mission));

        //when
        //then
        assertThatThrownBy(() -> missionService.getMission(1L)).isInstanceOf(MissionNotFoundException.class);
    }

    @Test
    void 미션수정_성공(){
        //given
        missionUpdateDto = helper.setUpMissionUpdateDto();
        given(missionRepository.findById(1L)).willReturn(Optional.of(mission));

        //when
        String resultMessage = missionService.updateMission(mission.getId(), missionUpdateDto);

        //then
        assertThat(resultMessage).isEqualTo("미션 수정 완료");
    }
}
