package com.halo.eventer.domain.inquiry.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InquiryListResDto {
    List<InquiryListElementResDto> inquiryList;

    public InquiryListResDto(List<InquiryListElementResDto> inquiryList) {
        this.inquiryList = inquiryList;
    }
}
