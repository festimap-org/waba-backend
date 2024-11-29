package com.halo.eventer.domain.monitoring_data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.monitoring_data.MonitoringData;
import com.halo.eventer.infra.naver.sms.dto.MessageDto;
import com.halo.eventer.infra.naver.sms.dto.SmsReqDto;
import com.halo.eventer.infra.naver.sms.dto.SmsResDto;
import com.halo.eventer.infra.naver.sms.util.SmsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitoringSmsService {

    private final FestivalService festivalService;
    private final SmsRequest smsRequest;

    /** 문자 알림 전송 */
    @Async
    public void sendAlertSms(MonitoringData monitoringData, Long festivalId, int percent) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        Festival festival = festivalService.getFestival(festivalId);
        List<String> phones = new ArrayList<>();
        if (monitoringData.getAlertPhone1() != null && !monitoringData.getAlertPhone1().equals("")) phones.add(monitoringData.getAlertPhone1());
        if (monitoringData.getAlertPhone2() != null && !monitoringData.getAlertPhone2().equals("")) phones.add(monitoringData.getAlertPhone2());
        if (monitoringData.getAlertPhone3() != null && !monitoringData.getAlertPhone3().equals("")) phones.add(monitoringData.getAlertPhone3());
        if (monitoringData.getAlertPhone4() != null && !monitoringData.getAlertPhone4().equals("")) phones.add(monitoringData.getAlertPhone4());
        if (monitoringData.getAlertPhone5() != null && !monitoringData.getAlertPhone5().equals("")) phones.add(monitoringData.getAlertPhone5());

        if (phones.isEmpty()) return;

        String message = "현재 \"" + festival.getName() + "\" 행사의 내부 인원 비율이 " + percent + "%에 도달했습니다.";

        List<MessageDto> messages = new ArrayList<>();
        for (String phone: phones) {
            MessageDto m = MessageDto.builder()
                    .to(phone)
                    .content(message)
                    .build();
            messages.add(m);
        }

        SmsReqDto smsReqDto = smsRequest.getSmsBodyWithFile(messages, null, message);
        SmsResDto smsResDto = smsRequest.sendSmsReq(smsReqDto);
    }
}
