package com.halo.eventer.user.dto;

import com.halo.eventer.user.MissingPerson;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MissingPersonPopupDto {
    private String name;
    private Integer age;
    private String gender;
    private String thumbnail;
    private String content;
    private String missingLocation;

    public MissingPersonPopupDto(MissingPerson m) {
        this.name = m.getName();
        this.age = m.getAge();
        this.gender = m.getGender();
        this.thumbnail = m.getThumbnail();
        this.content = m.getContent();
        this.missingLocation = m.getMissingLocation();
    }
}
