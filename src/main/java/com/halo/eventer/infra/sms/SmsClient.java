package com.halo.eventer.infra.sms;

import com.halo.eventer.infra.sms.common.SmsSendRequest;

import java.util.List;

public interface SmsClient {
    void sendToMany(List<SmsSendRequest> smsRequests);
}
