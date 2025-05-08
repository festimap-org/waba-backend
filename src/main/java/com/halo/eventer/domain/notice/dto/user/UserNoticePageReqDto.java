package com.halo.eventer.domain.notice.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserNoticePageReqDto {
    private Long lastId = 0L;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastUpdatedAt = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    @Min(0)
    @Max(50)
    private int size = 20;
}
