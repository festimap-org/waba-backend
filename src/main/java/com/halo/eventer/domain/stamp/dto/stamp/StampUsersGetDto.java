package com.halo.eventer.domain.stamp.dto.stamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StampUsersGetDto {
    private String uuid;

    public StampUsersGetDto(String uuid) { this.uuid = uuid; }
}
