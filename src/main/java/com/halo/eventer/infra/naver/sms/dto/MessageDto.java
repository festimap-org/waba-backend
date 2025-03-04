package com.halo.eventer.infra.naver.sms.dto;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class MessageDto {
    String to;
    String content;
}
