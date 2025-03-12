package com.halo.eventer.domain.inquiry.dto;


import com.halo.eventer.domain.inquiry.Inquiry;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryNoOffsetPagingListDto {
    private List<InquiryItemDto> inquiryList;
    private Boolean isLast;

    public InquiryNoOffsetPagingListDto(List<Inquiry> inquiryList, Boolean isLast) {
        this.inquiryList = inquiryList.stream().map(InquiryItemDto::new).collect(Collectors.toList());
        this.isLast = isLast;
    }
}
