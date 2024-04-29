package com.halo.eventer.notice.swagger;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "공지사항 리스트 조회", description = "공지사항 한개당 이미지(images), 이벤트명(title), 이벤트 간단 설명(simpleExplanation)")
public @interface GetNoticesReqApi {
}

