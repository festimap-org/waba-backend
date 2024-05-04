package com.halo.eventer.home;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "메인 페이지 조회", description = "축제 id로 축제 메인페이지 조회 (메인 배너에 들어갈 게시글이 response")
@ApiResponse(responseCode = "200", description = "배너 목록 조회 성공",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = {
                        @ExampleObject(name = "메인 배너 목록",
                                value = "[\n" +
                                        "    {\n" +
                                        "        \"id\": 1,\n" +
                                        "        \"image\": \"url2\"\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "        \"id\": 3,\n" +
                                        "        \"image\": \"url3\"\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "        \"id\": 7,\n" +
                                        "        \"image\": \"url5\"\n" +
                                        "    }\n" +
                                        "]",
                                summary = "배너 목록 조회 성공", description = "배너 목록을 성공적으로 조회한 경우의 예제입니다.")
                }))
public @interface GetHomeApi {
}
