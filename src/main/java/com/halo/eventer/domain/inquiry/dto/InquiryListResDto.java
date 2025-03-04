package com.halo.eventer.domain.inquiry.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryListResDto {
    List<InquiryListElementResDto> inquiryList;

    public InquiryListResDto(List<InquiryListElementResDto> inquiryList) {
        this.inquiryList = inquiryList;
    }
}
