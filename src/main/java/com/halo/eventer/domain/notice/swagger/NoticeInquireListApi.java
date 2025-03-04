package com.halo.eventer.domain.notice.swagger;

import com.halo.eventer.domain.notice.dto.NoticeInquireListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.http.MediaType;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "공지사항 리스트 조회", description = "공지사항 한개당 이미지(images), 이벤트명(title), 이벤트 간단 설명(simpleExplanation)")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = NoticeInquireListDto.class)))
})
public @interface NoticeInquireListApi {
}

