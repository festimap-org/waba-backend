package com.halo.eventer.domain.stamp.dto.stampUser.response;

import java.time.LocalDateTime;
import java.util.List;

import com.halo.eventer.domain.stamp.StampUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampUserInfoResDto {
    private Long id;
    private String name;
    private String phone;
    private String uuid;
    private Boolean finished;
    private Integer participantCount;
    private String receivedPrizeName;
    private LocalDateTime createdAt;

    public static StampUserInfoResDto from(StampUser stampUser) {
        return new StampUserInfoResDto(
                stampUser.getId(),
                stampUser.getName(),
                stampUser.getPhone(),
                stampUser.getUuid(),
                stampUser.getFinished(),
                stampUser.getParticipantCount(),
                stampUser.getReceivedPrizeName(),
                stampUser.getCreatedAt());
    }

    public static List<StampUserInfoResDto> fromEntities(List<StampUser> stampUsers) {
        return stampUsers.stream().map(StampUserInfoResDto::from).toList();
    }
}
