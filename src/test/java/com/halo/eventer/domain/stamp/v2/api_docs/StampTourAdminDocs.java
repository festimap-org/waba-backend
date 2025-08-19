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

    private static final String TAG = "스탬프투어(v2) 관리";

    public static RestDocumentationResultHandler createStampTour() {
        return document(
                "v2-stamptour-create",
                resource(builder()
                        .tag(TAG)
                        .summary("스탬프투어 생성")
                        .description("festivalId 에 새로운 스탬프투어를 생성합니다.")
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .pathParameters(parameterWithName("festivalId").description("축제 ID (>=1)"))
                        .requestFields(fieldWithPath("title").type(STRING).description("스탬프투어 제목"))
                        .build()));
    }

    public static RestDocumentationResultHandler listStampTours() {
        return document(
                "v2-stamptour-list",
                resource(builder()
                        .tag(TAG)
                        .summary("스탬프투어 목록 조회")
                        .pathParameters(parameterWithName("festivalId").description("축제 ID (>=1)"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("[].stampTourId").type(NUMBER).description("ID"),
                                fieldWithPath("[].title").type(STRING).description("제목"),
                                fieldWithPath("[].stampOn").type(BOOLEAN).description("활성화 여부"))
                        .build()));
    }

    public static RestDocumentationResultHandler getBasic() {
        return document(
                "v2-stamptour-basic-get",
                resource(builder()
                        .tag(TAG)
                        .summary("기본 설정 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampTourId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("stampTourId").type(NUMBER).description("ID"),
                                fieldWithPath("title").type(STRING).description("제목"),
                                fieldWithPath("authenticationMethod")
                                        .type(STRING)
                                        .optional()
                                        .description("인증 방식"),
                                fieldWithPath("boothAdminPassword").type(STRING).description("부스 관리자 비밀번호"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateBasic() {
        return document(
                "v2-stamptour-basic-update",
                resource(builder()
                        .tag(TAG)
                        .summary("기본 설정 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampTourId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("newTitle").type(STRING).description("새 제목"),
                                fieldWithPath("activation").type(BOOLEAN).description("활성화 여부"),
                                fieldWithPath("authMethod").type(STRING).description("인증 방식"),
                                fieldWithPath("boothPassword").type(STRING).description("부스 관리자 비밀번호"))
                        .build()));
    }

    public static RestDocumentationResultHandler getNotice() {
        return document(
                "v2-stamptour-notice-get",
                resource(builder()
                        .tag(TAG)
                        .summary("안내사항 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampTourId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("cautionContent").type(STRING).description("주의사항"),
                                fieldWithPath("personalInformationContent")
                                        .type(STRING)
                                        .description("개인정보 안내"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateNotice() {
        return document(
                "v2-stamptour-notice-update",
                resource(builder()
                        .tag(TAG)
                        .summary("안내사항 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampTourId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("cautionContent").type(STRING).description("주의사항"),
                                fieldWithPath("personalInformationContent")
                                        .type(STRING)
                                        .description("개인정보 안내"))
                        .build()));
    }

    public static RestDocumentationResultHandler getLanding() {
        return document(
                "v2-stamptour-landing-get",
                resource(builder()
                        .tag(TAG)
                        .summary("랜딩 페이지 설정 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampTourId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("designTemplate").type(STRING).description("디자인 템플릿"),
                                fieldWithPath("backgroundImg")
                                        .type(STRING)
                                        .optional()
                                        .description("배경 이미지"),
                                fieldWithPath("iconImg").type(STRING).optional().description("아이콘 이미지"),
                                fieldWithPath("description")
                                        .type(STRING)
                                        .optional()
                                        .description("설명"),
                                fieldWithPath("buttonLayout").type(STRING).description("버튼 배치"),
                                fieldWithPath("buttons").type(ARRAY).description("버튼 목록"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateLanding() {
        return document(
                "v2-stamp-tour-landing-update",
                resource(builder()
                        .tag(TAG)
                        .summary("랜딩 페이지 설정 수정")
                        .description("스탬프투어의 랜딩 페이지 설정을 갱신합니다.")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampTourId").description("스탬프투어 ID"))
                        .requestFields(
                                fieldWithPath("designTemplate")
                                        .type(STRING)
                                        .description("랜딩 디자인 템플릿 (enum: NONE, ...)"),
                                fieldWithPath("backgroundImg").type(STRING).description("배경 이미지 URL"),
                                fieldWithPath("iconImg").type(STRING).description("아이콘 이미지 URL"),
                                fieldWithPath("description").type(STRING).description("설명 텍스트"),
                                fieldWithPath("buttonLayout")
                                        .type(STRING)
                                        .description("버튼 레이아웃 (enum: ONE, TWO_ASYM, TWO_SYM, NONE)"),
                                fieldWithPath("buttons").type(ARRAY).description("버튼 목록(레이아웃이 NONE이면 빈 배열이어야 함)"),
                                fieldWithPath("buttons[].sequenceIndex")
                                        .type(NUMBER)
                                        .description("버튼 순서 인덱스(0-base)"),
                                fieldWithPath("buttons[].iconImg").type(STRING).description("버튼 아이콘 이미지"),
                                fieldWithPath("buttons[].content").type(STRING).description("버튼 표시 텍스트"),
                                fieldWithPath("buttons[].action")
                                        .type(STRING)
                                        .description("버튼 동작 (enum: OPEN_URL, ...)"),
                                fieldWithPath("buttons[].targetUrl")
                                        .type(STRING)
                                        .description("OPEN_URL일 때 이동할 URL"))
                        .build()));
    }

    public static RestDocumentationResultHandler getMain() {
        return document(
                "v2-stamptour-main-get",
                resource(builder()
                        .tag(TAG)
                        .summary("메인 페이지 설정 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampTourId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("designTemplate").type(STRING).description("디자인 템플릿"),
                                fieldWithPath("backgroundImg")
                                        .type(STRING)
                                        .optional()
                                        .description("배경 이미지"),
                                fieldWithPath("buttonLayout").type(STRING).description("버튼 배치"),
                                fieldWithPath("buttons").type(ARRAY).description("버튼 목록"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateMain() {
        return document(
                "v2-stamptour-main-update",
                resource(builder()
                        .tag(TAG)
                        .summary("메인 페이지 설정 수정")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampTourId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("designTemplate").type(STRING).description("디자인 템플릿"),
                                fieldWithPath("backgroundImg").type(STRING).description("배경 이미지"),
                                fieldWithPath("buttonLayout").type(STRING).description("버튼 배치"),
                                fieldWithPath("buttons").type(ARRAY).optional().description("버튼 목록"))
                        .build()));
    }

    public static RestDocumentationResultHandler getGuide() {
        return document(
                "v2-stamptour-guide-get",
                resource(builder()
                        .tag(TAG)
                        .summary("참여가이드 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampTourId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("participateGuideId").type(NUMBER).description("가이드 ID"),
                                fieldWithPath("guideDesignTemplate")
                                        .type(STRING)
                                        .description("디자인 템플릿"),
                                fieldWithPath("guideSlideMethod").type(STRING).description("슬라이드 방식"),
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
                                parameterWithName("stampTourId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("template").type(STRING).description("디자인 템플릿"),
                                fieldWithPath("method").type(STRING).description("슬라이드 방식"))
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
                                parameterWithName("stampTourId").description("스탬프투어 ID"))
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

    public static RestDocumentationResultHandler createGuidePage() {
        return document(
                "v2-stamptour-guide-page-create",
                resource(builder()
                        .tag(TAG)
                        .summary("참여가이드 페이지 생성")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID"),
                                parameterWithName("stampTourId").description("스탬프투어 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("guideId").type(NUMBER).description("가이드 ID"),
                                fieldWithPath("title").type(STRING).description("제목"),
                                fieldWithPath("guideMediaSpec").type(STRING).description("미디어 스펙"),
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
                                parameterWithName("stampTourId").description("스탬프투어 ID"),
                                parameterWithName("pageId").description("페이지 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .responseFields(
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("title").type(STRING).description("제목"),
                                fieldWithPath("guideMediaSpec").type(STRING).description("미디어 스펙"),
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
                                parameterWithName("stampTourId").description("스탬프투어 ID"),
                                parameterWithName("pageId").description("페이지 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .requestFields(
                                fieldWithPath("guideId").type(NUMBER).description("가이드 ID"),
                                fieldWithPath("title").type(STRING).description("제목"),
                                fieldWithPath("guideMediaSpec").type(STRING).description("미디어 스펙"),
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
                                parameterWithName("stampTourId").description("스탬프투어 ID"),
                                parameterWithName("pageId").description("페이지 ID"))
                        .requestHeaders(headerWithName("Authorization").description("JWT Access 토큰"))
                        .build()));
    }

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
