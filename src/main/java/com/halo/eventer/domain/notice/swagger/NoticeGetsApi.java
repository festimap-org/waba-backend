package com.halo.eventer.domain.notice.swagger;

import com.halo.eventer.domain.notice.dto.GetAllNoticeResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "공지사항 리스트 조회", description = "공지사항 한개당 이미지(images), 이벤트명(title), 이벤트 간단 설명(simpleExplanation)")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "공지사항 목록 조회 성공", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = GetAllNoticeResDto.class))
                )
        ),
        @ApiResponse(responseCode = "400", description = "데이터가 존재하지 않을 경우 예외",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        examples = {@ExampleObject(value = "공지사항이 존재하지 않습니다.")
                        })
        )
})
public @interface NoticeGetsApi {
}

