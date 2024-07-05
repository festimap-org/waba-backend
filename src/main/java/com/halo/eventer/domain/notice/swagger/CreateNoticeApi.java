package com.halo.eventer.domain.notice.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "공지사항 생성", description = "공지사항 등록")
@ApiResponse(responseCode = "200", description = "저장이 완료되었습니다.",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = {
                        @ExampleObject(name = "저장 완료 예제",
                                value = "저장완료",
                                summary = "API 성공 예제", description = "저장이 완료된 경우의 예제입니다.")
                }))
public @interface CreateNoticeApi {
}
