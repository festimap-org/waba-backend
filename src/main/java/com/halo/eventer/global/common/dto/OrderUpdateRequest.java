package com.halo.eventer.global.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderUpdateRequest {

    private Long id;
    private int displayOrder;
}