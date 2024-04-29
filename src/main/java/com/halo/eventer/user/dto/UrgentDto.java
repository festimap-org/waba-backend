package com.halo.eventer.user.dto;


import com.halo.eventer.user.Urgent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UrgentDto {

    private String title;
    private String content;

    public UrgentDto(Urgent u) {
        this.title = u.getTitle();
        this.content = u.getContent();
    }
}
