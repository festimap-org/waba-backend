package com.halo.eventer.infra.naver.sms.service;

import com.halo.eventer.domain.missing_person.dto.MissingPersonReqDto;
import com.halo.eventer.infra.naver.sms.dto.*;
import com.halo.eventer.infra.naver.sms.util.SmsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    private final SmsRequest smsRequest;

    @Transactional
    /*관리자 리스트 넘겨받기, 실종자 정보 넘겨받기*/
    public void sendSms(MissingPersonReqDto dto, List<String> phoneList)throws Exception{
        //1. 메시지 생성
        String message =
                "< 실종자 등록 알림 안내 >\n" +
                "이름 : %s\n" +
                "연령대 : %s\n" +
                "성별 : %s\n" +
                "실종 위치 : %s\n\n" +
                "자세한 실종자 정보는 링크 확인\n" +
                "%s";

        String realMessage = String.format(message,dto.getName(),dto.getAge(),dto.getGender(),dto.getMissingLocation(),dto.getDomainName());


        //2. 관리자 리스트 수만큼 MessageDto setter로 to 필드 변경하기
        List<MessageDto> messages = new ArrayList<>();
        for(String phone : phoneList) {
            MessageDto messageDto = MessageDto.builder()
                    .to(phone)
                    .content(realMessage)
                    .build();
            messages.add(messageDto);
        }


        //3. 네이버 클라우드 API 요청 보내기
        SmsReqDto smsReqDto = smsRequest.getSmsBodyWithFile(messages,realMessage);
        SmsResDto smsResDto = smsRequest.sendSmsReq(smsReqDto);
    }
}
