package com.halo.eventer.domain.stamp.dto.stamp;

import com.halo.eventer.domain.stamp.Stamp;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class StampGetDto {
    private Long stampId;
    private boolean stampOn;

    public StampGetDto(Stamp stamp) {
        this.stampId = stamp.getId();
        this.stampOn = stamp.isStampOn();
    }

    public static List<StampGetDto> fromStampList(List<Stamp> stampList) {
        return stampList.stream()
                .map(StampGetDto::new)
                .collect(Collectors.toList());
    }
}
