package com.halo.eventer;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class EventerApplication {

    @PostConstruct
    public void init() {
        // 애플리케이션 시작 시 기본 타임존을 Asia/Seoul로 설정
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(EventerApplication.class, args);
    }
}
