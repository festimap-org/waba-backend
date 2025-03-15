package com.halo.eventer.domain.manager.dto;

import com.halo.eventer.domain.manager.Manager;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ManagerListResDto {
    List<ManagerResDto> managerResDtoList;

    public ManagerListResDto(List<Manager> managerResDtoList) {
        this.managerResDtoList = managerResDtoList.stream()
                .map(o->new ManagerResDto(o.getId(),o.getPhoneNo())).collect(Collectors.toList());
    }
}
