package com.halo.eventer.domain.stamp.dto.stampUser;

import com.halo.eventer.domain.stamp.StampUser;
import com.halo.eventer.domain.stamp.UserMission;
import java.util.List;
import lombok.Getter;

@Getter
public class StampUserMissionDto {
    private StampUser stampUser;
    private List<UserMission> userMissions;

    public StampUserMissionDto(StampUser stampUser, List<UserMission> userMissions) {
        this.stampUser = stampUser;
        this.userMissions = userMissions;
    }
}
