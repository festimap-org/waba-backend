package com.halo.eventer.domain.stamp.v2.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    JwtProvider jwtProvider;

    // ---------------- 스탬프투어 생성/목록/삭제 ----------------
    @Nested
    class 스탬프투어_생성_목록_삭제 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 생성_성공() throws Exception {
            var body = Map.of("title", "새 스탬프", "showStamp", true);
            mockMvc.perform(post("/api/v2/admin/festivals/{festivalId}/stamp-tours", 축제_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.createStampTour());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 생성_실패_축제ID() throws Exception {
            var body = Map.of("title", "새 스탬프", "showStamp", true);
            mockMvc.perform(post("/api/v2/admin/festivals/{festivalId}/stamp-tours", 잘못된_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-create-badrequest"));
        }

        @Test
        void 생성_실패_권한없음() throws Exception {
            var body = Map.of("title", "새 스탬프", "showStamp", true);
            mockMvc.perform(post("/api/v2/admin/festivals/{festivalId}/stamp-tours", 축제_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-create-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 목록_조회_성공() throws Exception {
            var list = List.of(new StampTourSummaryResDto(10L, "A", true), new StampTourSummaryResDto(11L, "B", false));
            given(service.getStampTourListByFestival(축제_ID)).willReturn(list);

            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours", 축제_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].stampId").value(10L))
                    .andExpect(jsonPath("$[1].stampId").value(11L))
                    .andDo(StampTourAdminDocs.listStampTours());
        }

        @Test
        void 목록_조회_실패_권한없음() throws Exception {
            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours", 축제_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-list-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 목록_조회_실패_축제ID() throws Exception {
            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours", 잘못된_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-list-badrequest"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 삭제_성공() throws Exception {
            mockMvc.perform(delete("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}", 축제_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.deleteStampTour());
        }

        @Test
        void 삭제_실패_권한없음() throws Exception {
            mockMvc.perform(delete("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}", 축제_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-delete-unauthorized"));
        }
    }

    @Nested
    class 유저_설정_참여인증 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 조회_성공() throws Exception {
            var res = new StampTourJoinVerificationResDto(JoinVerificationMethod.SMS);
            given(service.getJoinVerification(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/user-info",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.joinVerificationMethod").value("SMS"))
                    .andDo(StampTourAdminDocs.getUserSettings());
        }

        @Test
        void 조회_실패_권한없음() throws Exception {
            mockMvc.perform(get(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/user-info",
                            축제_ID,
                            스탬프_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-user-settings-get-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 조회_실패_축제ID() throws Exception {
            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/user-info",
                                    잘못된_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-user-settings-get-badrequest"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 수정_성공() throws Exception {
            var body = Map.of("joinVerificationMethod", "PASS");

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/user-info",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.updateUserSettings());
        }

        @Test
        void 수정_실패_권한없음() throws Exception {
            var body = Map.of("joinVerificationMethod", "SMS");

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/user-info",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-user-settings-update-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 수정_실패_축제ID() throws Exception {
            var body = Map.of("joinVerificationMethod", "NONE");

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/user-info",
                                    잘못된_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-user-settings-update-badrequest"));
        }
    }

    // ---------------- 기본 설정 ----------------
    @Nested
    class 기본설정 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 조회_성공() throws Exception {
            var res = new StampTourSettingBasicResDto(스탬프_ID, true, "제목", AuthMethod.TAG_SCAN, "pw");
            given(service.getStampTourSettingBasicByFestival(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/basic",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.stampId").value(스탬프_ID))
                    .andDo(StampTourAdminDocs.getBasic());
        }

        @Test
        void 조회_실패_권한없음() throws Exception {
            mockMvc.perform(get(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/basic", 축제_ID, 스탬프_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-basic-get-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 업서트_성공() throws Exception {
            var body = Map.of(
                    "stampActivate", true,
                    "title", "새제목",
                    "authMethod", "TAG_SCAN",
                    "prizeReceiptAuthPassword", "newPw");

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/basic",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.updateBasic());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 업서트_실패_축제ID() throws Exception {
            var body = Map.of(
                    "isStampActivate", true, "title", "t", "authMethod", "TAG_SCAN", "prizeReceiptAuthPassword", "pw");
            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/basic",
                                    잘못된_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-basic-upsert-badrequest"));
        }

        @Test
        void 업서트_실패_권한없음() throws Exception {
            var body = Map.of(
                    "stampActivate", true,
                    "title", "새제목",
                    "authMethod", "TAG_SCAN",
                    "prizeReceiptAuthPassword", "newPw");

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/basic",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-basic-upsert-unauthorized"));
        }
    }

    // ---------------- 안내사항 ----------------
    @Nested
    class 안내사항 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 조회_성공() throws Exception {
            var res = new StampTourNotificationResDto("주의", "개인정보");
            given(service.getStampTourNotification(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/notice",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.cautionContent").value("주의"))
                    .andDo(StampTourAdminDocs.getNotice());
        }

        @Test
        void 조회_실패_권한없음() throws Exception {
            mockMvc.perform(get(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/notice",
                            축제_ID,
                            스탬프_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-notice-get-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 업서트_성공() throws Exception {
            var body = Map.of("cautionContent", "주의2", "personalInformationContent", "개인정보2");
            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/notice",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.updateNotice());
        }

        @Test
        void 업서트_실패_권한없음() throws Exception {
            var body = Map.of("cautionContent", "주의", "personalInformationContent", "개인정보");
            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/notice",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-notice-upsert-unauthorized"));
        }
    }

    // ---------------- 랜딩 ----------------
    @Nested
    class 랜딩 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 조회_성공() throws Exception {
            var res = new StampTourLandingPageResDto(
                    LandingPageDesignTemplate.NONE, "bg.jpg", "icon.jpg", "desc", ButtonLayout.NONE, List.of());
            given(service.getLandingPageSettings(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/landing",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.designTemplate").value("NONE"))
                    .andDo(StampTourAdminDocs.getLanding());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 업서트_성공() throws Exception {
            Map<String, Object> req = new LinkedHashMap<>();
            req.put("designTemplate", "NONE");
            req.put("backgroundImgUrl", "bg.jpg");
            req.put("iconImgUrl", "icon.jpg");
            req.put("description", "설명");
            req.put("buttonLayout", "ONE");

            List<Map<String, Object>> buttons = new ArrayList<>();

            Map<String, Object> b1 = new LinkedHashMap<>();
            b1.put("sequenceIndex", 0);
            b1.put("content", "바로가기");
            b1.put("iconImgUrl", "i1.png");
            b1.put("action", "OPEN_URL"); // enum은 문자열로
            b1.put("targetUrl", "https://a.b"); // OPEN_URL이면 URL 포함
            buttons.add(b1);

            Map<String, Object> b2 = new LinkedHashMap<>();
            b2.put("sequenceIndex", 1);
            b2.put("content", "QR 스캔");
            b2.put("iconImgUrl", "i2.png");
            b2.put("action", "QR_CAMERA");
            b2.put("targetUrl", null); // 스캔류는 없어도 됨
            buttons.add(b2);

            Map<String, Object> b3 = new LinkedHashMap<>();
            b3.put("sequenceIndex", 2);
            b3.put("content", "새창");
            b3.put("iconImgUrl", "i3.png");
            b3.put("action", "OPEN_NEW_PAGE");
            b3.put("targetUrl", "/info"); // 내부 경로 예시
            buttons.add(b3);

            req.put("buttons", buttons);

            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/landing",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.updateLanding());
        }

        @Test
        void 업서트_실패_권한없음() throws Exception {
            Map<String, Object> req = new LinkedHashMap<>();
            req.put("designTemplate", "NONE");
            req.put("backgroundImgUrl", "bg.jpg");
            req.put("iconImgUrl", "icon.jpg");
            req.put("description", "설명");
            req.put("buttonLayout", "ONE");
            req.put("buttons", List.of());

            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/landing",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-landing-upsert-unauthorized"));
        }
    }

    // ---------------- 메인 ----------------
    @Nested
    class 메인 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 조회_성공() throws Exception {
            var res =
                    new StampTourMainPageResDto(MainPageDesignTemplate.GRID_Nx2, "bg.jpg", ButtonLayout.ONE, List.of());
            given(service.getMainPageSettings(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/main",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.designTemplate").value("GRID_Nx2"))
                    .andDo(StampTourAdminDocs.getMain());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 업서트_성공() throws Exception {
            var req = Map.of(
                    "designTemplate", "GRID_Nx3",
                    "backgroundImgUrl", "bg.jpg",
                    "buttonLayout", "TWO_SYM",
                    "buttons", List.of());

            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/main",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.updateMain());
        }

        @Test
        void 업서트_실패_권한없음() throws Exception {
            var req = Map.of(
                    "designTemplate", "GRID_Nx3",
                    "backgroundImgUrl", "bg.jpg",
                    "buttonLayout", "TWO_SYM",
                    "buttons", List.of());

            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/main",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-main-upsert-unauthorized"));
        }
    }

    // ---------------- 참여가이드 ----------------
    @Nested
    class 참여가이드 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 조회_성공() throws Exception {
            var pages = List.of(new ParticipateGuidePageSummaryResDto(페이지_ID, "p1", 1));
            var res = new StampTourParticipateGuideResDto(1L, GuideDesignTemplate.FULL, GuideSlideMethod.SLIDE, pages);
            given(service.getParticipateGuide(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.participateGuideId").value(1L))
                    .andDo(StampTourAdminDocs.getGuide());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 조회_성공_페이지_없음() throws Exception {
            var res = new StampTourParticipateGuideResDto(
                    1L, GuideDesignTemplate.FULL, GuideSlideMethod.SLIDE, List.of());
            given(service.getParticipateGuide(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.participateGuideId").value(1L))
                    .andDo(StampTourAdminDocs.getGuide());
        }

        @Test
        void 조회_실패_권한없음() throws Exception {
            mockMvc.perform(get(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides",
                            축제_ID,
                            스탬프_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-guide-get-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 업서트_성공() throws Exception {
            var req = new StampTourParticipateGuideReqDto(GuideDesignTemplate.FULL, GuideSlideMethod.SLIDE);

            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.upsertGuide());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 순서수정_성공() throws Exception {
            var req = List.of(OrderUpdateRequest.of(101L, 1), OrderUpdateRequest.of(102L, 2));
            var res = List.of(
                    new ParticipateGuidePageSummaryResDto(101L, "p1", 1),
                    new ParticipateGuidePageSummaryResDto(102L, "p2", 2));
            given(service.updateDisplayOrder(eq(축제_ID), eq(스탬프_ID), anyList())).willReturn(res);

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides",
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
        void 업서트_실패_권한없음() throws Exception {
            var req = new StampTourParticipateGuideReqDto(GuideDesignTemplate.FULL, GuideSlideMethod.SLIDE);

            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-guide-upsert-unauthorized"));
        }

        @Test
        void 순서수정_실패_권한없음() throws Exception {
            var req = List.of(OrderUpdateRequest.of(101L, 1), OrderUpdateRequest.of(102L, 2));

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-guide-pages-order-unauthorized"));
        }
    }

    // ---------------- 참여가이드 페이지 ----------------
    @Nested
    class 참여가이드_페이지 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 생성_성공() throws Exception {
            var req = new StampTourParticipateGuidePageReqDto("title", MediaSpec.NONE, null, "sum", "det", "add");

            mockMvc.perform(post(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides/pages",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.createGuidePage());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 상세_조회_성공() throws Exception {
            var res = new ParticipateGuidePageDetailsResDto(페이지_ID, "t", MediaSpec.NONE, null, "s", "d", "a");
            given(service.getParticipateGuidePageDetails(축제_ID, 스탬프_ID, 페이지_ID)).willReturn(res);

            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides/pages/{pageId}",
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
        void 수정_성공() throws Exception {
            var req = Map.of(
                    "guideId", 1L,
                    "title", "title2",
                    "mediaSpec", MediaSpec.NONE.name(),
                    "mediaUrl", "url",
                    "summary", "s2",
                    "details", "d2",
                    "additional", "a2");

            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides/pages/{pageId}",
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
        @WithMockUser(roles = "ADMIN")
        void 삭제_성공() throws Exception {
            mockMvc.perform(delete(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides/pages/{pageId}",
                                    축제_ID,
                                    스탬프_ID,
                                    페이지_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampTourAdminDocs.deleteGuidePage());
        }

        @Test
        void 생성_실패_권한없음() throws Exception {
            var req = new StampTourParticipateGuidePageReqDto("title", MediaSpec.NONE, null, "sum", "det", "add");

            mockMvc.perform(post(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides/pages",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-guide-page-create-unauthorized"));
        }

        @Test
        void 수정_실패_권한없음() throws Exception {
            var req = Map.of(
                    "guideId", 1L,
                    "title", "title2",
                    "mediaSpec", MediaSpec.NONE.name(),
                    "mediaUrl", "url",
                    "summary", "s2",
                    "details", "d2",
                    "additional", "a2");

            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides/pages/{pageId}",
                                    축제_ID,
                                    스탬프_ID,
                                    페이지_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-guide-page-update-unauthorized"));
        }

        @Test
        void 삭제_실패_권한없음() throws Exception {
            mockMvc.perform(delete(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/settings/guides/pages/{pageId}",
                            축제_ID,
                            스탬프_ID,
                            페이지_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourAdminDocs.error("v2-stamptour-guide-page-delete-unauthorized"));
        }
    }
}
