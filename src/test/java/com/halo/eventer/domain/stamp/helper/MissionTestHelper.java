package com.halo.eventer.domain.stamp.helper;

import com.halo.eventer.domain.stamp.Mission;
import com.halo.eventer.domain.stamp.dto.mission.MissionUpdateDto;

import static org.springframework.test.util.ReflectionTestUtils.setField;

public class MissionTestHelper {

    public static Mission setUpMission(){
        Mission mission = new Mission();
        setField(mission, "id", 1L);
        setField(mission, "boothId", 1L);
        setField(mission, "title", "mission title");
        setField(mission, "content", "mission content");
        setField(mission, "place", "mission place");
        setField(mission, "time", "mission time");
        setField(mission, "clearedThumbnail", "mission cleared thumbnail");
        setField(mission, "notClearedThumbnail", "mission not cleared thumbnail");
        return mission;
    }

    public static MissionUpdateDto setUpMissionUpdateDto() {
        MissionUpdateDto dto = new MissionUpdateDto();
        setField(dto, "boothId", 1L);
        setField(dto, "title", "test title");
        setField(dto, "content", "test content");
        setField(dto, "place", "test place");
        setField(dto, "time", "test time");
        setField(dto, "clearedThumbnail", "test cleared thumbnail");
        setField(dto, "notClearedThumbnail", "test not cleared thumbnail");
        return dto;
    }
}
