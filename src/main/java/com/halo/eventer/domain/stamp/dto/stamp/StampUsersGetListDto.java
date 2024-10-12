package com.halo.eventer.domain.stamp.dto.stamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class StampUsersGetListDto {
    private List<StampUsersGetDto> stampUsers;

    public StampUsersGetListDto(List<StampUsersGetDto> stampUsers) { this.stampUsers = stampUsers; }
}
