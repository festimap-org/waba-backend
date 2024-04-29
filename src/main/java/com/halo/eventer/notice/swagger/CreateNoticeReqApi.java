package com.halo.eventer.notice.swagger;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "공지사항 생성", description = "공지사항 등록")
public @interface CreateNoticeReqApi {
}
