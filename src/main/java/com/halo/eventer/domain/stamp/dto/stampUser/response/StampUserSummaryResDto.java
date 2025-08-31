package com.halo.eventer.domain.stamp.dto.stampUser.response;

import com.halo.eventer.domain.stamp.StampUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampUserSummaryResDto {
    private long id;
    private String name;
    private String phone;
    private String uuid;
    private boolean isFinished;

    public static StampUserSummaryResDto from(StampUser stampUser) {
        return new StampUserSummaryResDto(
                stampUser.getId(),
                stampUser.getName(),
                stampUser.getPhone(),
                stampUser.getUuid(),
                stampUser.getIsFinished());
    }
}
