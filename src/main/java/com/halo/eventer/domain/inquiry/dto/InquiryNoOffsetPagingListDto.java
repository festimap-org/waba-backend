package com.halo.eventer.domain.inquiry.dto;


import com.halo.eventer.domain.inquiry.Inquiry;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class InquiryNoOffsetPagingListDto {
    private List<InquiryListElementResDto> inquiryList;
    private Boolean isLast;

    public InquiryNoOffsetPagingListDto(List<Inquiry> inquiryList, Boolean isLast) {
        this.inquiryList = inquiryList.stream().map(InquiryListElementResDto::new).collect(Collectors.toList());
        this.isLast = isLast;
    }
}
