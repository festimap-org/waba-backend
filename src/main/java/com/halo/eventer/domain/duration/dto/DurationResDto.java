package com.halo.eventer.domain.duration.dto;


import com.halo.eventer.domain.duration.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class DurationGetDto {

    private Long durationId;
    private LocalDate date;
    private Integer day;

    public DurationGetDto(Duration duration) {
        this.durationId = duration.getId();
        this.date = duration.getDate();
        this.day = duration.getDayNumber();
    }

    public static List<DurationGetDto> fromDurationList(List<Duration> durationList) {
        return durationList.stream()
                .map(DurationGetDto::new)
                .collect(Collectors.toList());
    }
}

