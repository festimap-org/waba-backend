package com.halo.eventer.domain.stamp.v2.controller;

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
import com.halo.eventer.domain.stamp.controller.v2.StampUserAdminController;
import com.halo.eventer.domain.stamp.dto.stampUser.enums.MissionCleared;
import com.halo.eventer.domain.stamp.dto.stampUser.enums.SortType;
import com.halo.eventer.domain.stamp.dto.stampUser.response.StampUserDetailResDto;
import com.halo.eventer.domain.stamp.dto.stampUser.response.StampUserSummaryResDto;
import com.halo.eventer.domain.stamp.dto.stampUser.response.UserMissionStatusResDto;
import com.halo.eventer.domain.stamp.service.v2.StampUserAdminService;
import com.halo.eventer.domain.stamp.v2.api_docs.StampUserAdminDocs;
import com.halo.eventer.global.common.page.PageInfo;
import com.halo.eventer.global.common.page.PagedResponse;
import com.halo.eventer.global.config.ControllerTestSecurityBeans;
import com.halo.eventer.global.config.CustomRestDocsConfig;
import com.halo.eventer.global.config.security.SecurityConfig;
import com.halo.eventer.global.security.provider.JwtProvider;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StampUserAdminController.class)
@AutoConfigureRestDocs
@Import({ControllerTestSecurityBeans.class, SecurityConfig.class, CustomRestDocsConfig.class})
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class StampUserAdminControllerTest {

    private static final String AUTH = "Bearer admin-token";
    private static final long 축제_ID = 1L;
    private static final long 스탬프_ID = 10L;
    private static final long 사용자_ID = 77L;
    private static final String 사용자_UUID = "uuid-1234";
    private static final long 잘못된_ID = 0L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    StampUserAdminService service;

    @MockitoBean
    JwtProvider jwtProvider;

    private PagedResponse<StampUserSummaryResDto> 샘플_목록() {
        var u1 = new StampUserSummaryResDto(1L, "김수한", "010-1234-5678", "uuid-1", false);
        var u2 = new StampUserSummaryResDto(2L, "박영희", "010-2222-3333", "uuid-2", true);
        var pageInfo = PageInfo.builder()
                .pageNumber(1) // 문서화 편의상 1-base
                .pageSize(10)
                .totalElements(2)
                .totalPages(1)
                .build();
        return PagedResponse.<StampUserSummaryResDto>builder()
                .content(List.of(u1, u2))
                .pageInfo(pageInfo)
                .build();
    }

    private StampUserDetailResDto 샘플_상세() {
        List<UserMissionStatusResDto> missions = List.of(
                new UserMissionStatusResDto(100L, 1001L, "미션1", true),
                new UserMissionStatusResDto(101L, 1002L, "미션2", false));
        return new StampUserDetailResDto(
                사용자_ID, "김수한", "010-1234-5678", 사용자_UUID, false, missions, "this is extra text", 4);
    }

    @Nested
    class 목록_조회 {
        @Test
        @WithMockUser(roles = "ADMIN")
        void 성공() throws Exception {
            given(service.getStampUsers(축제_ID, 스탬프_ID, "김", MissionCleared.ALL, 0, 10, SortType.NAME))
                    .willReturn(샘플_목록());

            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users", 축제_ID, 스탬프_ID)
                            .param("q", "김")
                            .param("missionCleared", "ALL")
                            .param("page", "0")
                            .param("size", "10")
                            .param("sortType", "NAME")
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampUserAdminDocs.listUsers());
        }

        @Test
        void 실패_권한없음() throws Exception {
            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users", 축제_ID, 스탬프_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-list-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 실패_축제ID_검증() throws Exception {
            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users", 잘못된_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isBadRequest())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-list-badrequest"));
        }
    }

    @Nested
    class 상세_조회 {
        @Test
        @WithMockUser(roles = "ADMIN")
        void 성공() throws Exception {
            given(service.getUserDetail(축제_ID, 스탬프_ID, 사용자_UUID)).willReturn(샘플_상세());

            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{uuid}",
                                    축제_ID,
                                    스탬프_ID,
                                    사용자_UUID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampUserAdminDocs.getUser());
        }

        @Test
        void 실패_권한없음() throws Exception {
            mockMvc.perform(get(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{uuid}",
                            축제_ID,
                            스탬프_ID,
                            사용자_UUID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-get-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 실패_축제ID_검증() throws Exception {
            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{uuid}",
                                    잘못된_ID,
                                    스탬프_ID,
                                    사용자_UUID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isBadRequest())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-get-badrequest"));
        }
    }

    @Nested
    class 전체미션_완료_토글 {
        @Test
        @WithMockUser(roles = "ADMIN")
        void 성공_complete_true() throws Exception {
            given(service.setAllMissionsCompletion(축제_ID, 스탬프_ID, 사용자_ID, true)).willReturn(샘플_상세());

            var body = Map.of("complete", true);

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}/missions/all",
                                    축제_ID,
                                    스탬프_ID,
                                    사용자_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampUserAdminDocs.setAllMissionsCompletion());
        }

        @Test
        void 실패_권한없음() throws Exception {
            var body = Map.of("complete", true);

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}/missions/all",
                                    축제_ID,
                                    스탬프_ID,
                                    사용자_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-missions-all-unauthorized"));
        }
    }
}
