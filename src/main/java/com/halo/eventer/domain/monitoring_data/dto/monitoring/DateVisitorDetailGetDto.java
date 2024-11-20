package com.halo.eventer.domain.monitoring_data.dto.monitoring;

import lombok.Getter;

@Getter
public class DateVisitorDetailGetDto {

    private int teenager;
    private int twenties_and_thirties;
    private int forties_and_fifties;
    private int sixties_and_above;

    private int male;
    private int female;
    private int none;

    public DateVisitorDetailGetDto(int teenager, int twenties_and_thirties, int forties_and_fifties, int sixties_and_above, int male, int female, int none) {
        this.teenager = teenager;
        this.twenties_and_thirties = twenties_and_thirties;
        this.forties_and_fifties = forties_and_fifties;
        this.sixties_and_above = sixties_and_above;
        this.male = male;
        this.female = female;
        this.none = none;
    }
}
