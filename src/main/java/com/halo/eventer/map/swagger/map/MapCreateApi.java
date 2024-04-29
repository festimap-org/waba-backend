package com.halo.eventer.map.swagger.map;


import com.halo.eventer.festival.dto.FestivalResDto;
import com.halo.eventer.map.dto.map.StoreCreateResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "맵 등록", description = "맵 등록 URI")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "맵 정보 생성 성공",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = StoreCreateResDto.class))),
        @ApiResponse(responseCode = "400", description = "축제가 삭제되었을 경우",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        examples = {@ExampleObject(value = "축제 정보가 존재하지 않습니다.")
                        })
        )
})
public @interface MapCreateApi {
}
