package com.halo.eventer.domain.inquiry.dto;


import com.halo.eventer.global.common.PageInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InquiryPageResDto {
    private List<InquiryListElementResDto> inquiryList;
    private PageInfo pageInfo;

    public InquiryPageResDto(List<InquiryListElementResDto> inquiryList, PageInfo pageInfo) {
        this.inquiryList = inquiryList;
        this.pageInfo = pageInfo;
    }
}
