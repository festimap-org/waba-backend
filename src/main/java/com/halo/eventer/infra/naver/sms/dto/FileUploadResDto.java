package com.halo.eventer.infra.naver.sms.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class FileUploadResDto {
    private String fileId;
    private String createTime;
    private String expireTime;
}
