package com.halo.eventer.domain.stamp.dto.mission.response;

import java.util.List;
import java.util.stream.Collectors;

import com.halo.eventer.domain.stamp.Mission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionBriefResDto {
    private Long missionId;
    private String title;

    public static List<MissionBriefResDto> fromEntities(List<Mission> missions) {
        return missions.stream().map(MissionBriefResDto::from).collect(Collectors.toList());
    }

    public static MissionBriefResDto from(Mission mission) {
        return new MissionBriefResDto(mission.getId(), mission.getTitle());
    }
}
