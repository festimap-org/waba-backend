package com.halo.eventer.domain.parking.api_docs;

import com.halo.eventer.global.constants.ApiConstraint;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

public class ParkingManagementDoc {

    private static final String TAG = "주차 관리";

    public static RestDocumentationResultHandler errorSnippet(String identifier) {
        return document(
                identifier,
                resource(ResourceSnippetParameters.builder()
                        .tag(TAG)
                        .summary("에러 응답 형식")
                        .responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("에러 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("상세 메시지"),
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"))
                        .build()));
    }

    // ========== ADMIN ==========
    public static RestDocumentationResultHandler create() {
        return document(
                "parking-management 생성",
                resource(ResourceSnippetParameters.builder()
                        .tag(TAG)
                        .summary("주차 관리 생성 (ADMIN)")
                        .description("축제 ID에 해당하는 주차 관리 영역을 생성합니다.")
                        .requestSchema(Schema.schema("ParkingManagementReqDto"))
                        .pathParameters(
                                parameterWithName("festivalId").description("축제 ID")
                                        .attributes(key("constraints").value("필수, 1 이상의 양의 정수")))
                        .requestHeaders(
                                headerWithName("Authorization").description("Bearer {JWT}"))
                        .requestFields(
                                fieldWithPath("headerName").type(JsonFieldType.STRING)
                                        .description("헤더명")
                                        .attributes(key("constraints").value("필수, NotBlank")),
                                fieldWithPath("parkingInfoType").type(JsonFieldType.STRING)
                                        .description("표시 타입 (BASIC|BUTTON|SIMPLE)")
                                        .attributes(key("constraints").value("필수, 패턴: BASIC|BUTTON|SIMPLE 중 선택")),
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("타이틀").optional(),
                                fieldWithPath("description").type(JsonFieldType.STRING)
                                        .description("설명").optional(),
                                fieldWithPath("buttonName").type(JsonFieldType.STRING)
                                        .description("버튼명").optional(),
                                fieldWithPath("buttonTargetUrl").type(JsonFieldType.STRING)
                                        .description("버튼 링크 URL").optional()
                                        .attributes(key("constraints").value("URL 형식 권장")),
                                fieldWithPath("backgroundImage").type(JsonFieldType.STRING)
                                        .description("배경 이미지 URL").optional(),
                                fieldWithPath("visible").type(JsonFieldType.BOOLEAN)
                                        .description("노출 여부").optional())
                        .build()));
    }

    public static RestDocumentationResultHandler update() {
        return document(
                "parking-management 수정",
                resource(ResourceSnippetParameters.builder()
                        .tag(TAG)
                        .summary("주차 관리 수정 (ADMIN)")
                        .description("주차 관리 정보를 수정합니다.")
                        .requestSchema(Schema.schema("ParkingManagementReqDto"))
                        .pathParameters(
                                parameterWithName("id").description("주차 관리 ID")
                                        .attributes(key("validationConstraints").value(List.of(ApiConstraint.JavaxMin2(1)))))
                        .requestHeaders(
                                headerWithName("Authorization").description("Bearer {JWT}"))
                        .requestFields(
                                fieldWithPath("headerName").type(JsonFieldType.STRING)
                                        .description("헤더명").optional(),
                                fieldWithPath("parkingInfoType").type(JsonFieldType.STRING)
                                        .description("표시 타입 (BASIC|BUTTON|SIMPLE)"),
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("타이틀").optional(),
                                fieldWithPath("description").type(JsonFieldType.STRING)
                                        .description("설명").optional(),
                                fieldWithPath("buttonName").type(JsonFieldType.STRING)
                                        .description("버튼명").optional(),
                                fieldWithPath("buttonTargetUrl").type(JsonFieldType.STRING)
                                        .description("버튼 링크 URL").optional(),
                                fieldWithPath("backgroundImage").type(JsonFieldType.STRING)
                                        .description("배경 이미지 URL").optional(),
                                fieldWithPath("visible").type(JsonFieldType.BOOLEAN)
                                        .description("노출 여부"))
                        .build()));
    }

    public static RestDocumentationResultHandler updateSubPageHeaderName() {
        return document(
                "parking-management 서브페이지-헤더명 수정",
                resource(ResourceSnippetParameters.builder()
                        .tag(TAG)
                        .summary("서브페이지 헤더명 수정 (ADMIN)")
                        .description("서브페이지 상단에 표시할 헤더명을 수정합니다.")
                        .requestSchema(Schema.schema("ParkingSubPageReqDto"))
                        .pathParameters(
                                parameterWithName("id").description("주차 관리 ID")
                                        .attributes(key("validationConstraints").value(List.of(ApiConstraint.JavaxMin2(1)))))
                        .requestHeaders(
                                headerWithName("Authorization").description("Bearer {JWT}"))
                        .requestFields(
                                fieldWithPath("subPageHeaderName").type(JsonFieldType.STRING)
                                        .description("서브페이지 헤더명")
                                        .attributes(key("constraints").value("필수, NotNull")))
                        .build()));
    }

    public static RestDocumentationResultHandler createParkingMapImage() {
        return document(
                "parking-management 주차맵이미지 등록",
                resource(ResourceSnippetParameters.builder()
                        .tag(TAG)
                        .summary("주차맵 이미지 등록 (ADMIN)")
                        .description("주차 맵 이미지를 1건 등록합니다.")
                        .requestSchema(Schema.schema("FileDto"))
                        .pathParameters(
                                parameterWithName("id").description("주차 관리 ID")
                                        .attributes(key("validationConstraints").value(List.of(ApiConstraint.JavaxMin2(1)))))
                        .requestHeaders(
                                headerWithName("Authorization").description("Bearer {JWT}"))
                        .requestFields(
                                // 프로젝트에 따라 필드명이 image 또는 url일 수 있어, 실제 DTO에 맞춰 한쪽을 사용하세요.
                                fieldWithPath("image").type(JsonFieldType.STRING)
                                        .description("이미지 URL/식별자").optional(),
                                fieldWithPath("url").type(JsonFieldType.STRING)
                                        .description("이미지 URL (대체 필드)").optional())
                        .build()));
    }

    public static RestDocumentationResultHandler updateParkingMapImageDisplayOrder() {
        return document(
                "parking-management 주차맵이미지 정렬변경",
                resource(ResourceSnippetParameters.builder()
                        .tag(TAG)
                        .summary("주차맵 이미지 정렬 변경 (ADMIN)")
                        .description("전달된 순서대로 이미지 표시 순서를 재배치합니다.")
                        .requestSchema(Schema.schema("ParkingMapImageReqDto"))
                        .pathParameters(
                                parameterWithName("id").description("주차 관리 ID")
                                        .attributes(key("validationConstraints").value(List.of(ApiConstraint.JavaxMin2(1)))))
                        .requestHeaders(
                                headerWithName("Authorization").description("Bearer {JWT}"))
                        .requestFields(
                                fieldWithPath("imageIds").type(JsonFieldType.ARRAY)
                                        .description("표시 순서대로 정렬할 이미지 ID 목록")
                                        .attributes(key("constraints").value("필수, 각 원소는 1 이상의 양의 정수")),
                                fieldWithPath("imageIds[].").ignored())
                        .build()));
    }

    public static RestDocumentationResultHandler deleteParkingMapImages() {
        return document(
                "parking-management 주차맵이미지 삭제",
                resource(ResourceSnippetParameters.builder()
                        .tag(TAG)
                        .summary("주차맵 이미지 삭제 (ADMIN)")
                        .description("전달된 이미지 ID 목록을 삭제합니다.")
                        .requestSchema(Schema.schema("ParkingMapImageReqDto"))
                        .pathParameters(
                                parameterWithName("id").description("주차 관리 ID")
                                        .attributes(key("validationConstraints").value(List.of(ApiConstraint.JavaxMin2(1)))))
                        .requestHeaders(
                                headerWithName("Authorization").description("Bearer {JWT}"))
                        .requestFields(
                                fieldWithPath("imageIds").type(JsonFieldType.ARRAY)
                                        .description("삭제할 이미지 ID 목록")
                                        .attributes(key("constraints").value("필수, 각 원소는 1 이상의 양의 정수")))
                        .build()));
    }

    // ========== 조회 ==========
    public static RestDocumentationResultHandler getOne() {
        return document(
                "parking-management 단건조회",
                resource(ResourceSnippetParameters.builder()
                        .tag(TAG)
                        .summary("주차 관리 단건 조회 (COMMON)")
                        .description("주차 관리 영역(공통)을 조회합니다.")
                        .responseSchema(Schema.schema("ParkingManagementResDto"))
                        .pathParameters(
                                parameterWithName("id").description("주차 관리 ID")
                                        .attributes(key("validationConstraints").value(List.of(ApiConstraint.JavaxMin2(1)))))
                        .responseFields(
                                fieldWithPath("headerName").type(JsonFieldType.STRING).description("헤더명"),
                                fieldWithPath("parkingInfoType").type(JsonFieldType.STRING).description("표시 타입"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("타이틀").optional(),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("설명").optional(),
                                fieldWithPath("buttonName").type(JsonFieldType.STRING).description("버튼명").optional(),
                                fieldWithPath("buttonTargetUrl").type(JsonFieldType.STRING).description("버튼 링크 URL").optional(),
                                fieldWithPath("backgroundImage").type(JsonFieldType.STRING).description("배경 이미지 URL").optional(),
                                fieldWithPath("visible").type(JsonFieldType.BOOLEAN).description("노출 여부"))
                        .build()));
    }

    public static RestDocumentationResultHandler getSubPageInfo() {
        return document(
                "parking-management 서브페이지 조회",
                resource(ResourceSnippetParameters.builder()
                        .tag(TAG)
                        .summary("서브페이지 정보 조회 (ADMIN)")
                        .description("서브페이지 헤더명과 주차 맵 이미지 목록을 조회합니다.")
                        .responseSchema(Schema.schema("ParkingManagementSubPageResDto"))
                        .pathParameters(
                                parameterWithName("id").description("주차 관리 ID")
                                        .attributes(key("validationConstraints").value(List.of(ApiConstraint.JavaxMin2(1)))))
                        .requestHeaders(
                                headerWithName("Authorization").description("Bearer {JWT}"))
                        .responseFields(
                                fieldWithPath("subPageHeaderName").type(JsonFieldType.STRING).description("서브페이지 헤더명"),
                                fieldWithPath("images").type(JsonFieldType.ARRAY).description("이미지 목록"),
                                fieldWithPath("images[].id").type(JsonFieldType.NUMBER).description("이미지 ID"),
                                fieldWithPath("images[].image").type(JsonFieldType.STRING).description("이미지 URL/식별자"))
                        .build()));
    }
}
