package com.halo.eventer.domain.inquiry.dto;


import com.halo.eventer.domain.inquiry.Inquiry;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InquiryItemDto {
    private Long id;
    private String title;
    private Boolean isAnswered;
    private Boolean isSecret;
    private String userId;
    private LocalDateTime createdDate;

    public InquiryItemDto(Inquiry inquiry, String title, String userId) {
        this.id = inquiry.getId();
        this.isAnswered = inquiry.isAnswered();
        this.createdDate = inquiry.getCreatedAt();
        this.isSecret = inquiry.isSecret();
        this.title = title;
        this.userId = userId;
    }

    public InquiryItemDto(Inquiry inquiry) {
        this.id = inquiry.getId();
        this.isAnswered = inquiry.isAnswered();
        this.createdDate = inquiry.getCreatedAt();
        this.isSecret = inquiry.isSecret();
        this.title = inquiry.getTitle();
        this.userId = inquiry.getUserId();
    }
}
