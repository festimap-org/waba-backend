package com.halo.eventer.domain.home.swagger;


import com.halo.eventer.domain.home.dto.HomeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.http.MediaType;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "메인 페이지 조회", description = "축제 id로 축제 메인페이지 조회 (메인 배너에 들어갈 게시글이 response")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = HomeDto.class)))
})
public @interface HomeGetApi {
}
