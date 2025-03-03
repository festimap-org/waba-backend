package com.halo.eventer.domain.stamp.dto.stamp;

import com.halo.eventer.domain.stamp.Stamp;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StampGetDto {
    private Long stampId;
    private boolean stampOn;
    private Integer stampFinishCnt;

    public StampGetDto(Stamp stamp) {
        this.stampId = stamp.getId();
        this.stampOn = stamp.isStampOn();
        this.stampFinishCnt = stamp.getStampFinishCnt();
    }

    public static List<StampGetDto> fromStampList(List<Stamp> stampList) {
        return stampList.stream()
                .map(StampGetDto::new)
                .collect(Collectors.toList());
    }
}
