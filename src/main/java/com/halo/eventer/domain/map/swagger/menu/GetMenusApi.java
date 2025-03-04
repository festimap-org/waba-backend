package com.halo.eventer.domain.map.swagger.menu;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.http.MediaType;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "메뉴 전체 조회", description = "(임시) 특정 상점 id로 메뉴 전체 조회")
@ApiResponse(responseCode = "200", description = "메뉴 목록 조회 성공",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = {
                        @ExampleObject(name = "메뉴 목록",
                                value = "[\n" +
                                        "    {\n" +
                                        "        \"id\": 2,\n" +
                                        "        \"name\": \"삼겹살\",\n" +
                                        "        \"price\": 10000,\n" +
                                        "        \"summary\": \"메인\"\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "        \"id\": 3,\n" +
                                        "        \"name\": \"단무지\",\n" +
                                        "        \"price\": 100,\n" +
                                        "        \"summary\": \"기본 반찬\"\n" +
                                        "    }\n" +
                                        "]",
                                summary = "메뉴 목록 조회 성공", description = "메뉴 목록을 성공적으로 가져온 경우의 예제입니다.")
                }))
public @interface GetMenusApi {
}
