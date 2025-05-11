package com.halo.eventer.infra.sms;

import com.halo.eventer.infra.sms.common.SmsSendRequest;
import com.halo.eventer.infra.sms.naver.dto.NaverSmsResDto;

import java.util.List;

public interface SmsClient {
    NaverSmsResDto sendToMany(List<SmsSendRequest> smsRequests);
}
