package com.halo.eventer.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.timestreamquery.TimestreamQueryClient;
import software.amazon.awssdk.services.timestreamwrite.TimestreamWriteClient;

@Configuration
public class TimeStreamConfig {

    @Bean
    public TimestreamWriteClient timestreamWriteClient() {
        return TimestreamWriteClient.builder()
                .region(Region.AP_NORTHEAST_1)
                .build();
    }

    @Bean
    public TimestreamQueryClient timestreamQueryClient() {
        return TimestreamQueryClient.builder()
                .region(Region.AP_NORTHEAST_1)      // 도쿄
                .build();
    }
}
