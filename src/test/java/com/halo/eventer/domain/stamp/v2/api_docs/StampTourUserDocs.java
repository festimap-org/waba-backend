package com.halo.eventer.domain.stamp.v2.api_docs;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.ResourceSnippetParameters.builder;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class StampTourUserDocs {

    private static final String TAG = "스탬프투어(v2) 사용자";

    // --- 회원가입(permitAll)
    public static RestDocumentationResultHandler signup() {
        return document(
                "v2-user-stamptour-signup",
                resource(builder()
                        .tag(TAG)
                        .summary("스탬프투어 사용자 회원가입")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"))
                        .requestFields(
                                fieldWithPath("name").type(STRING).description("이름"),
                                fieldWithPath("phone").type(STRING).description("전화번호"),
                                fieldWithPath("participantCount").type(NUMBER).description("참여 인원 수(>=1)"),
                                fieldWithPath("extraText").type(STRING).description("추가 정보"))
                        .build()));
    }

    // --- 로그인(permitAll) : 응답 헤더 Authorization만 문서화
    public static RestDocumentationResultHandler login() {
        return document(
                "v2-user-stamptour-login",
                resource(builder()
                        .tag(TAG)
                        .summary("스탬프투어 사용자 로그인")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"))
                        .requestFields(
                                fieldWithPath("name").type(STRING).description("이름"),
                                fieldWithPath("phone").type(STRING).description("전화번호"))
                        // 응답 바디 필드는 고정 아님: 헤더만 문서화
                        .responseHeaders(headerWithName("Authorization").description("JWT Access 토큰 (Bearer …)"))
                        .build()));
    }

    public static RestDocumentationResultHandler missionBoard() {
        return document(
                "v2-user-stamptour-mission-board",
                resource(builder()
                        .tag(TAG)
                        .summary("사용자 미션 보드 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("clearCount").type(NUMBER).description("완료한 미션 수"),
                                fieldWithPath("totalCount").type(NUMBER).description("전체 미션 수"),
                                fieldWithPath("finished").type(BOOLEAN).description("전체 완료 여부"),
                                fieldWithPath("missions").type(ARRAY).description("미션 아이콘 목록"),
                                fieldWithPath("missions[].missionId")
                                        .type(NUMBER)
                                        .description("미션 ID"),
                                fieldWithPath("missions[].title").type(STRING).description("미션 제목"),
                                fieldWithPath("missions[].clear").type(BOOLEAN).description("완료 여부"),
                                fieldWithPath("missions[].imageUrl")
                                        .type(STRING)
                                        .description("표시 썸네일 URL"))
                        .build()));
    }

    // --- 미션 상세(ROLE_STAMP)
    public static RestDocumentationResultHandler missionDetails() {
        return document(
                "v2-user-stamptour-mission-details",
                resource(builder()
                        .tag(TAG)
                        .summary("사용자 미션 상세 템플릿 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"),
                                parameterWithName("missionId").description("미션 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("missionId").type(NUMBER).description("미션 ID"),
                                fieldWithPath("title")
                                        .type(STRING)
                                        .optional()
                                        .description("미션 제목(표시 설정에 따라 미표시일 수 있음)"),
                                fieldWithPath("mediaUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("미디어 URL"),
                                fieldWithPath("layout")
                                        .type(STRING)
                                        .description("디자인 레이아웃(enum: CARD, IMAGE_TOP, IMAGE_SIDE, TEXT_ONLY)"),
                                fieldWithPath("userSuccessCount").type(NUMBER).description("사용자 성공 횟수"),
                                fieldWithPath("requiredSuccessCount")
                                        .type(NUMBER)
                                        .optional()
                                        .description("필요 성공 횟수(null일 수 있음)"),
                                fieldWithPath("extraInfos").type(ARRAY).description("추가 정보 목록"),
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
                                fieldWithPath("buttonLayout").type(STRING).description("버튼 레이아웃"),
                                fieldWithPath("buttons").type(ARRAY).description("버튼 목록"),
                                fieldWithPath("buttons[].sequenceIndex")
                                        .type(NUMBER)
                                        .description("표시 순서(0-base)"),
                                fieldWithPath("buttons[].content").type(STRING).description("라벨"),
                                fieldWithPath("buttons[].iconImgUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("아이콘 URL"),
                                fieldWithPath("buttons[].action").type(STRING).description("동작"),
                                fieldWithPath("buttons[].targetUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("대상 URL"))
                        .build()));
    }

    // --- QR 인증(ROLE_STAMP)
    public static RestDocumentationResultHandler verifyByQr() {
        return document(
                "v2-user-stamptour-mission-verify-qr",
                resource(builder()
                        .tag(TAG)
                        .summary("미션 완료 QR 인증")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(fieldWithPath("missionId").type(NUMBER).description("미션 ID"))
                        .build()));
    }

    // --- 경품 수령 QR 정보(ROLE_STAMP)
    public static RestDocumentationResultHandler prizeQr() {
        return document(
                "v2-user-stamptour-prize-qr",
                resource(builder()
                        .tag(TAG)
                        .summary("경품 수령용 QR 정보 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("userName").type(STRING).description("이름"),
                                fieldWithPath("phone").type(STRING).description("전화번호"),
                                fieldWithPath("participateCount").type(NUMBER).description("참여 인원 수"),
                                fieldWithPath("extraText")
                                        .type(STRING)
                                        .optional()
                                        .description("추가 정보"))
                        .build()));
    }

    // 공통 에러
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
