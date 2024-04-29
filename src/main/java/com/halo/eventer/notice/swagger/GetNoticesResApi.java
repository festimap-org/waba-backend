package com.halo.eventer.notice.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "공지사항 리스트 조회", description = "공지사항 한 개당 이미지, 이벤트명(title), 이벤트 간단설명(simpleExplanation)")
@ApiResponse(responseCode = "200", description = "공지사항 목록 조회 성공",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = {
                        @ExampleObject(name = "공지사항 목록",
                                value = "[\n" +
                                        "    {\n" +
                                        "        \"title\": \"공지사항1\",\n" +
                                        "        \"simpleExplanation\": \"안녕하세요\",\n" +
                                        "        \"thumbnail\": \"url2\"\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "        \"title\": \"공지사항2\",\n" +
                                        "        \"simpleExplanation\": \"안녕하세요\",\n" +
                                        "        \"thumbnail\": \"url2\"\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "        \"title\": \"공지사항3\",\n" +
                                        "        \"simpleExplanation\": \"안녕하세요\",\n" +
                                        "        \"thumbnail\": \"url2\"\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "        \"title\": \"공지사항4\",\n" +
                                        "        \"simpleExplanation\": \"안녕하세요\",\n" +
                                        "        \"thumbnail\": \"url2\"\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "        \"title\": \"공지사항5\",\n" +
                                        "        \"simpleExplanation\": \"안녕하세요\",\n" +
                                        "        \"thumbnail\": \"url2\"\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "        \"title\": \"돌림판 이벤트\",\n" +
                                        "        \"simpleExplanation\": \"안녕하세요\",\n" +
                                        "        \"thumbnail\": \"url2\"\n" +
                                        "    }\n" +
                                        "]",
                                summary = "공지사항 목록 조회 성공", description = "공지사항 목록을 성공적으로 조회한 경우의 예제입니다.")
                }))
public @interface GetNoticesResApi {
}

