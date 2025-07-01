package com.halo.eventer.domain.duration.api_docs;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.snippet.Attributes.key;

public class DurationDoc {

    public static RestDocumentationResultHandler createDuration() {
        return document(
                "duration/create-duration",
                requestHeaders(headerWithName("Authorization").description("JWT Access 토큰")),
                queryParameters(parameterWithName("festivalId")
                        .description("축제 id")
                        .attributes(key("constraints").value("필수, 1 이상의 양의 정수"))),
                requestFields(
                        fieldWithPath("[].date").type(JsonFieldType.STRING).description("축제 날짜"),
                        fieldWithPath("[].dayNumber").type(JsonFieldType.NUMBER).description("축제 일차 (n일차)")));
    }

    public static RestDocumentationResultHandler getDurations() {
        return document(
                "duration/get-durations",
                queryParameters(parameterWithName("festivalId")
                        .description("축제 id")
                        .attributes(key("constraints").value("필수, 1 이상의 양의 정수"))),
                responseFields(
                        fieldWithPath("[]").description("기간(Duration) 목록"),
                        fieldWithPath("[].durationId")
                                .type(JsonFieldType.NUMBER)
                                .description("기간 식별자(ID)"),
                        fieldWithPath("[].date").type(JsonFieldType.STRING).description("해당 기간 날짜 (yyyy-MM-dd)"),
                        fieldWithPath("[].dayNumber")
                                .type(JsonFieldType.NUMBER)
                                .description("축제 시작일 기준 N번째 날 (1부터 시작)")));
    }
}
