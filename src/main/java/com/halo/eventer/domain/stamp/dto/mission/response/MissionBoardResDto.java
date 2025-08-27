package com.halo.eventer.domain.stamp.dto.mission.response;

import java.util.Comparator;
import java.util.List;

import com.halo.eventer.domain.stamp.UserMission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MissionBoardResDto {
    private int completedCount;
    private int totalCount;
    private boolean allCleared;
    private List<MissionIconRes> missions;

    public static MissionBoardResDto from(List<UserMission> list) {
        int total = list.size();
        int done = (int) list.stream().filter(UserMission::isComplete).count();
        return new MissionBoardResDto(
                done,
                total,
                done == total,
                list.stream()
                        .sorted(Comparator.comparing(um -> um.getMission().getId()))
                        .map(MissionIconRes::from)
                        .toList());
    }
}
