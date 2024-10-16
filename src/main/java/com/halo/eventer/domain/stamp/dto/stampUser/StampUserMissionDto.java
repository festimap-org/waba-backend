package com.halo.eventer.domain.stamp.dto.stampUser;

import com.halo.eventer.domain.stamp.StampUser;
import com.halo.eventer.domain.stamp.UserMission;
import lombok.Getter;

import java.util.List;

@Getter
public class StampUserMissionDto {
    private StampUser stampUser;
    private List<UserMission> userMissions;

    public StampUserMissionDto(StampUser stampUser, List<UserMission> userMissions) {
        this.stampUser = stampUser;
        this.userMissions = userMissions;
    }
}
