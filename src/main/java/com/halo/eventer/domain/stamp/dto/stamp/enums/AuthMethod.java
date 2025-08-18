package com.halo.eventer.domain.stamp.dto.stamp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthMethod {
    // 0번 - 사용자가 부스에 있는 NFC를 스캔하거나, 미션 QR을 직접 스캔
    TAG_SCAN(0),
    // 1번 - 사용자에게 QR/바코드를 화면에 띄워주고, 부스 측이 이를 스캔
    USER_CODE_PRESENT(1),
    ;

    private final long code;
}
