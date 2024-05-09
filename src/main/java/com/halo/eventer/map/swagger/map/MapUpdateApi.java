package com.halo.eventer.map.swagger.map;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "맵 수정", description = "맵 id로 수정, 기간 변경시 deleteIds에 지울 id 넣어주기, 만약 기간 변경하지 않으면 durationIds 비워서 보내주기")
@ApiResponse(responseCode = "200", description = "맵 정보 수정 성공",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = {
                        @ExampleObject(name = "맵 업데이트 후 정보",
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
                                summary = "맵 정보 업데이트 성공", description = "맵 정보를 성공적으로 수정한 경우의 예제입니다.")
                }))
public @interface MapUpdateApi {

}
