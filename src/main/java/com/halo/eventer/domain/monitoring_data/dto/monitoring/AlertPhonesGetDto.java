package com.halo.eventer.domain.monitoring_data.dto.monitoring;

import lombok.Getter;


@Getter
public class AlertPhonesGetDto {
    private String phone1;
    private String phone2;
    private String phone3;
    private String phone4;
    private String phone5;

    public AlertPhonesGetDto(String phone1, String phone2, String phone3, String phone4, String phone5) {
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
        this.phone4 = phone4;
        this.phone5 = phone5;
    }
}
