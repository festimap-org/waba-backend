package com.halo.eventer.domain.inquiry.dto;


import com.halo.eventer.domain.inquiry.Inquiry;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InquiryListElementResDto {
    private Long id;
    private String title;
    private Boolean isAnswered;
    private Boolean isSecret;
    private String userId;
    private LocalDateTime createdDate;

    public InquiryListElementResDto(Inquiry inquiry, String title, String userId) {
        this.id = inquiry.getId();
        this.isAnswered = inquiry.isAnswered();
        this.createdDate = inquiry.getCreatedDate();
        this.isSecret = inquiry.getIsSecret();
        this.title = title;
        this.userId = userId;
    }

    public InquiryListElementResDto(Inquiry inquiry) {
        this.id = inquiry.getId();
        this.isAnswered = inquiry.isAnswered();
        this.createdDate = inquiry.getCreatedDate();
        this.isSecret = inquiry.getIsSecret();
        this.title = inquiry.getTitle();
        this.userId = inquiry.getUserId();
    }
}
