package com.halo.eventer.user;


import com.halo.eventer.user.dto.MissingPersonDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class MissingPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String gender;
    private String thumbnail;
    private String missingLocation;
    private String missingTime;
    private String content;

    private String parentName;
    private String parentNo;
    private Boolean popup;


    public MissingPerson(MissingPersonDto m) {
        this.name = m.getName();
        this.age = m.getAge();
        this.gender = m.getGender();
        this.thumbnail = m.getThumbnail();
        this.missingLocation = m.getMissingLocation();
        this.missingTime = m.getMissingTime();
        this.content = m.getContent();
        this.parentName = m.getParentName();
        this.parentNo = m.getParentNo();
        this.popup = false;
    }

    public void update(MissingPersonDto m) {
        this.name = m.getName();
        this.age = m.getAge();
        this.gender = m.getGender();
        this.thumbnail = m.getThumbnail();
        this.missingLocation = m.getMissingLocation();
        this.missingTime = m.getMissingTime();
        this.content = m.getContent();
        this.parentName = m.getParentName();
        this.parentNo = m.getParentNo();
    }

    public void setPopup(Boolean popup) {
        this.popup = popup;
    }
}
