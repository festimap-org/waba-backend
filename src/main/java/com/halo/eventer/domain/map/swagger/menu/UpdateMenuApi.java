package com.halo.eventer.domain.map.swagger.menu;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "메뉴 수정", description = "메뉴 id로 수정")
@ApiResponse(responseCode = "200", description = "메뉴 수정 성공",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = {
                        @ExampleObject(name = "메뉴 수정 후 정보",
                                value = "{\n" +
                                        "    \"id\": 2,\n" +
                                        "    \"name\": \"삼겹살\",\n" +
                                        "    \"price\": 10000,\n" +
                                        "    \"summary\": \"메인\"\n" +
                                        "}",
                                summary = "메뉴 수정 성공", description = "메뉴를 성공적으로 수정한 경우의 예제입니다.")
                }))
public @interface UpdateMenuApi {
}
