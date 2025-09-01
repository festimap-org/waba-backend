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

    private static final String TAG = "스탬프투어(v2) 관리자 미션";

    // ------ 미션 생성/목록 ------
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
                        .requestFields(
                                fieldWithPath("title").type(STRING).description("미션 이름"),
                                fieldWithPath("showMission").type(BOOLEAN).description("미션 공개 여부"))
                        .build()));
    }

    public static RestDocumentationResultHandler deleteMission() {
        return document(
                "v2-mission-delete",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 삭제")
                        .description("스탬프투어에서 특정 미션을 삭제합니다.")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"),
                                parameterWithName("missionId").description("미션 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
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
                        .responseFields(
                                fieldWithPath("missionCount").type(NUMBER).description("최대 미션 개수 제한"),
                                fieldWithPath("missionList").type(ARRAY).description("미션 목록(비어있을 수 있음)"),
                                fieldWithPath("missionList[].missionId")
                                        .type(NUMBER)
                                        .optional()
                                        .description("미션 ID"),
                                fieldWithPath("missionList[].title")
                                        .type(STRING)
                                        .optional()
                                        .description("제목"),
                                fieldWithPath("missionList[].showMission")
                                        .type(BOOLEAN)
                                        .optional()
                                        .description("미션 공개 여부"),
                                fieldWithPath("missionList[].showMissionTitle")
                                        .type(BOOLEAN)
                                        .optional()
                                        .description("미션 제목 공개 여부"))
                        .build()));
    }

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
                        .responseFields(
                                fieldWithPath("missionCount").type(NUMBER).description("미션 총 개수"),
                                fieldWithPath("missionDetailsDesignLayout")
                                        .type(STRING)
                                        .description("기본 상세 레이아웃"),
                                fieldWithPath("prizes").type(ARRAY).optional().description("경품 목록"),
                                fieldWithPath("prizes[].id").type(NUMBER).description("상품 ID"),
                                fieldWithPath("prizes[].requiredCount")
                                        .type(NUMBER)
                                        .description("필요 스탬프 수"),
                                fieldWithPath("prizes[].prizeDescription")
                                        .type(STRING)
                                        .description("경품 설명"))
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
                                fieldWithPath("missionDetailsDesignLayout")
                                        .type(STRING)
                                        .description("기본 상세 레이아웃(   CARD, IMAGE_TOP, IMAGE_SIDE, TEXT_ONLY)"))
                        .build()));
    }

    public static RestDocumentationResultHandler toggleMissionShow() {
        return document(
                "v2-mission-show-toggle",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 표시 여부 토글")
                        .description("해당 미션의 사용자 노출 여부를 on/off 합니다.")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"),
                                parameterWithName("missionId").description("미션 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("showMission").type(BOOLEAN).description("true=표시, false=숨김"))
                        .build()));
    }

    // ------ 리워드(상품) ------
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

    // ------ 미션 상세 설정 ------
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
                        .responseFields(
                                fieldWithPath("missionDetailsDesignLayout")
                                        .type(STRING)
                                        .description("디자인 레이아웃"),
                                fieldWithPath("showMissionTitle").type(BOOLEAN).description("제목 표시"),
                                fieldWithPath("missionTitle").type(STRING).description("미션 제목"),
                                fieldWithPath("showSuccessCount").type(BOOLEAN).description("성공횟수 표시"),
                                fieldWithPath("mediaSpec")
                                        .type(STRING)
                                        .description("미디어 스펙 (NONE, ONE_TO_ONE, SIXTEEN_TO_NINE, RATIO_NO_CHANGE)"),
                                fieldWithPath("mediaUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("미디어 URL"),
                                fieldWithPath("showExtraInfos").type(BOOLEAN).description("추가정보 표시"),
                                fieldWithPath("extraInfoType")
                                        .type(STRING)
                                        .optional()
                                        .description("추가정보 레이아웃 (LIST)"),
                                fieldWithPath("extraInfos").type(ARRAY).description("추가정보 요약 목록"),
                                fieldWithPath("extraInfos[].extraInfoId")
                                        .type(NUMBER)
                                        .description("추가정보 ID"),
                                fieldWithPath("extraInfos[].titleText")
                                        .type(STRING)
                                        .optional()
                                        .description("제목"),
                                fieldWithPath("extraInfos[].bodyText")
                                        .type(STRING)
                                        .optional()
                                        .description("본문"),
                                fieldWithPath("showButtons").type(BOOLEAN).description("버튼 표시"),
                                fieldWithPath("buttonLayout")
                                        .type(STRING)
                                        .description("버튼 레이아웃 (ONE,TWO_ASYM, TWO_SYM, TWO_UP_DOWN, NONE)"),
                                fieldWithPath("buttons").type(ARRAY).description("버튼 목록"),
                                fieldWithPath("buttons[].sequenceIndex")
                                        .type(NUMBER)
                                        .description("순서(0-base)"),
                                fieldWithPath("buttons[].content").type(STRING).description("라벨"),
                                fieldWithPath("buttons[].iconImgUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("아이콘 URL"),
                                fieldWithPath("buttons[].action")
                                        .type(STRING)
                                        .description("동작 ( QR_CAMERA, NFC_SCAN, OPEN_URL, OPEN_NEW_PAGE, OPEN_POPUP)"),
                                fieldWithPath("buttons[].targetUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("URL"))
                        .build()));
    }

    public static RestDocumentationResultHandler upsertMissionDetails() {
        return document(
                "v2-mission-details-upsert",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 상세 설정 업서트")
                        .description("템플릿/추가정보/버튼을 일괄 갱신합니다.")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"),
                                parameterWithName("missionId").description("미션 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("missionDetailsDesignLayout")
                                        .type(STRING)
                                        .description("디자인 레이아웃"),
                                fieldWithPath("missionTitle").type(STRING).description("미션 제목"),
                                fieldWithPath("showMissionTitle").type(BOOLEAN).description("제목 표시"),
                                fieldWithPath("showSuccessCount").type(BOOLEAN).description("성공횟수 표시"),
                                fieldWithPath("showExtraInfos").type(BOOLEAN).description("추가정보 표시"),
                                fieldWithPath("showButtons").type(BOOLEAN).description("버튼 표시"),
                                fieldWithPath("mediaSpec").type(STRING).description("미디어 스펙"),
                                fieldWithPath("mediaUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("미디어 URL"),
                                fieldWithPath("extraInfoType")
                                        .type(STRING)
                                        .optional()
                                        .description("추가정보 레이아웃"),
                                fieldWithPath("extraInfos")
                                        .type(ARRAY)
                                        .optional()
                                        .description("추가정보 배열"),
                                fieldWithPath("extraInfos[].titleText")
                                        .type(STRING)
                                        .optional()
                                        .description("제목"),
                                fieldWithPath("extraInfos[].bodyText")
                                        .type(STRING)
                                        .optional()
                                        .description("본문"),
                                fieldWithPath("buttonLayout").type(STRING).description("버튼 레이아웃"),
                                fieldWithPath("buttons").type(ARRAY).optional().description("버튼 목록"),
                                fieldWithPath("buttons[].sequenceIndex")
                                        .type(NUMBER)
                                        .description("순서(0-base)"),
                                fieldWithPath("buttons[].iconImgUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("아이콘 이미지"),
                                fieldWithPath("buttons[].content").type(STRING).description("라벨"),
                                fieldWithPath("buttons[].action").type(STRING).description("동작"),
                                fieldWithPath("buttons[].targetUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("URL(OPEN_URL)"))
                        .build()));
    }

    // ------ 완료 이미지 / QR ------
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
                        .responseFields(
                                fieldWithPath("showMissionTitle").type(BOOLEAN).description("제목 표시"),
                                fieldWithPath("clearedThumbnail")
                                        .type(STRING)
                                        .optional()
                                        .description("완료 썸네일"),
                                fieldWithPath("notClearedThumbnail")
                                        .type(STRING)
                                        .optional()
                                        .description("미완료 썸네일"))
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
                        .requestFields(
                                fieldWithPath("showMissionTitle").type(BOOLEAN).description("제목 표시"),
                                fieldWithPath("clearedThumbnail")
                                        .type(STRING)
                                        .optional()
                                        .description("완료 썸네일"),
                                fieldWithPath("notClearedThumbnail")
                                        .type(STRING)
                                        .optional()
                                        .description("미완료 썸네일"))
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
                        .responseFields(
                                fieldWithPath("[].missionId").type(NUMBER).description("미션 ID"),
                                fieldWithPath("[].title").type(STRING).description("미션 제목"))
                        .build()));
    }

    public static RestDocumentationResultHandler getPrizeList() {
        return document(
                "v2-stamptour-prize-list",
                resource(builder()
                        .tag(TAG)
                        .summary("경품(미션 보상) 목록 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("[]").type(ARRAY).description("경품 목록(비어있을 수 있음)"),
                                fieldWithPath("[].id").type(NUMBER).description("경품 ID"),
                                fieldWithPath("[].requiredCount").type(NUMBER).description("필요 성공 미션 수"),
                                fieldWithPath("[].prizeDescription")
                                        .type(STRING)
                                        .description("설명"))
                        .build()));
    }

    // ------ 에러 ------
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
