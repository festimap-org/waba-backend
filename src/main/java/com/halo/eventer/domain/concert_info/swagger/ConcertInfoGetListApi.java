package com.halo.eventer.domain.concert_info.swagger;

import com.halo.eventer.domain.concert_info.dto.ConcertInfoGetListDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.http.MediaType;

@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(
    value = {
      @ApiResponse(
          responseCode = "200",
          content =
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ConcertInfoGetListDto.class)))
    })
public @interface ConcertInfoGetListApi {}
