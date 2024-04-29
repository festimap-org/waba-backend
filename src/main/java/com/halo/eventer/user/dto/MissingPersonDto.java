package com.halo.eventer.user.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MissingPersonDto {
    private String name;
    private Integer age;
    private String gender;
    private String thumbnail;
    private String missingLocation;
    private String missingTime;
    private String content;

    private String parentName;
    private String parentNo;
    private boolean popup;
}
