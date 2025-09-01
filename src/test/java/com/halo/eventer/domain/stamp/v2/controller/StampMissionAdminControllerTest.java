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
import com.halo.eventer.domain.stamp.dto.mission.response.*;
import com.halo.eventer.domain.stamp.dto.stamp.enums.*;
import com.halo.eventer.domain.stamp.dto.stamp.response.ButtonResDto;
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

        private static final int LIMIT = 8;

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션_생성_성공() throws Exception {
            var body = new MissionCreateReqDto("새 미션", true);

            mockMvc.perform(post("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions", 축제_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.createMission());
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
            var list = List.of(
                    new MissionBriefResDto(101L, "미션A", true, true), new MissionBriefResDto(102L, "미션B", false, false));
            var res = new MissionListResDto(LIMIT, list);
            given(service.getMissions(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions", 축제_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.missionCount").value(LIMIT))
                    .andExpect(jsonPath("$.missionList[0].missionId").value(101L))
                    .andExpect(jsonPath("$.missionList[0].title").value("미션A"))
                    .andDo(StampMissionAdminDocs.listMissions());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션_목록_조회_성공_빈목록() throws Exception {
            var res = new MissionListResDto(LIMIT, List.of());
            given(service.getMissions(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions", 축제_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.missionCount").value(LIMIT))
                    .andExpect(jsonPath("$.missionList.length()").value(0))
                    .andDo(StampMissionAdminDocs.listMissions());
        }

        @Test
        void 미션_목록_조회_실패_권한없음() throws Exception {
            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions", 축제_ID, 스탬프_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-list-unauthorized"));
        }
    }

    @Nested
    class 미션_삭제 {

        private static final long 미션_ID = 101L;

        @Test
        @WithMockUser(roles = "ADMIN")
        void 삭제_성공() throws Exception {
            mockMvc.perform(delete(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}",
                                    축제_ID,
                                    스탬프_ID,
                                    미션_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.deleteMission());
        }

        @Test
        void 삭제_실패_권한없음() throws Exception {
            mockMvc.perform(delete(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}",
                            축제_ID,
                            스탬프_ID,
                            미션_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-delete-unauthorized"));
        }
    }

    @Nested
    class 미션_표시여부 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 표시여부_수정_성공() throws Exception {
            var body = Map.of("showMission", true);

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}/show",
                                    축제_ID,
                                    스탬프_ID,
                                    미션_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.toggleMissionShow());
        }

        @Test
        void 표시여부_수정_실패_권한없음() throws Exception {
            var body = Map.of("show", false);

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}/show",
                                    축제_ID,
                                    스탬프_ID,
                                    미션_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-mission-show-toggle-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 표시여부_수정_실패_축제ID() throws Exception {
            var body = Map.of("show", true);

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}/show",
                                    잘못된_ID,
                                    스탬프_ID,
                                    미션_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampMissionAdminDocs.error("v2-mission-show-toggle-badrequest"));
        }
    }

    @Nested
    class 기본설정 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 기본설정_조회_성공() throws Exception {
            var prizes = List.of(new MissionPrizeResDto(1L, 5, "스티커 세트"), new MissionPrizeResDto(2L, 8, "티셔츠"));
            var res = new StampMissionBasicSettingsResDto(8, MissionDetailsDesignLayout.CARD, prizes);
            given(service.getBasicSettings(축제_ID, 스탬프_ID)).willReturn(res);

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
            var body = new MissionBasicSettingsReqDto(4, MissionDetailsDesignLayout.CARD);

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
        void 기본설정_업서트_실패_권한없음() throws Exception {
            var body = Map.of("missionCount", 6, "defaultDetailLayout", "TEXT_ONLY");
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
    }

    @Nested
    class 미션_상세설정 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션상세_조회_성공() throws Exception {
            var buttons = List.of(new ButtonResDto(0, "바로가기", "icon.png", ButtonAction.OPEN_URL, "https://a.b"));
            var extras = List.of(new MissionExtraInfoSummaryResDto(11L, "유의사항", "본인 신분증 지참"));
            var res = new MissionDetailsTemplateResDto(
                    MissionDetailsDesignLayout.CARD,
                    true,
                    "미션 타이틀",
                    true,
                    MediaSpec.NONE,
                    null,
                    true,
                    ExtraInfoLayout.LIST,
                    extras,
                    true,
                    ButtonLayout.ONE,
                    buttons);
            given(service.getMissionDetailsTemplate(축제_ID, 스탬프_ID, 미션_ID)).willReturn(res);

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
        @WithMockUser(roles = "ADMIN")
        void 미션상세_업서트_성공() throws Exception {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("missionDetailsDesignLayout", "CARD");
            body.put("missionTitle", "수정된 미션");
            body.put("showMissionTitle", true);
            body.put("showSuccessCount", true);
            body.put("showExtraInfos", true);
            body.put("showButtons", true);
            body.put("mediaSpec", "NONE");
            body.put("mediaUrl", null);
            body.put("extraInfoType", "LIST");
            body.put("buttonLayout", "ONE");

            List<Map<String, Object>> extraInfos = new ArrayList<>();
            Map<String, Object> info1 = new LinkedHashMap<>();
            info1.put("titleText", "유의사항");
            info1.put("bodyText", "본인 신분증 지참");
            extraInfos.add(info1);
            body.put("extraInfos", extraInfos);

            List<Map<String, Object>> buttons = new ArrayList<>();
            Map<String, Object> b1 = new LinkedHashMap<>();
            b1.put("sequenceIndex", 0);
            b1.put("iconImgUrl", "i");
            b1.put("content", "바로가기");
            b1.put("action", "OPEN_URL");
            b1.put("targetUrl", null); // null 허용: LinkedHashMap 사용으로 OK
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
                    .andDo(StampMissionAdminDocs.upsertMissionDetails());
        }
    }

    @Nested
    class 미션_완료이미지_QR {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 미션_완료이미지_조회_성공() throws Exception {
            var res = new StampMissionClearImageResDto(true, null, "not-cleared.jpg");
            given(service.getStampMissionCompleteImage(축제_ID, 스탬프_ID, 미션_ID)).willReturn(res);

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
        @WithMockUser(roles = "ADMIN")
        void 미션_완료이미지_수정_성공() throws Exception {
            var body = Map.of(
                    "showMissionTitle", true,
                    "clearedThumbnail", "cleared.jpg",
                    "notClearedThumbnail", "not-cleared.jpg");

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
        @WithMockUser(roles = "ADMIN")
        void 미션_QR_데이터_조회_성공() throws Exception {
            var list = List.of(new MissionQrDataResDto(201L, "미션1"), new MissionQrDataResDto(202L, "미션2"));
            given(service.getMissionsQrData(축제_ID, 스탬프_ID)).willReturn(list);

            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/qr", 축제_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampMissionAdminDocs.getQrData());
        }
    }

    @Nested
    class 경품_목록 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 조회_성공() throws Exception {
            var list = List.of(new MissionPrizeResDto(1L, 1, "스티커"), new MissionPrizeResDto(2L, 3, "티셔츠"));
            given(service.getPrizes(축제_ID, 스탬프_ID)).willReturn(list);

            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/missions/prizes",
                                    축제_ID,
                                    스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1L))
                    .andExpect(jsonPath("$[0].requiredCount").value(1))
                    .andExpect(jsonPath("$[0].prizeDescription").value("스티커"))
                    .andDo(StampMissionAdminDocs.getPrizeList());
        }

        @Test
        void 조회_실패_권한없음() throws Exception {
            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/prizes", 축제_ID, 스탬프_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampMissionAdminDocs.error("v2-stamptour-prize-list-unauthorized"));
        }
    }
}
