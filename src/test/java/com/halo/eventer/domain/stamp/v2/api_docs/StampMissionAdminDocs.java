package com.halo.eventer.domain.stamp.v2.api_docs;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.ResourceSnippetParameters.builder;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class StampMissionAdminDocs {

    private static final String TAG = "스탬프투어 미션(v2)";

    // ---------- 미션 생성/목록 ----------
    public static RestDocumentationResultHandler createMission() {
        return document(
                "v2-mission-create",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 생성")
                        .description("스탬프투어에 신규 미션을 생성합니다.")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(fieldWithPath("name").type(STRING).description("미션 이름"))
                        .build()));
    }

    public static RestDocumentationResultHandler listMissions() {
        return document(
                "v2-mission-list",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 목록 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        // 응답 필드는 DTO 확정 후 추가 가능
                        .build()));
    }

    // ---------- 기본 설정 ----------
    public static RestDocumentationResultHandler getBasicSettings() {
        return document(
                "v2-mission-basic-get",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 기본 설정 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .build()));
    }

    public static RestDocumentationResultHandler upsertBasicSettings() {
        return document(
                "v2-mission-basic-upsert",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 기본 설정 수정/생성")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("missionCount").type(NUMBER).description("미션 총 개수(2~16)"),
                                fieldWithPath("defaultDetailLayout")
                                        .type(STRING)
                                        .description("기본 상세 레이아웃(enum)"))
                        .build()));
    }

    // ---------- 리워드(상품) ----------
    public static RestDocumentationResultHandler createPrize() {
        return document(
                "v2-mission-prize-create",
                resource(builder()
                        .tag(TAG)
                        .summary("리워드(상품) 추가")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("requiredCount").type(NUMBER).description("필요 스탬프 수"),
                                fieldWithPath("prizeDescription").type(STRING).description("상품 설명"))
                        .build()));
    }

    public static RestDocumentationResultHandler updatePrize() {
        return document(
                "v2-mission-prize-update",
                resource(builder()
                        .tag(TAG)
                        .summary("리워드(상품) 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"),
                                parameterWithName("prizeId").description("상품 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("requiredCount").type(NUMBER).description("필요 스탬프 수"),
                                fieldWithPath("prizeDescription").type(STRING).description("상품 설명"))
                        .build()));
    }

    public static RestDocumentationResultHandler deletePrize() {
        return document(
                "v2-mission-prize-delete",
                resource(builder()
                        .tag(TAG)
                        .summary("리워드(상품) 삭제")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"),
                                parameterWithName("prizeId").description("상품 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .build()));
    }

    // ---------- 미션 상세설정 ----------
    public static RestDocumentationResultHandler getMissionDetails() {
        return document(
                "v2-mission-details-get",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 상세 설정 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"),
                                parameterWithName("missionId").description("미션 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .build()));
    }

    public static RestDocumentationResultHandler upsertMissionDetails() {
        return document(
                "v2-mission-details-upsert",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 상세 설정 업서트")
                        .description("미션 상세 페이지의 템플릿/추가정보/버튼 구성을 한 번에 갱신합니다.")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"),
                                parameterWithName("missionId").description("미션 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("layout")
                                        .type(STRING)
                                        .description("세부 디자인 레이아웃(enum: CARD, IMAGE_TOP, IMAGE_SIDE, TEXT_ONLY)"),
                                fieldWithPath("missionTitle").type(STRING).description("미션 제목"),
                                fieldWithPath("showMissionName").type(BOOLEAN).description("제목 표시 여부"),
                                fieldWithPath("showSuccessCount").type(BOOLEAN).description("성공 횟수(기준) 표시 여부"),
                                fieldWithPath("showExtraInfos").type(BOOLEAN).description("추가정보 영역 표시 여부"),
                                fieldWithPath("showButtons").type(BOOLEAN).description("버튼 영역 표시 여부"),
                                fieldWithPath("missionMediaSpec")
                                        .type(STRING)
                                        .description(
                                                "미디어 스펙(enum: NONE, ONE_TO_ONE, SIXTEEN_TO_NINE, RATIO_NO_CHANGE)"),
                                fieldWithPath("mediaUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("미디어 URL"),
                                fieldWithPath("extraInfoType")
                                        .type(STRING)
                                        .optional()
                                        .description("추가정보 레이아웃(enum: LIST)"),
                                fieldWithPath("extraInfos")
                                        .type(ARRAY)
                                        .optional()
                                        .description("추가정보 항목 배열(타입에 따라 페이로드 달라짐)"),
                                fieldWithPath("extraInfos[].titleText")
                                        .type(STRING)
                                        .optional()
                                        .description("항목 제목"),
                                fieldWithPath("extraInfos[].bodyText")
                                        .type(STRING)
                                        .optional()
                                        .description("본문/설명"),
                                fieldWithPath("extraInfos[].freeText")
                                        .type(STRING)
                                        .optional()
                                        .description("자유 텍스트"),
                                fieldWithPath("extraInfos[].displayOrder")
                                        .type(NUMBER)
                                        .optional()
                                        .description("표시 순서"),
                                fieldWithPath("extraInfos[].images")
                                        .type(ARRAY)
                                        .optional()
                                        .description("이미지 리스트"),
                                fieldWithPath("extraInfos[].images[].imageUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("이미지 URL"),
                                fieldWithPath("extraInfos[].images[].displayOrder")
                                        .type(NUMBER)
                                        .optional()
                                        .description("이미지 표시 순서"),
                                fieldWithPath("buttonLayout")
                                        .type(STRING)
                                        .description("버튼 레이아웃(enum: ONE, TWO_ASYM, TWO_SYM, TWO_UP_DOWN, NONE)"),
                                fieldWithPath("buttons").type(ARRAY).optional().description("버튼 목록"),
                                fieldWithPath("buttons[].sequenceIndex")
                                        .type(NUMBER)
                                        .description("버튼 순서(0-base)"),
                                fieldWithPath("buttons[].iconImg")
                                        .type(STRING)
                                        .optional()
                                        .description("아이콘 이미지"),
                                fieldWithPath("buttons[].content").type(STRING).description("버튼 라벨"),
                                fieldWithPath("buttons[].action").type(STRING).description("동작(enum: OPEN_URL 등)"),
                                fieldWithPath("buttons[].targetUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("OPEN_URL 대상 URL"))
                        .build()));
    }

    // ---------- 완료 이미지 / QR ----------
    public static RestDocumentationResultHandler getMissionClearImg() {
        return document(
                "v2-mission-clearimg-get",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 완료 이미지 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"),
                                parameterWithName("missionId").description("미션 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateMissionClearImg() {
        return document(
                "v2-mission-clearimg-update",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 완료 이미지 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"),
                                parameterWithName("missionId").description("미션 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(fieldWithPath("imageUrl").type(STRING).description("완료 이미지 URL"))
                        .build()));
    }

    public static RestDocumentationResultHandler getQrData() {
        return document(
                "v2-mission-qr-get",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 QR 데이터 일괄 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        // 응답 필드는 DTO 확정 후 추가
                        .build()));
    }

    // ---------- 에러 ----------
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
