package com.halo.eventer.infra.naver.sms.dto;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class SmsResDto {
    String requestId;
    LocalDateTime requestTime;
    String statusCode;
    String statusName;
}
