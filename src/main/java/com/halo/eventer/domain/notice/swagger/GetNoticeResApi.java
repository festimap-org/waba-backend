package com.halo.eventer.domain.notice.swagger;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "200", description = "공지사항 정보 조회 성공",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = {
                        @ExampleObject(name = "공지사항 정보",
                                value = "{\n" +
                                        "    \"title\": \"공지사항 1\",\n" +
                                        "    \"subtitle\": \"공지 1\",\n" +
                                        "    \"content\": \"내용\",\n" +
                                        "    \"images\": [\n" +
                                        "        \"url1\",\n" +
                                        "        \"url2\"\n" +
                                        "    ]\n" +
                                        "}",
                                summary = "공지사항 정보 조회 성공", description = "공지사항 정보를 성공적으로 조회한 경우의 예제입니다.")
                }))
public @interface GetNoticeResApi {
}
