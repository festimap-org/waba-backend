package com.halo.eventer.domain.inquiry;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.inquiry.dto.InquiryCreateReqDto;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(nullable = false)
    private Boolean isSecret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    private String userId;
    private String password;

    @Column(length = 500)
    private String content;
    private boolean isAnswered;

    @Column(length = 500)
    private String answer;

    @CreatedDate
    private LocalDateTime createdDate;
    public Inquiry(Festival festival,InquiryCreateReqDto inquiryCreateReqDto) {
        this.festival = festival;
        this.title = inquiryCreateReqDto.getTitle();
        this.isSecret = inquiryCreateReqDto.getIsSecret();
        this.userId = inquiryCreateReqDto.getUserId();
        this.password = inquiryCreateReqDto.getPassword();
        this.content = inquiryCreateReqDto.getContent();
        this.isAnswered = false;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        this.isAnswered = true;
    }
}
