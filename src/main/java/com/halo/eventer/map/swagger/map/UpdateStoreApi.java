package com.halo.eventer.map.swagger.map;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "상점 수정", description = "상점 id로 수정")
@ApiResponse(responseCode = "200", description = "상점 정보 수정 성공",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = {
                        @ExampleObject(name = "상점 업데이트 후 정보",
                                value = "{\n" +
                                        "    \"id\": 3,\n" +
                                        "    \"name\": \"대동제\",\n" +
                                        "    \"summary\": \"세종대학교\",\n" +
                                        "    \"latitude\": 242.12,\n" +
                                        "    \"longitude\": 112.46,\n" +
                                        "    \"isOperation\": false,\n" +
                                        "    \"operationHours\": \"\",\n" +
                                        "    \"type\": \"주점\"\n" +
                                        "}",
                                summary = "상점 정보 업데이트 성공", description = "상점 정보를 성공적으로 수정한 경우의 예제입니다.")
                }))
public @interface UpdateStoreApi {
}
