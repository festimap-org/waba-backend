package com.halo.eventer.domain.stamp.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.halo.eventer.domain.stamp.Mission;
import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.StampUser;
import com.halo.eventer.domain.stamp.UserMission;
import com.halo.eventer.domain.stamp.exception.UserMissionNotFoundException;
import com.halo.eventer.domain.stamp.fixture.MissionFixture;
import com.halo.eventer.domain.stamp.fixture.StampFixture;
import com.halo.eventer.domain.stamp.fixture.StampUserFixture;
import com.halo.eventer.domain.stamp.fixture.UserMissionFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
public class StampUserTest {

    private Stamp stamp;
    private StampUser stampUser;
    private Mission mission;

    @BeforeEach
    void setUp() {
        stampUser = StampUserFixture.스탬프유저_엔티티_생성(StampFixture.스탬프_엔티티_생성());
        stamp = new Stamp();
        stamp.defineFinishCnt(1);
        stampUser.addStamp(stamp);
    }

    @Test
    void 완료_마킹() {
        stampUser.markAsFinished();
        assertThat(stampUser.isFinished()).isTrue();
    }

    @Test
    void 유저미션_할당() {
        // given
        List<UserMission> userMissions = new ArrayList<>();
        UserMission userMission1 = UserMissionFixture.유저미션_엔티티_생성(1L, stampUser, MissionFixture.미션_엔티티_생성());
        UserMission userMission2 = UserMissionFixture.유저미션_엔티티_생성(2L, stampUser, MissionFixture.미션_엔티티_생성());
        userMissions.add(userMission1);
        userMissions.add(userMission2);

        // when
        stampUser.assignUserMissions(userMissions);

        // then
        assertThat(stampUser.getUserMissions()).isEqualTo(userMissions);
    }

    @Test
    void 유저미션_전부_성공_완료여부_확인() {
        // given
        List<UserMission> userMissions = new ArrayList<>();
        UserMission userMission1 = UserMissionFixture.유저미션_엔티티_생성(1L, stampUser, MissionFixture.미션_엔티티_생성());
        UserMission userMission2 = UserMissionFixture.유저미션_엔티티_생성(2L, stampUser, MissionFixture.미션_엔티티_생성());
        userMission1.markAsComplete();
        userMission2.markAsComplete();
        userMissions.add(userMission1);
        userMissions.add(userMission2);

        // when
        stampUser.assignUserMissions(userMissions);
        boolean result = stampUser.isMissionsAllCompleted();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 유저미션_완료() {
        // given
        List<UserMission> userMissions = new ArrayList<>();
        UserMission userMission = UserMissionFixture.유저미션_엔티티_생성(1L, stampUser, MissionFixture.미션_엔티티_생성());
        userMissions.add(userMission);
        stampUser.assignUserMissions(userMissions);

        // when
        stampUser.userMissionComplete(1L);

        // then
        assertThat(stampUser.isMissionsAllCompleted()).isTrue();
    }

    @Test
    void 유저미션_완료_없는_ID_실패() {
        // given
        stampUser.assignUserMissions(List.of()); // 비어있거나 다른 id만 존재

        // when & then
        assertThatThrownBy(() -> stampUser.userMissionComplete(999L)).isInstanceOf(UserMissionNotFoundException.class);
    }

    @Test
    void 스탬프투어_종료_가능() {
        // given
        UserMission mission = UserMissionFixture.유저미션_엔티티_생성(1L, stampUser, MissionFixture.미션_엔티티_생성());
        mission.markAsComplete();
        stampUser.assignUserMissions(List.of(mission));

        // when
        boolean result = stampUser.canFinishTour();

        // then
        assertThat(result).isTrue();
    }
}
