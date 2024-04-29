package com.halo.eventer.map.swagger.map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "상점 단일 조회", description = "(임시) 상점 id로 조회")
@ApiResponse(responseCode = "200", description = "상점 단일 조회 성공",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = {
                        @ExampleObject(name = "상점 정보",
                                value = "{\n" +
                                        "    \"id\": 1,\n" +
                                        "    \"name\": \"대동제\",\n" +
                                        "    \"summary\": \"세종대학교 축제\",\n" +
                                        "    \"latitude\": 242.12,\n" +
                                        "    \"longitude\": 333.222,\n" +
                                        "    \"isOperation\": false,\n" +
                                        "    \"operationHours\": \"미정\",\n" +
                                        "    \"type\": \"주점\"\n" +
                                        "}",
                                summary = "상점 단일 조회 성공", description = "상점 단일 조회를 성공적으로 수행한 예제입니다.")
                }))
public @interface GetStoreApi {
}
