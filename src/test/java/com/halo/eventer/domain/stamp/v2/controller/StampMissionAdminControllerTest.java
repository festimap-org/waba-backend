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
import com.halo.eventer.domain.stamp.controller.v2.StampMissionAdminController;
import com.halo.eventer.domain.stamp.dto.mission.request.*;
import com.halo.eventer.domain.stamp.dto.mission.response.MissionPrizeResDto;
import com.halo.eventer.domain.stamp.service.v2.StampMissionAdminService;
import com.halo.eventer.domain.stamp.v2.api_docs.StampMissionAdminDocs;
import com.halo.eventer.global.config.ControllerTestSecurityBeans;
import com.halo.eventer.global.config.CustomRestDocsConfig;
import com.halo.eventer.global.config.security.SecurityConfig;
import com.halo.eventer.global.security.provider.JwtProvider;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StampMissionAdminController.class)
@AutoConfigureRestDocs
@Import({ControllerTestSecurityBeans.class, SecurityConfig.class, CustomRestDocsConfig.class})
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class StampMissionAdminControllerTest {

    private static final String AUTH = "Bearer admin-token";
    private static final long 축제_ID = 1L;
    private static final long 스탬프_ID = 10L;
    private static final long 미션_ID = 101L;
    private static final long 상품_ID = 1001L;
    private static final long 잘못된_ID = 0L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    StampMissionAdminService service;

    @MockitoBean
    JwtProvider jwtProvider;

    @Nested
    class 미션_생성_및_목록 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션_생성_성공() throws Exception {
            var body = Map.of("name", "새 미션");
            mockMvc.perform(post("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions", 축제_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.createMission());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션_생성_실패_축제ID_검증() throws Exception {
            var body = Map.of("name", "새 미션");
            mockMvc.perform(post("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions", 잘못된_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampMissionAdminDocs.error("v2-mission-create-badrequest"));
        }

        @Test
        void 미션_생성_실패_권한없음() throws Exception {
            var body = Map.of("name", "새 미션");
            mockMvc.perform(post("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions", 축제_ID, 스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-create-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션_목록_조회_성공() throws Exception {
            given(service.getMissions(축제_ID, 스탬프_ID)).willReturn(List.of()); // 내용 검증은 생략
            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions", 축제_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.listMissions());
        }

        @Test
        void 미션_목록_조회_실패_권한없음() throws Exception {
            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions", 축제_ID, 스탬프_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-list-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션_목록_조회_실패_축제ID_검증() throws Exception {
            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions", 잘못된_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isBadRequest())
                    .andDo(StampMissionAdminDocs.error("v2-mission-list-badrequest"));
        }
    }

    @Nested
    class 기본설정 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 기본설정_조회_성공() throws Exception {
            // 응답 필드 문서는 생략(스키마 변화 안전성), 상태만 검증
            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/settings",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.getBasicSettings());
        }

        @Test
        void 기본설정_조회_실패_권한없음() throws Exception {
            mockMvc.perform(get(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/settings",
                            축제_ID,
                            스탬프_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-basic-get-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 기본설정_업서트_성공() throws Exception {
            var body = Map.of("missionCount", 8, "defaultDetailLayout", "CARD");

            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/settings",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.upsertBasicSettings());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 기본설정_업서트_실패_축제ID_검증() throws Exception {
            var body = Map.of("missionCount", 2, "defaultDetailLayout", "CARD");
            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/settings",
                                    잘못된_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampMissionAdminDocs.error("v2-mission-basic-upsert-badrequest"));
        }

        @Test
        void 기본설정_업서트_실패_권한없음() throws Exception {
            var body = Map.of("missionCount", 6, "defaultDetailLayout", "CARD");
            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/settings",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-basic-upsert-unauthorized"));
        }
    }

    @Nested
    class 상품_리워드 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 상품_추가_성공() throws Exception {
            var body = Map.of("requiredCount", 5, "prizeDescription", "스티커 세트");
            given(service.addPrize(eq(축제_ID), eq(스탬프_ID), any(MissionPrizeCreateReqDto.class)))
                    .willReturn(new MissionPrizeResDto(상품_ID, 5, "스티커 세트"));

            mockMvc.perform(post(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/prizes",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.createPrize());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 상품_추가_실패_축제ID_검증() throws Exception {
            var body = Map.of("requiredCount", 3, "prizeDescription", "볼펜");
            mockMvc.perform(post(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/prizes",
                                    잘못된_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampMissionAdminDocs.error("v2-mission-prize-create-badrequest"));
        }

        @Test
        void 상품_추가_실패_권한없음() throws Exception {
            var body = Map.of("requiredCount", 3, "prizeDescription", "볼펜");
            mockMvc.perform(post(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/prizes",
                                    축제_ID,
                                    스탬프_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-prize-create-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 상품_수정_성공() throws Exception {
            var body = Map.of("requiredCount", 7, "prizeDescription", "티셔츠");
            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/prizes/{prizeId}",
                                    축제_ID,
                                    스탬프_ID,
                                    상품_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.updatePrize());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 상품_수정_실패_축제ID_검증() throws Exception {
            var body = Map.of("requiredCount", 10, "prizeDescription", "머그컵");
            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/prizes/{prizeId}",
                                    잘못된_ID,
                                    스탬프_ID,
                                    상품_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampMissionAdminDocs.error("v2-mission-prize-update-badrequest"));
        }

        @Test
        void 상품_수정_실패_권한없음() throws Exception {
            var body = Map.of("requiredCount", 10, "prizeDescription", "머그컵");
            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/prizes/{prizeId}",
                                    축제_ID,
                                    스탬프_ID,
                                    상품_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-prize-update-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 상품_삭제_성공() throws Exception {
            mockMvc.perform(delete(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/prizes/{prizeId}",
                                    축제_ID,
                                    스탬프_ID,
                                    상품_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.deletePrize());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 상품_삭제_실패_축제ID_검증() throws Exception {
            mockMvc.perform(delete(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/prizes/{prizeId}",
                                    잘못된_ID,
                                    스탬프_ID,
                                    상품_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isBadRequest())
                    .andDo(StampMissionAdminDocs.error("v2-mission-prize-delete-badrequest"));
        }

        @Test
        void 상품_삭제_실패_권한없음() throws Exception {
            mockMvc.perform(delete(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/prizes/{prizeId}",
                            축제_ID,
                            스탬프_ID,
                            상품_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-prize-delete-unauthorized"));
        }
    }

    @Nested
    class 미션_상세설정 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션상세_조회_성공() throws Exception {
            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}/details",
                                    축제_ID,
                                    스탬프_ID,
                                    미션_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.getMissionDetails());
        }

        @Test
        void 미션상세_조회_실패_권한없음() throws Exception {
            mockMvc.perform(get(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}/details",
                            축제_ID,
                            스탬프_ID,
                            미션_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-details-get-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션상세_업서트_성공() throws Exception {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("layout", "CARD");
            body.put("missionTitle", "수정된 미션");
            body.put("showMissionName", true);
            body.put("showSuccessCount", true);
            body.put("showExtraInfos", true);
            body.put("showButtons", true);
            body.put("missionMediaSpec", "NONE"); // MediaSpec
            body.put("mediaUrl", null); // null 허용
            body.put("extraInfoType", "LIST"); // ExtraInfoLayout
            body.put("buttonLayout", "ONE"); // ButtonLayout

            // extraInfos
            List<Map<String, Object>> extraInfos = new ArrayList<>();
            Map<String, Object> info1 = new LinkedHashMap<>();
            info1.put("titleText", "유의사항");
            info1.put("bodyText", "본인 신분증 지참");
            extraInfos.add(info1);
            body.put("extraInfos", extraInfos);

            // buttons
            List<Map<String, Object>> buttons = new ArrayList<>();
            Map<String, Object> b1 = new LinkedHashMap<>();
            b1.put("sequenceIndex", 0);
            b1.put("iconImg", "i");
            b1.put("content", "바로가기");
            b1.put("action", "OPEN_URL");
            b1.put("targetUrl", null);
            buttons.add(b1);
            body.put("buttons", buttons);

            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}/details",
                                    축제_ID,
                                    스탬프_ID,
                                    미션_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.upsertMissionDetails()); // 아래 RestDocs 스니펫 갱신본
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션상세_업서트_실패_축제ID_검증() throws Exception {
            var body = Map.of("missionTitle", "제목", "designLayout", "CARD");
            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}/details",
                                    잘못된_ID,
                                    스탬프_ID,
                                    미션_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampMissionAdminDocs.error("v2-mission-details-upsert-badrequest"));
        }

        @Test
        void 미션상세_업서트_실패_권한없음() throws Exception {
            var body = Map.of("missionTitle", "제목", "designLayout", "CARD");
            mockMvc.perform(put(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}/details",
                                    축제_ID,
                                    스탬프_ID,
                                    미션_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-details-upsert-unauthorized"));
        }
    }

    @Nested
    class 미션_완료이미지_QR {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션_완료이미지_조회_성공() throws Exception {
            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}/clear-img",
                                    축제_ID,
                                    스탬프_ID,
                                    미션_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.getMissionClearImg());
        }

        @Test
        void 미션_완료이미지_조회_실패_권한없음() throws Exception {
            mockMvc.perform(get(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}/clear-img",
                            축제_ID,
                            스탬프_ID,
                            미션_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-clearimg-get-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션_완료이미지_수정_성공() throws Exception {
            var body = Map.of("imageUrl", "https://img/clear.jpg");
            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}/clear-img",
                                    축제_ID,
                                    스탬프_ID,
                                    미션_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.updateMissionClearImg());
        }

        @Test
        void 미션_완료이미지_수정_실패_권한없음() throws Exception {
            var body = Map.of("imageUrl", "https://img/clear.jpg");
            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}/clear-img",
                                    축제_ID,
                                    스탬프_ID,
                                    미션_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-clearimg-update-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션_QR_데이터_조회_성공() throws Exception {
            given(service.getMissionsQrData(축제_ID, 스탬프_ID)).willReturn(List.of());
            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/qr", 축제_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.getQrData());
        }

        @Test
        void 미션_QR_데이터_조회_실패_권한없음() throws Exception {
            mockMvc.perform(get(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/qr", 축제_ID, 스탬프_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-qr-get-unauthorized"));
        }
    }
}
