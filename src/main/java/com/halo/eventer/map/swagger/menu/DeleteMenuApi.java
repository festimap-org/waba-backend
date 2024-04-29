package com.halo.eventer.map.swagger.menu;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "메뉴 삭제", description = "메뉴 id로 삭제")
@ApiResponse(responseCode = "200", description = "삭제 완료되었습니다.",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = {
                        @ExampleObject(name = "삭제 완료 예제",
                                value = "삭제완료",
                                summary = "API 성공 예제", description = "삭제가 완료된 경우의 예제입니다.")
                }))
public @interface DeleteMenuApi {
}
