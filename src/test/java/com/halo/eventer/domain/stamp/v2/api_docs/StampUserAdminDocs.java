package com.halo.eventer.domain.stamp.v2.api_docs;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.ResourceSnippetParameters.builder;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class StampUserAdminDocs {

    private static final String TAG = "스탬프투어(v2) 사용자 관리";

    public static RestDocumentationResultHandler listUsers() {
        return document(
                "v2-stampuser-list",
                resource(builder()
                        .tag(TAG)
                        .summary("사용자 목록 조회(검색/필터/정렬/페이지)")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .queryParameters(
                                parameterWithName("q")
                                        .description("검색어(이름/전화번호, 공백이면 전체)")
                                        .optional(),
                                parameterWithName("missionCleared")
                                        .description("미션 완료여부 필터 (ALL|TRUE|FALSE)")
                                        .optional(),
                                parameterWithName("page")
                                        .description("페이지(0-base)")
                                        .optional(),
                                parameterWithName("size").description("페이지 크기").optional(),
                                parameterWithName("sortType")
                                        .description("정렬 기준 (NAME|CREATED_AT)")
                                        .optional())
                        .responseFields(
                                fieldWithPath("content").type(ARRAY).description("사용자 요약 목록"),
                                fieldWithPath("content[].id").type(NUMBER).description("사용자 ID"),
                                fieldWithPath("content[].name").type(STRING).description("이름"),
                                fieldWithPath("content[].phone").type(STRING).description("전화번호"),
                                fieldWithPath("content[].uuid").type(STRING).description("UUID"),
                                // ⚠️ Jackson이 boolean 필드명을 finished 로 직렬화하는 경우가 많음(필드명이 isFinished일 때)
                                fieldWithPath("content[].finished")
                                        .type(BOOLEAN)
                                        .description("투어 완료 여부"),
                                fieldWithPath("pageInfo").type(OBJECT).description("페이지 정보"),
                                fieldWithPath("pageInfo.pageNumber")
                                        .type(NUMBER)
                                        .description("현재 페이지(1-base 문서화 예시)"),
                                fieldWithPath("pageInfo.pageSize").type(NUMBER).description("페이지 크기"),
                                fieldWithPath("pageInfo.totalElements")
                                        .type(NUMBER)
                                        .description("전체 개수"),
                                fieldWithPath("pageInfo.totalPages")
                                        .type(NUMBER)
                                        .description("전체 페이지 수"))
                        .build()));
    }

    public static RestDocumentationResultHandler getUser() {
        return document(
                "v2-stampuser-get",
                resource(builder()
                        .tag(TAG)
                        .summary("사용자 상세 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"),
                                parameterWithName("uuid").description("사용자 UUID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("userId").type(NUMBER).description("사용자 ID"),
                                fieldWithPath("name").type(STRING).description("이름"),
                                fieldWithPath("phone").type(STRING).description("전화번호"),
                                fieldWithPath("uuid").type(STRING).description("UUID"),
                                fieldWithPath("tourFinished").type(BOOLEAN).description("투어 완료 여부"),
                                fieldWithPath("participantCount").type(NUMBER).description("동반 인원 수"),
                                fieldWithPath("extraText")
                                        .type(STRING)
                                        .optional()
                                        .description("추가 입력값(없을 수 있음)"),
                                fieldWithPath("missions").type(ARRAY).description("사용자의 미션 현황"),
                                fieldWithPath("missions[].userMissionId")
                                        .type(NUMBER)
                                        .description("사용자-미션 ID"),
                                fieldWithPath("missions[].missionId")
                                        .type(NUMBER)
                                        .description("미션 ID"),
                                fieldWithPath("missions[].missionTitle")
                                        .type(STRING)
                                        .description("미션 제목"),
                                fieldWithPath("missions[].complete")
                                        .type(BOOLEAN)
                                        .description("완료 여부"))
                        .build()));
    }

    public static RestDocumentationResultHandler setAllMissionsCompletion() {
        return document(
                "v2-stampuser-missions-all",
                resource(builder()
                        .tag(TAG)
                        .summary("사용자 전체 미션 일괄 완료/미완료 처리")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"),
                                parameterWithName("userId").description("사용자 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("complete").type(BOOLEAN).description("true=전체 완료 처리, false=전체 미완료 처리"))
                        .responseFields(
                                fieldWithPath("userId").type(NUMBER).description("사용자 ID"),
                                fieldWithPath("name").type(STRING).description("이름"),
                                fieldWithPath("phone").type(STRING).description("전화번호"),
                                fieldWithPath("uuid").type(STRING).description("UUID"),
                                fieldWithPath("tourFinished").type(BOOLEAN).description("투어 완료 여부"),
                                fieldWithPath("participantCount").type(NUMBER).description("동반 인원 수"),
                                fieldWithPath("extraText")
                                        .type(STRING)
                                        .optional()
                                        .description("추가 입력값(없을 수 있음)"),
                                fieldWithPath("missions").type(ARRAY).description("사용자의 미션 현황"),
                                fieldWithPath("missions[].userMissionId")
                                        .type(NUMBER)
                                        .description("사용자-미션 ID"),
                                fieldWithPath("missions[].missionId")
                                        .type(NUMBER)
                                        .description("미션 ID"),
                                fieldWithPath("missions[].missionTitle")
                                        .type(STRING)
                                        .description("미션 제목"),
                                fieldWithPath("missions[].complete")
                                        .type(BOOLEAN)
                                        .description("완료 여부"))
                        .build()));
    }

    // 공통 에러 문서
    public static RestDocumentationResultHandler error(String id) {
        return document(
                id,
                resource(builder()
                        .tag(TAG)
                        .responseFields(
                                fieldWithPath("code").type(STRING).description("에러 코드"),
                                fieldWithPath("message").type(STRING).description("에러 메시지"),
                                fieldWithPath("status").type(NUMBER).description("HTTP 상태 코드"))
                        .build()));
    }
}
