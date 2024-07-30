package com.halo.eventer.domain.inquiry.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InquiryUserReqDto {
    private String userId;
    private String password;
}
