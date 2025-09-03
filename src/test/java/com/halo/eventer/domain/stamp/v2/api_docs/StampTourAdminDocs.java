package com.halo.eventer.domain.stamp.v2.api_docs;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.ResourceSnippetParameters.builder;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class StampTourAdminDocs {

    private static final String TAG = "스탬프투어(v2) 관리자";

    // 생성/목록/삭제
    public static RestDocumentationResultHandler createStampTour() {
        return document(
                "v2-stamptour-create",
                resource(builder()
                        .tag(TAG)
                        .summary("스탬프투어 생성")
                        .pathParameters(parameterWithName("festivalId").description("축제 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("title").type(STRING).description("제목"),
                                fieldWithPath("showStamp").type(BOOLEAN).description("목록 노출 여부"))
                        .build()));
    }

    public static RestDocumentationResultHandler listStampTours() {
        return document(
                "v2-stamptour-list",
                resource(builder()
                        .tag(TAG)
                        .summary("스탬프투어 목록 조회")
                        .pathParameters(parameterWithName("festivalId").description("축제 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("[].stampId").type(NUMBER).description("스탬프 ID"),
                                fieldWithPath("[].title").type(STRING).description("제목"),
                                fieldWithPath("[].showStamp").type(BOOLEAN).description("목록 노출 여부"))
                        .build()));
    }

    public static RestDocumentationResultHandler deleteStampTour() {
        return document(
                "v2-stamptour-delete",
                resource(builder()
                        .tag(TAG)
                        .summary("스탬프투어 삭제")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .build()));
    }

    public static RestDocumentationResultHandler toggleShowStamp() {
        return document(
                "v2-admin-stamptour-toggle-show",
                resource(builder()
                        .tag(TAG)
                        .summary("스탬프투어 노출 토글")
                        .description("해당 스탬프투어의 사용자 앱 노출 여부를 설정합니다.")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("showStamp").type(BOOLEAN).description("노출 여부 (true=보이기, false=숨기기)"))
                        .build()));
    }

    public static RestDocumentationResultHandler getUserSettings() {
        return document(
                "v2-stamptour-user-settings-get",
                resource(builder()
                        .tag(TAG)
                        .summary("유저 설정(참여 인증 방식) 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(fieldWithPath("joinVerificationMethod")
                                .type(STRING)
                                .description("참여 인증 방식(enum: NONE, SMS, PASS)"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateUserSettings() {
        return document(
                "v2-stamptour-user-settings-update",
                resource(builder()
                        .tag(TAG)
                        .summary("유저 설정(참여 인증 방식) 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(fieldWithPath("joinVerificationMethod")
                                .type(STRING)
                                .description("참여 인증 방식(enum: NONE, SMS, PASS)"))
                        .build()));
    }

    // 기본 설정
    public static RestDocumentationResultHandler getBasic() {
        return document(
                "v2-stamptour-basic-get",
                resource(builder()
                        .tag(TAG)
                        .summary("기본 설정 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("stampId").type(NUMBER).description("ID"),
                                fieldWithPath("stampActive").type(BOOLEAN).description("활성화 여부"),
                                fieldWithPath("title").type(STRING).description("제목"),
                                fieldWithPath("authMethod")
                                        .type(STRING)
                                        .description("인증 방식 (TAG_SCAN, USER_CODE_PRESENT)"),
                                fieldWithPath("prizeReceiptAuthPassword")
                                        .type(STRING)
                                        .description("경품 수령 관리자 비밀번호"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateBasic() {
        return document(
                "v2-stamptour-basic-upsert",
                resource(builder()
                        .tag(TAG)
                        .summary("기본 설정 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("stampActivate").type(BOOLEAN).description("활성화"),
                                fieldWithPath("title").type(STRING).description("제목"),
                                fieldWithPath("authMethod")
                                        .type(STRING)
                                        .description("인증 방식 (TAG_SCAN, USER_CODE_PRESENT)"),
                                fieldWithPath("prizeReceiptAuthPassword")
                                        .type(STRING)
                                        .description("경품 수령 관리자 비밀번호"))
                        .build()));
    }

    // 안내사항
    public static RestDocumentationResultHandler getNotice() {
        return document(
                "v2-stamptour-notice-get",
                resource(builder()
                        .tag(TAG)
                        .summary("안내사항 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("cautionContent").type(STRING).description("주의사항"),
                                fieldWithPath("personalInformationContent")
                                        .type(STRING)
                                        .description("개인정보"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateNotice() {
        return document(
                "v2-stamptour-notice-upsert",
                resource(builder()
                        .tag(TAG)
                        .summary("안내사항 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("cautionContent").type(STRING).description("주의사항"),
                                fieldWithPath("personalInformationContent")
                                        .type(STRING)
                                        .description("개인정보"))
                        .build()));
    }

    // 랜딩
    public static RestDocumentationResultHandler getLanding() {
        return document(
                "v2-stamptour-landing-get",
                resource(builder()
                        .tag(TAG)
                        .summary("랜딩 페이지 설정 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("landingPageDesignTemplate")
                                        .type(STRING)
                                        .description("랜딩 페이지 디자인 템플릿 (NONE)"),
                                fieldWithPath("backgroundImgUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("배경 이미지"),
                                fieldWithPath("iconImgUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("아이콘 이미지"),
                                fieldWithPath("description")
                                        .type(STRING)
                                        .optional()
                                        .description("설명"),
                                fieldWithPath("buttonLayout")
                                        .type(STRING)
                                        .description(
                                                "버튼 배치 방법(ONE(1개), TWO_ASYM(2개 비대칭), TWO_SYM(2개 대칭), TWO_UP_DOWN(2개 상하), NONE(없음))"),
                                fieldWithPath("buttons")
                                        .type(ARRAY)
                                        .optional()
                                        .description("버튼 목록, buttonLayout이 NONE인 경우 빈 리스트 반환"),
                                fieldWithPath("buttons[].sequenceIndex")
                                        .type(NUMBER)
                                        .description("순서"),
                                fieldWithPath("buttons[].content").type(STRING).description("라벨"),
                                fieldWithPath("buttons[].iconImgUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("아이콘 URL"),
                                fieldWithPath("buttons[].action")
                                        .type(STRING)
                                        .description("버튼 기능 (QR_CAMERA, NFC_SCAN,OPEN_URL,OPEN_NEW_PAGE, OPEN_POPUP)"),
                                fieldWithPath("buttons[].targetUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("URL, QR 카메라 혹은 NFC 일때는 null"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateLanding() {
        return document(
                "v2-stamptour-landing-upsert",
                resource(builder()
                        .tag(TAG)
                        .summary("랜딩 페이지 설정 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("landingPageDesignTemplate")
                                        .type(STRING)
                                        .description("랜딩 페이지 디자인 템플릿 (NONE)"),
                                fieldWithPath("backgroundImgUrl").type(STRING).description("배경 이미지 URL"),
                                fieldWithPath("iconImgUrl").type(STRING).description("아이콘 이미지 URL"),
                                fieldWithPath("description").type(STRING).description("설명"),
                                fieldWithPath("buttonLayout")
                                        .type(STRING)
                                        .description(
                                                "버튼 배치 방법(ONE(1개), TWO_ASYM(2개 비대칭), TWO_SYM(2개 대칭), TWO_UP_DOWN(2개 상하), NONE(없음))"),
                                fieldWithPath("buttons")
                                        .type(ARRAY)
                                        .optional()
                                        .description("버튼 목록, buttonLayout이 NONE인 경우 빈 리스트로 요청"),
                                fieldWithPath("buttons[].sequenceIndex")
                                        .type(NUMBER)
                                        .description("순서"),
                                fieldWithPath("buttons[].iconImgUrl")
                                        .type(STRING)
                                        .description("아이콘"),
                                fieldWithPath("buttons[].content").type(STRING).description("라벨"),
                                fieldWithPath("buttons[].action")
                                        .type(STRING)
                                        .description("버튼 기능 (QR_CAMERA, NFC_SCAN,OPEN_URL,OPEN_NEW_PAGE, OPEN_POPUP)"),
                                fieldWithPath("buttons[].targetUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("URL, QR 카메라 혹은 NFC 일때는 null"))
                        .build()));
    }

    // 메인
    public static RestDocumentationResultHandler getMain() {
        return document(
                "v2-stamptour-main-get",
                resource(builder()
                        .tag(TAG)
                        .summary("메인 페이지 설정 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("mainPageDesignTemplate")
                                        .type(STRING)
                                        .description("메인 페이지 디자인 템플릿(GRID_Nx1, GRID_Nx2, GRID_Nx3)"),
                                fieldWithPath("backgroundImgUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("배경 이미지"),
                                fieldWithPath("buttonLayout")
                                        .type(STRING)
                                        .description(
                                                "버튼 배치 방법(ONE(1개), TWO_ASYM(2개 비대칭), TWO_SYM(2개 대칭), TWO_UP_DOWN(2개 상하), NONE(없음))"),
                                fieldWithPath("buttons").type(ARRAY).optional().description("버튼 목록"),
                                fieldWithPath("buttons[].sequenceIndex")
                                        .type(NUMBER)
                                        .description("순서"),
                                fieldWithPath("buttons[].iconImgUrl")
                                        .type(STRING)
                                        .description("아이콘 (요청 필드)"),
                                fieldWithPath("buttons[].content").type(STRING).description("라벨"),
                                fieldWithPath("buttons[].action")
                                        .type(STRING)
                                        .description("버튼 기능 (QR_CAMERA, NFC_SCAN, OPEN_URL,OPEN_NEW_PAGE, OPEN_POPUP)"),
                                fieldWithPath("buttons[].targetUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("URL, QR 카메라 혹은 NFC 일때는 null"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateMain() {
        return document(
                "v2-stamptour-main-upsert",
                resource(builder()
                        .tag(TAG)
                        .summary("메인 페이지 설정 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("mainPageDesignTemplate")
                                        .type(STRING)
                                        .description("메인 페이지 디자인 템플릿(GRID_Nx1, GRID_Nx2, GRID_Nx3)"),
                                fieldWithPath("backgroundImgUrl").type(STRING).description("배경 이미지 URL"),
                                fieldWithPath("buttonLayout")
                                        .type(STRING)
                                        .description(
                                                "버튼 배치 방법(ONE(1개), TWO_ASYM(2개 비대칭), TWO_SYM(2개 대칭), TWO_UP_DOWN(2개 상하), NONE(없음))"),
                                fieldWithPath("buttons").type(ARRAY).optional().description("버튼 목록"),
                                fieldWithPath("buttons[].sequenceIndex")
                                        .type(NUMBER)
                                        .description("순서"),
                                fieldWithPath("buttons[].iconImgUrl")
                                        .type(STRING)
                                        .description("아이콘"),
                                fieldWithPath("buttons[].content").type(STRING).description("라벨"),
                                fieldWithPath("buttons[].action")
                                        .type(STRING)
                                        .description("버튼 기능 (QR_CAMERA, NFC_SCAN,OPEN_URL,OPEN_NEW_PAGE, OPEN_POPUP)"),
                                fieldWithPath("buttons[].targetUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("URL, QR 카메라 혹은 NFC 일때는 null"))
                        .build()));
    }

    // 참여가이드
    public static RestDocumentationResultHandler getGuide() {
        return document(
                "v2-stamptour-guide-get",
                resource(builder()
                        .tag(TAG)
                        .summary("참여가이드 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("guideDesignTemplate")
                                        .type(STRING)
                                        .description("참여 방법 안내 디자인 템플릿 (FULL, NON_FULL)"),
                                fieldWithPath("guideSlideMethod").type(STRING).description("슬라이드 방식 (SLIDE)"),
                                fieldWithPath("participateGuidePages")
                                        .type(ARRAY)
                                        .optional()
                                        .description("참여 방법 페이지들 간략 요소 (페이지 없으면 빈 리스트 반환)"),
                                fieldWithPath("participateGuidePages[].pageId")
                                        .type(NUMBER)
                                        .description("페이지 ID"),
                                fieldWithPath("participateGuidePages[].title")
                                        .type(STRING)
                                        .description("제목"),
                                fieldWithPath("participateGuidePages[].displayOrder")
                                        .type(NUMBER)
                                        .description("순서"))
                        .build()));
    }

    public static RestDocumentationResultHandler upsertGuide() {
        return document(
                "v2-stamptour-guide-upsert",
                resource(builder()
                        .tag(TAG)
                        .summary("참여가이드 생성/수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("guideDesignTemplate")
                                        .type(STRING)
                                        .description("참여 방법 안내 디자인 템플릿 (FULL, NON_FULL)"),
                                fieldWithPath("guideSlideMethod").type(STRING).description("슬라이드 방식 (SLIDE)"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateDisplayOrder() {
        return document(
                "v2-stamptour-guide-pages-order",
                resource(builder()
                        .tag(TAG)
                        .summary("참여가이드 페이지 순서 수정(일괄)")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("[].id").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("[].displayOrder").type(NUMBER).description("표시 순서"))
                        .responseFields(
                                fieldWithPath("[].pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("[].title").type(STRING).description("제목"),
                                fieldWithPath("[].displayOrder").type(NUMBER).description("표시 순서"))
                        .build()));
    }

    // 참여가이드 페이지
    public static RestDocumentationResultHandler createGuidePage() {
        return document(
                "v2-stamptour-guide-page-create",
                resource(builder()
                        .tag(TAG)
                        .summary("참여가이드 페이지 생성")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("title").type(STRING).description("제목"),
                                fieldWithPath("mediaSpec")
                                        .type(STRING)
                                        .description("미디어 스펙 ( NONE, ONE_TO_ONE, SIXTEEN_TO_NINE, RATIO_NO_CHANGE )"),
                                fieldWithPath("mediaUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("미디어 URL"),
                                fieldWithPath("summary").type(STRING).description("요약"),
                                fieldWithPath("details").type(STRING).description("상세"),
                                fieldWithPath("additional").type(STRING).description("추가"))
                        .build()));
    }

    public static RestDocumentationResultHandler getGuidePage() {
        return document(
                "v2-stamptour-guide-page-get",
                resource(builder()
                        .tag(TAG)
                        .summary("참여가이드 페이지 상세 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"),
                                parameterWithName("pageId").description("페이지 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("title").type(STRING).description("제목"),
                                fieldWithPath("mediaSpec")
                                        .type(STRING)
                                        .description("미디어 스펙( NONE, ONE_TO_ONE, SIXTEEN_TO_NINE, RATIO_NO_CHANGE )"),
                                fieldWithPath("mediaUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("미디어 URL"),
                                fieldWithPath("summary").type(STRING).description("요약"),
                                fieldWithPath("details").type(STRING).description("상세"),
                                fieldWithPath("additional").type(STRING).description("추가"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateGuidePage() {
        return document(
                "v2-stamptour-guide-page-update",
                resource(builder()
                        .tag(TAG)
                        .summary("참여가이드 페이지 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"),
                                parameterWithName("pageId").description("페이지 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("title").type(STRING).description("제목"),
                                fieldWithPath("mediaSpec")
                                        .type(STRING)
                                        .description("미디어 스펙 ( NONE, ONE_TO_ONE, SIXTEEN_TO_NINE, RATIO_NO_CHANGE )"),
                                fieldWithPath("mediaUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("미디어 URL"),
                                fieldWithPath("summary").type(STRING).description("요약"),
                                fieldWithPath("details").type(STRING).description("상세"),
                                fieldWithPath("additional").type(STRING).description("추가"))
                        .build()));
    }

    public static RestDocumentationResultHandler deleteGuidePage() {
        return document(
                "v2-stamptour-guide-page-delete",
                resource(builder()
                        .tag(TAG)
                        .summary("참여가이드 페이지 삭제")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampId").description("스탬프투어 ID"),
                                parameterWithName("pageId").description("페이지 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .build()));
    }

    public static RestDocumentationResultHandler getStampActive() {
        return document(
                "v2-stamptour-active-get",
                resource(builder()
                        .tag(TAG)
                        .summary("스탬프 활성화 조회")
                        .description("특정 스탬프투어의 제목 및 활성화 여부를 조회합니다.")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampId").description("스탬프투어 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("title").type(STRING).description("스탬프 이름"),
                                fieldWithPath("active").type(BOOLEAN).description("활성화 여부"))
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
