package com.halo.eventer.domain.stamp.v2.api_docs;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.ResourceSnippetParameters.builder;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class StampTourTemplateDocs {

    private static final String TAG = "스탬프투어(v2) 템플릿";

    public static RestDocumentationResultHandler listStampTours() {
        return document(
                "v2-template-stamptour-list",
                resource(builder()
                        .tag(TAG)
                        .summary("스탬프투어 목록 조회(템플릿)")
                        .pathParameters(parameterWithName("festivalId").description("축제 ID (>=1)"))
                        .responseFields(
                                fieldWithPath("[].stampTourId").type(NUMBER).description("스탬프투어 ID"),
                                fieldWithPath("[].title").type(STRING).description("제목"),
                                fieldWithPath("[].showStamp").type(BOOLEAN).description("목록 노출 여부"))
                        .build()));
    }

    public static RestDocumentationResultHandler getSignUpTemplate() {
        return document(
                "v2-template-signup-get",
                resource(builder()
                        .tag(TAG)
                        .summary("회원가입 템플릿 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampTourId").description("스탬프투어 ID (>=1)"))
                        .responseFields(fieldWithPath("joinVerificationMethod")
                                .type(STRING)
                                .description("참여 인증 방식(enum: NONE, SMS, PASS)"))
                        .build()));
    }

    public static RestDocumentationResultHandler getLanding() {
        return document(
                "v2-template-landing-get",
                resource(builder()
                        .tag(TAG)
                        .summary("랜딩 페이지 템플릿 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampTourId").description("스탬프투어 ID (>=1)"))
                        .responseFields(
                                fieldWithPath("designTemplate").type(STRING).description("랜딩 디자인 템플릿"),
                                fieldWithPath("iconImgUrl").type(STRING).description("아이콘 이미지 URL"),
                                fieldWithPath("backgroundImgUrl").type(STRING).description("배경 이미지 URL"),
                                fieldWithPath("description").type(STRING).description("설명"),
                                fieldWithPath("buttonLayout").type(STRING).description("버튼 레이아웃"),
                                fieldWithPath("buttons").type(ARRAY).description("버튼 목록"),
                                fieldWithPath("buttons[].sequenceIndex")
                                        .type(NUMBER)
                                        .description("순서(0-base)"),
                                fieldWithPath("buttons[].content").type(STRING).description("라벨"),
                                fieldWithPath("buttons[].iconImgUrl")
                                        .type(STRING)
                                        .description("아이콘 URL"),
                                fieldWithPath("buttons[].action").type(STRING).description("동작(enum)"),
                                fieldWithPath("buttons[].targetUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("URL(OPEN_URL)"))
                        .build()));
    }

    public static RestDocumentationResultHandler getJoinTemplate() {
        return document(
                "v2-template-join-template-get",
                resource(builder()
                        .tag(TAG)
                        .summary("참여 인증 템플릿 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampTourId").description("스탬프투어 ID (>=1)"))
                        .responseFields(
                                fieldWithPath("method").type(STRING).description("참여 인증 방식(enum: NONE, SMS, PASS)"))
                        .build()));
    }

    public static RestDocumentationResultHandler getAuthMethod() {
        return document(
                "v2-template-auth-method-get",
                resource(builder()
                        .tag(TAG)
                        .summary("스탬프 인증 방식 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampTourId").description("스탬프투어 ID (>=1)"))
                        .responseFields(fieldWithPath("authMethod")
                                .type(STRING)
                                .description("인증 방식(enum: TAG_SCAN, USER_CODE_PRESENT)"))
                        .build()));
    }

    public static RestDocumentationResultHandler getMain() {
        return document(
                "v2-template-main-get",
                resource(builder()
                        .tag(TAG)
                        .summary("메인 페이지 템플릿 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampTourId").description("스탬프투어 ID (>=1)"))
                        .responseFields(
                                fieldWithPath("designTemplate").type(STRING).description("메인 디자인 템플릿"),
                                fieldWithPath("backgroundImg").type(STRING).description("배경 이미지"),
                                fieldWithPath("buttonLayout").type(STRING).description("버튼 레이아웃"),
                                fieldWithPath("buttons").type(ARRAY).description("버튼 목록"),
                                fieldWithPath("buttons[].sequenceIndex")
                                        .type(NUMBER)
                                        .description("순서(0-base)"),
                                fieldWithPath("buttons[].content").type(STRING).description("라벨"),
                                fieldWithPath("buttons[].iconImgUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("아이콘 URL"),
                                fieldWithPath("buttons[].action").type(STRING).description("동작(enum)"),
                                fieldWithPath("buttons[].targetUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("URL(OPEN_URL)"))
                        .build()));
    }

    public static RestDocumentationResultHandler getNotice() {
        return document(
                "v2-template-notice-get",
                resource(builder()
                        .tag(TAG)
                        .summary("안내사항 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampTourId").description("스탬프투어 ID (>=1)"))
                        .responseFields(
                                fieldWithPath("cautionContent").type(STRING).description("주의사항"),
                                fieldWithPath("personalInformationContent")
                                        .type(STRING)
                                        .description("개인정보 처리 안내"))
                        .build()));
    }

    public static RestDocumentationResultHandler getGuide() {
        return document(
                "v2-template-guide-get",
                resource(builder()
                        .tag(TAG)
                        .summary("참여가이드 조회")
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID (>=1)"),
                                parameterWithName("stampTourId").description("스탬프투어 ID (>=1)"))
                        .responseFields(
                                fieldWithPath("participateGuideId").type(NUMBER).description("가이드 ID"),
                                fieldWithPath("guideDesignTemplate")
                                        .type(STRING)
                                        .description("디자인 템플릿(enum)"),
                                fieldWithPath("guideSlideMethod").type(STRING).description("슬라이드 방식(enum)"),
                                fieldWithPath("participateGuidePages")
                                        .type(ARRAY)
                                        .description("가이드 페이지 목록"),
                                fieldWithPath("participateGuidePages[].pageId")
                                        .type(NUMBER)
                                        .description("페이지 ID"),
                                fieldWithPath("participateGuidePages[].title")
                                        .type(STRING)
                                        .description("제목"),
                                fieldWithPath("participateGuidePages[].mediaUrl")
                                        .type(STRING)
                                        .optional()
                                        .description("미디어 URL"),
                                fieldWithPath("participateGuidePages[].summary")
                                        .type(STRING)
                                        .optional()
                                        .description("요약"),
                                fieldWithPath("participateGuidePages[].details")
                                        .type(STRING)
                                        .optional()
                                        .description("상세"),
                                fieldWithPath("participateGuidePages[].additional")
                                        .type(STRING)
                                        .optional()
                                        .description("추가"))
                        .build()));
    }
}
