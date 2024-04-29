package com.halo.eventer.user;

import com.halo.eventer.user.dto.UrgentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Urgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Boolean popup;

    public Urgent(UrgentDto u) {
        this.title = u.getTitle();
        this.content = u.getContent();
        this.popup = false;
    }

    public void update(UrgentDto u) {
        this.title = u.getTitle();
        this.content = u.getContent();
    }

    public void setPopup(Boolean popup) {
        this.popup = popup;
    }
}
