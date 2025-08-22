package com.halo.eventer.domain.stamp.v2.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.halo.eventer.domain.stamp.controller.v2.StampTourAdminController;
import com.halo.eventer.domain.stamp.dto.stamp.enums.*;
import com.halo.eventer.domain.stamp.dto.stamp.request.*;
import com.halo.eventer.domain.stamp.dto.stamp.response.*;
import com.halo.eventer.domain.stamp.service.v2.StampTourAdminService;
import com.halo.eventer.domain.stamp.v2.api_docs.StampTourAdminDocs;
import com.halo.eventer.global.common.dto.OrderUpdateRequest;
import com.halo.eventer.global.config.ControllerTestSecurityBeans;
import com.halo.eventer.global.config.CustomRestDocsConfig;
import com.halo.eventer.global.config.security.SecurityConfig;
import com.halo.eventer.global.security.provider.JwtProvider;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StampTourAdminController.class)
@AutoConfigureRestDocs
@Import({ControllerTestSecurityBeans.class, SecurityConfig.class, CustomRestDocsConfig.class})
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class StampTourAdminControllerTest {

    private static final String AUTH = "Bearer admin-token";
    private static final long 축제_ID = 1L;
    private static final long 잘못된_ID = 0L;
    private static final long 스탬프_ID = 10L;
    private static final long 페이지_ID = 100L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    StampTourAdminService service;

    @MockitoBean
    private JwtProvider jwtProvider;

    List<StampTourSummaryResDto> summaryList;

    @BeforeEach
    void setUp() {
        summaryList = List.of(new StampTourSummaryResDto(10L, "A", true), new StampTourSummaryResDto(11L, "B", false));
    }

    @Nested
    class 스탬프투어_생성_및_목록 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 스탬프투어_생성_성공() throws Exception {
            var body = new StampTourCreateReqDto("새 스탬프");

            mockMvc.perform(post("/v2/admin/festivals/{festivalId}/stamp-tours", 축제_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.createStampTour());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 스탬프투어_생성_실패_축제ID_검증() throws Exception {
            var body = new StampTourCreateReqDto("새 스탬프");
            mockMvc.perform(post("/v2/admin/festivals/{festivalId}/stamp-tours", 잘못된_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-create-badrequest"));
        }

        @Test
        void 스탬프투어_생성_실패_권한없음() throws Exception {
            var body = new StampTourCreateReqDto("새 스탬프");
            mockMvc.perform(post("/v2/admin/festivals/{festivalId}/stamp-tours", 축제_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-create-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 스탬프투어_목록_조회_성공() throws Exception {
            given(service.getStampTourListByFestival(축제_ID)).willReturn(summaryList);

            mockMvc.perform(get("/v2/admin/festivals/{festivalId}/stamp-tours", 축제_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].stampTourId").value(10L))
                    .andDo(StampTourAdminDocs.listStampTours());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 스탬프투어_목록_조회_실패_축제ID_검증() throws Exception {
            mockMvc.perform(get("/v2/admin/festivals/{festivalId}/stamp-tours", 잘못된_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-list-badrequest"));
        }

        @Test
        void 스탬프투어_목록_조회_실패_권한없음() throws Exception {
            mockMvc.perform(get("/v2/admin/festivals/{festivalId}/stamp-tours", 축제_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-list-unauthorized"));
        }
    }

    @Nested
    class 기본설정 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 기본설정_조회_성공() throws Exception {
            var res = new StampTourSettingBasicResDto(스탬프_ID, "제목", AuthMethod.TAG_SCAN, "pw");
            given(service.getStampTourSettingBasicByFestival(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/basic",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.stampTourId").value(스탬프_ID))
                    .andDo(StampTourAdminDocs.getBasic());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 기본설정_수정_성공() throws Exception {
            var req = new StampTourBasicUpdateReqDto("새제목", true, AuthMethod.TAG_SCAN, "newPw");

            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/basic",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.updateBasic());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 기본설정_수정_실패_ID_검증() throws Exception {
            var req = new StampTourBasicUpdateReqDto("새제목", true, AuthMethod.TAG_SCAN, "pw");
            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/basic",
                                    잘못된_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-basic-update-badrequest"));
        }

        @Test
        void 기본설정_수정_실패_권한없음() throws Exception {
            var req = new StampTourBasicUpdateReqDto("새제목", true, null, "pw");
            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/basic",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-basic-update-unauthorized"));
        }
    }

    // ---------- 안내사항 ----------
    @Nested
    class 안내사항 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 안내사항_조회_성공() throws Exception {
            var res = new StampTourNotificationResDto("주의", "개인정보");
            given(service.getStampTourNotification(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/notice",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.cautionContent").value("주의"))
                    .andDo(StampTourAdminDocs.getNotice());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 안내사항_수정_성공() throws Exception {
            var req = new NotificationContentReqDto("주의2", "개인정보2");

            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/notice",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.updateNotice());
        }

        @Test
        void 안내사항_수정_실패_권한없음() throws Exception {
            var req = new NotificationContentReqDto("주의", "개인정보");
            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/notice",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-notice-update-unauthorized"));
        }
    }

    // ---------- 랜딩 ----------
    @Nested
    class 랜딩 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 랜딩_설정_조회_성공() throws Exception {
            var res = new StampTourLandingPageResDto(
                    LandingPageDesignTemplate.NONE, "bg.jpg", "icon.jpg", "desc", ButtonLayout.ONE, List.of());
            given(service.getLandingPageSettings(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/landing",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.designTemplate").value("NONE"))
                    .andDo(StampTourAdminDocs.getLanding());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 랜딩_설정_수정_성공() throws Exception {
            var req = new StampTourLandingPageReqDto(
                    LandingPageDesignTemplate.NONE,
                    "bg.jpg",
                    "icon.jpg",
                    "desc",
                    ButtonLayout.ONE,
                    List.of(new ButtonReqDto(0, "i", "c", ButtonAction.OPEN_URL, "u")));

            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/landing",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.updateLanding());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 랜딩_설정_수정_실패_ID_검증() throws Exception {
            var req = new StampTourLandingPageReqDto(
                    LandingPageDesignTemplate.NONE, "bg.jpg", "icon.jpg", "desc", ButtonLayout.ONE, List.of());
            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/landing",
                                    잘못된_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-landing-update-badrequest"));
        }

        @Test
        void 랜딩_설정_수정_실패_권한없음() throws Exception {
            var req = new StampTourLandingPageReqDto(
                    LandingPageDesignTemplate.NONE, "bg.jpg", "icon.jpg", "desc", ButtonLayout.ONE, List.of());
            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/landing",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-landing-update-unauthorized"));
        }
    }

    // ---------- 메인 ----------
    @Nested
    class 메인 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 메인_설정_조회_성공() throws Exception {
            var res = new StampTourMainPageResDto(
                    MainPageDesignTemplate.GRID_Nx2, "bg.jpg", ButtonLayout.TWO_SYM.name(), List.of());
            given(service.getMainPageSettings(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/main",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.designTemplate").value("GRID_Nx2"))
                    .andDo(StampTourAdminDocs.getMain());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 메인_설정_수정_성공() throws Exception {
            var req = new StampTourMainPageReqDto(
                    MainPageDesignTemplate.GRID_Nx3, "bg.jpg", ButtonLayout.TWO_ASYM, List.of());

            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/main",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.updateMain());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 메인_설정_수정_실패_ID_검증() throws Exception {
            var req =
                    new StampTourMainPageReqDto(MainPageDesignTemplate.GRID_Nx3, "bg.jpg", ButtonLayout.ONE, List.of());
            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/main",
                                    잘못된_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-main-update-badrequest"));
        }

        @Test
        void 메인_설정_수정_실패_권한없음() throws Exception {
            var req =
                    new StampTourMainPageReqDto(MainPageDesignTemplate.GRID_Nx3, "bg.jpg", ButtonLayout.ONE, List.of());
            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/main",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-main-update-unauthorized"));
        }
    }

    // ---------- 참여가이드 ----------
    @Nested
    class 참여가이드 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 참여가이드_조회_성공() throws Exception {
            var pages = List.of(new ParticipateGuidePageSummaryResDto(페이지_ID, "p1", 1));
            var res = new StampTourParticipateGuideResDto(1L, GuideDesignTemplate.FULL, GuideSlideMethod.SLIDE, pages);
            given(service.getParticipateGuide(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.participateGuideId").value(1L))
                    .andDo(StampTourAdminDocs.getGuide());
        }

        @Test
        void 참여가이드_조회_실패_권한없음() throws Exception {
            mockMvc.perform(get(
                            "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides",
                            축제_ID,
                            스탬프_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-guide-get-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 참여가이드_업서트_성공() throws Exception {
            var req = new StampTourParticipateGuideReqDto(GuideDesignTemplate.FULL, GuideSlideMethod.SLIDE);

            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.upsertGuide());
        }

        @Test
        void 참여가이드_업서트_실패_권한없음() throws Exception {
            var req = new StampTourParticipateGuideReqDto(GuideDesignTemplate.FULL, GuideSlideMethod.SLIDE);
            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-guide-upsert-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 참여가이드_페이지_순서수정_성공() throws Exception {
            var req = List.of(OrderUpdateRequest.of(101L, 1), OrderUpdateRequest.of(102L, 2));
            var res = List.of(
                    new ParticipateGuidePageSummaryResDto(101L, "p1", 1),
                    new ParticipateGuidePageSummaryResDto(102L, "p2", 2));
            given(service.updateDisplayOrder(eq(축제_ID), eq(스탬프_ID), anyList())).willReturn(res);

            mockMvc.perform(patch(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].pageId").value(101L))
                    .andDo(StampTourAdminDocs.updateDisplayOrder());
        }

        @Test
        void 참여가이드_페이지_순서수정_실패_권한없음() throws Exception {
            var req = List.of(OrderUpdateRequest.of(101L, 1));
            mockMvc.perform(patch(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-guide-order-unauthorized"));
        }
    }

    // ---------- 참여가이드 페이지 ----------
    @Nested
    class 참여가이드_페이지 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 페이지_생성_성공() throws Exception {
            var req = new StampTourParticipateGuidePageReqDto(1L, "title", MediaSpec.NONE, null, "sum", "det", "add");

            mockMvc.perform(post(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides/pages",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.createGuidePage());
        }

        @Test
        void 페이지_생성_실패_권한없음() throws Exception {
            var req = new StampTourParticipateGuidePageReqDto(1L, "title", MediaSpec.NONE, null, "s", "d", "a");
            mockMvc.perform(post(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides/pages",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-guide-page-create-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 페이지_상세_조회_성공() throws Exception {
            var res = new ParticipateGuidePageDetailsResDto(페이지_ID, "t", MediaSpec.NONE, null, "s", "d", "a");
            given(service.getParticipateGuidePageDetails(축제_ID, 스탬프_ID, 페이지_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides/pages/{pageId}",
                                    축제_ID,
                                    스탬프_ID,
                                    페이지_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.pageId").value(페이지_ID))
                    .andDo(StampTourAdminDocs.getGuidePage());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 페이지_수정_성공() throws Exception {
            var req = new StampTourParticipateGuidePageReqDto(1L, "title2", MediaSpec.NONE, null, "s2", "d2", "a2");

            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides/pages/{pageId}",
                                    축제_ID,
                                    스탬프_ID,
                                    페이지_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.updateGuidePage());
        }

        @Test
        void 페이지_수정_실패_권한없음() throws Exception {
            var req = new StampTourParticipateGuidePageReqDto(1L, "title2", MediaSpec.NONE, null, "s2", "d2", "a2");

            mockMvc.perform(put(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides/pages/{pageId}",
                                    축제_ID,
                                    스탬프_ID,
                                    페이지_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-guide-page-update-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 페이지_삭제_성공() throws Exception {
            mockMvc.perform(delete(
                                    "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides/pages/{pageId}",
                                    축제_ID,
                                    스탬프_ID,
                                    페이지_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.deleteGuidePage());
        }

        @Test
        void 페이지_삭제_실패_권한없음() throws Exception {
            mockMvc.perform(delete(
                            "/v2/admin/festivals/{festivalId}/stamp-tours/{stampTourId}/settings/guides/pages/{pageId}",
                            축제_ID,
                            스탬프_ID,
                            페이지_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-guide-page-delete-unauthorized"));
        }
    }
}
