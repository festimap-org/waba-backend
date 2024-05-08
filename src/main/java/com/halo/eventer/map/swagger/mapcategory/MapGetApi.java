package com.halo.eventer.map.swagger.mapcategory;



import com.halo.eventer.notice.dto.GetAllNoticeResDto;
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
@Operation(summary = "고정 부스 조회용 + 부스 관리 기능용", description = "부스 추가에서 고정 부스 조회와, 부스관리에서 부스 카테고리 ID로 부스를 조회할 수 있다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "부스 정보 조회 성공",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = MapGetApi.class))))
})
public @interface MapGetApi {
}
