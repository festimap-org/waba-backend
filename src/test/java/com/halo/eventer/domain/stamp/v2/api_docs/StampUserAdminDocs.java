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
                                parameterWithName("finished")
                                        .description("투어 완료 여부 필터 (ALL|TRUE|FALSE)")
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
                                fieldWithPath("content[].finished")
                                        .type(BOOLEAN)
                                        .description("투어 완료 여부"),
                                fieldWithPath("content[].createdAt")
                                        .type(STRING)
                                        .description("생성 시각 (ISO-8601, 예: 2025-09-01T10:15:30)"),
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
                                parameterWithName("userId").description("사용자 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("name").type(STRING).description("이름"),
                                fieldWithPath("phone").type(STRING).description("전화번호"),
                                fieldWithPath("uuid").type(STRING).description("UUID"),
                                fieldWithPath("receivedPrizeName").type(STRING).description("수령한 상품 이름"),
                                fieldWithPath("finished").type(BOOLEAN).description("투어 완료 여부"),
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
                                fieldWithPath("missions[].clear").type(BOOLEAN).description("완료 여부"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateTourFinish() {
        return document(
                "v2-stampuser-tour-finish",
                resource(builder()
                        .tag(TAG)
                        .summary("사용자 투어 완료 처리 및 경품 정보 업데이트")
                        .description("특정 사용자의 투어 완료 상태를 업데이트하고, 경품 정보를 설정합니다.")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"),
                                parameterWithName("userId").description("사용자 ID (>=1)"))
                        .requestFields(
                                fieldWithPath("finished").type(BOOLEAN).description("투어 완료 여부"),
                                fieldWithPath("prizeName")
                                        .type(STRING)
                                        .optional()
                                        .description("선택한 경품 이름 (선택 사항)"))
                        .build()));
    }

    public static RestDocumentationResultHandler getStampUser() {
        return document(
                "v2-stampuser-get",
                resource(builder()
                        .tag(TAG)
                        .summary("사용자 상세 조회")
                        .description("특정 스탬프투어에 참여한 사용자의 정보를 상세히 조회합니다.")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프 ID (>=1)"),
                                parameterWithName("userId").description("사용자 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access Token"))
                        .responseFields(
                                fieldWithPath("name").type(STRING).description("이름"),
                                fieldWithPath("phone").type(STRING).description("전화번호"),
                                fieldWithPath("uuid").type(STRING).description("UUID"),
                                fieldWithPath("tourFinished").type(BOOLEAN).description("투어 완료 여부"),
                                fieldWithPath("participantCount").type(NUMBER).description("동반 인원 수"),
                                fieldWithPath("extraText")
                                        .type(STRING)
                                        .optional()
                                        .description("추가 입력값"),
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
                                fieldWithPath("missions[].clear").type(BOOLEAN).description("미션 완료 여부"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateUserMission() {
        return document(
                "v2-stampuser-user-mission-update",
                resource(builder()
                        .tag(TAG)
                        .summary("특정 사용자-미션 완료 여부 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"),
                                parameterWithName("userId").description("사용자 ID (>=1)"),
                                parameterWithName("userMissionId").description("사용자-미션 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(fieldWithPath("clear").type(BOOLEAN).description("완료 여부 (true: 완료, false: 미완료)"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateUserInfo() {
        return document(
                "v2-stampuser-update-info",
                resource(builder()
                        .tag(TAG)
                        .summary("사용자 정보 수정")
                        .description("관리자가 특정 사용자에 대해 이름/전화번호/동반 인원 수/추가 텍스트를 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"),
                                parameterWithName("userId").description("사용자 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰 (ADMIN)"))
                        .requestFields(
                                fieldWithPath("name").type(STRING).optional().description("이름(선택, 부분 수정)"),
                                fieldWithPath("phone").type(STRING).optional().description("전화번호(선택, 부분 수정)"),
                                fieldWithPath("participantCount")
                                        .type(NUMBER)
                                        .optional()
                                        .description("동반 인원 수(선택, 부분 수정)"),
                                fieldWithPath("extraText")
                                        .type(STRING)
                                        .optional()
                                        .description("추가 입력값/비고(선택, 부분 수정)"))
                        .build()));
    }

    public static RestDocumentationResultHandler getUserIdByUuid() {
        return document(
                "v2-stampuser-get-id-by-uuid",
                resource(builder()
                        .tag(TAG)
                        .summary("UUID로 사용자 ID 조회")
                        .description("uuid를 전달하면 해당 사용자의 내부 userId를 반환합니다.")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"),
                                parameterWithName("uuid").description("스탬프투어 유저 uuid"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰 (ADMIN)"))
                        .responseFields(fieldWithPath("userId").type(NUMBER).description("사용자 ID"))
                        .build()));
    }

    public static RestDocumentationResultHandler listAllUsers() {
        return document(
                "v2-stampuser-all",
                resource(builder()
                        .tag(TAG)
                        .summary("전체 사용자 목록(간단 정보) 조회")
                        .description("특정 스탬프투어의 모든 사용자 간략 정보를 배열로 반환합니다.")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰 (ADMIN)"))
                        .responseFields(
                                fieldWithPath("[].id").type(NUMBER).description("사용자 ID"),
                                fieldWithPath("[].name").type(STRING).description("이름(복호화된 값일 수도 있음)"),
                                fieldWithPath("[].phone").type(STRING).description("전화번호(복호화된 값일 수도 있음)"),
                                fieldWithPath("[].uuid").type(STRING).description("UUID"),
                                fieldWithPath("[].finished").type(BOOLEAN).description("투어 완료 여부"),
                                fieldWithPath("[].participantCount")
                                        .type(NUMBER)
                                        .description("동반 인원 수"),
                                fieldWithPath("[].receivedPrizeName")
                                        .type(STRING)
                                        .description("수령 상품 명"),
                                fieldWithPath("[].createdAt").type(STRING).description("생성 시각 (ISO-8601)"))
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
