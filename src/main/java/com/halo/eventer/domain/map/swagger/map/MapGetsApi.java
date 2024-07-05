package com.halo.eventer.domain.map.swagger.map;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "맵 전체 조회", description = "(임시) 특정 축제 id로 맵 전체 조회")
@ApiResponse(responseCode = "200", description = "맵 목록 조회 성공",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = {
                        @ExampleObject(name = "맵 목록",
                                value = "[\n" +
                                        "    {\n" +
                                        "        \"id\": 1,\n" +
                                        "        \"name\": \"대동제\",\n" +
                                        "        \"summary\": \"세종대학교 축제\",\n" +
                                        "        \"latitude\": 242.12,\n" +
                                        "        \"longitude\": 0.0,\n" +
                                        "        \"isOperation\": false,\n" +
                                        "        \"operationHours\": \"주소\",\n" +
                                        "        \"type\": \"주점\"\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "        \"id\": 2,\n" +
                                        "        \"name\": \"대동제\",\n" +
                                        "        \"summary\": \"세종대학교 축제\",\n" +
                                        "        \"latitude\": 242.12,\n" +
                                        "        \"longitude\": 0.0,\n" +
                                        "        \"isOperation\": false,\n" +
                                        "        \"operationHours\": \"주소\",\n" +
                                        "        \"type\": \"기타\"\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "        \"id\": 3,\n" +
                                        "        \"name\": \"대동제\",\n" +
                                        "        \"summary\": \"세종대학교\",\n" +
                                        "        \"latitude\": 242.12,\n" +
                                        "        \"longitude\": 112.46,\n" +
                                        "        \"isOperation\": false,\n" +
                                        "        \"operationHours\": \"\",\n" +
                                        "        \"type\": \"주점\"\n" +
                                        "    }\n" +
                                        "]",
                                summary = "맵 목록 조회 성공", description = "맵 목록을 성공적으로 가져온 경우의 예제입니다.")
                }))
public @interface MapGetsApi {
}
