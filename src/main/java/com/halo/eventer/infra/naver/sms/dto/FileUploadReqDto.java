package com.halo.eventer.infra.naver.sms.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FileUploadReqDto {
    private String fileName;
    private String fileBody;
}
