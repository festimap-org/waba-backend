package com.halo.eventer.domain.stamp.dto.stamp.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ButtonAction {
    QR_CAMERA(0, "QR"),
    NFC_SCAN(1, "NFC"),
    OPEN_URL(2, "OPEN URL"),
    OPEN_NEW_PAGE(3, "NEW PAGE"),
    OPEN_POPUP(4, "POPUP"),
    ;

    private final int code;
    private final String label;

    public static ButtonAction of(int code) {
        return Arrays.stream(values())
                .filter(v -> v.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("action code=" + code));
    }
}
