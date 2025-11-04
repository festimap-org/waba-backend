package com.halo.eventer.domain.stamp.v2.controller;

import java.time.LocalDateTime;
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
import com.halo.eventer.domain.stamp.dto.mission.request.MissionClearReqDto;
import com.halo.eventer.domain.stamp.dto.stampUser.enums.Finished;
import com.halo.eventer.domain.stamp.dto.stampUser.enums.SortType;
import com.halo.eventer.domain.stamp.dto.stampUser.request.MissionCompletionUpdateReq;
import com.halo.eventer.domain.stamp.dto.stampUser.request.StampUserInfoUpdateReqDto;
import com.halo.eventer.domain.stamp.dto.stampUser.response.*;
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

    private static final long 유저_ID = 999L;
    private static final long 유저미션_ID = 5001L;
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
        var u1 = new StampUserSummaryResDto(1L, "김수한", "010-1234-5678", "uuid-1", false, LocalDateTime.now());
        var u2 = new StampUserSummaryResDto(2L, "박영희", "010-2222-3333", "uuid-2", true, LocalDateTime.now());
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
                "김수한", "010-1234-5678", 사용자_UUID, "티셔츠", false, missions, "this is extra text", 4);
    }

    @Nested
    class 목록_조회 {
        @Test
        @WithMockUser(roles = "ADMIN")
        void 성공() throws Exception {
            given(service.getStampUsers(축제_ID, 스탬프_ID, "김", Finished.ALL, 0, 10, SortType.NAME))
                    .willReturn(샘플_목록());

            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users", 축제_ID, 스탬프_ID)
                            .param("q", "김")
                            .param("finished", "ALL")
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
    class 투어완료_및_경품수정 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 수정_성공() throws Exception {
            var req = new MissionCompletionUpdateReq(true, "티셔츠");

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}/tour-finish",
                                    축제_ID,
                                    스탬프_ID,
                                    유저_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampUserAdminDocs.updateTourFinish());
        }

        @Test
        void 수정_실패_권한없음() throws Exception {
            var req = new MissionCompletionUpdateReq(false, null);

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}/tour-finish",
                                    축제_ID,
                                    스탬프_ID,
                                    유저_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-tourfinish-unauthorized"));
        }
    }

    private StampUserDetailResDto 샘플상세() {
        var missions = List.of(
                new UserMissionStatusResDto(100L, 1001L, "미션1", true),
                new UserMissionStatusResDto(101L, 1002L, "미션2", false));
        return new StampUserDetailResDto("홍길동", "010-1234-5678", "uuid-1234", "티셔츠", false, missions, "비고", 3);
    }

    @Nested
    class 사용자_상세_조회 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 성공() throws Exception {
            given(service.getUserDetail(축제_ID, 스탬프_ID, 사용자_ID)).willReturn(샘플상세());

            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}",
                                    축제_ID,
                                    스탬프_ID,
                                    사용자_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andDo(StampUserAdminDocs.getUser());
        }

        @Test
        void 실패_권한없음() throws Exception {
            mockMvc.perform(get(
                            "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}",
                            축제_ID,
                            스탬프_ID,
                            사용자_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-get-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 실패_축제ID_검증() throws Exception {
            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}",
                                    잘못된_ID,
                                    스탬프_ID,
                                    사용자_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isBadRequest())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-get-badrequest"));
        }
    }

    @Nested
    class 유저미션_수정 {
        @Test
        @WithMockUser(roles = "ADMIN")
        void 유저미션_완료여부_수정_성공() throws Exception {
            var req = new MissionClearReqDto(true);

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}/user-mission/{userMissionId}",
                                    축제_ID,
                                    스탬프_ID,
                                    사용자_ID,
                                    유저미션_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andDo(StampUserAdminDocs.updateUserMission());
        }

        @Test
        void 유저미션_완료여부_수정_실패_권한없음() throws Exception {
            var req = new MissionClearReqDto(false);

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}/user-mission/{userMissionId}",
                                    축제_ID,
                                    스탬프_ID,
                                    사용자_ID,
                                    유저미션_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-updatemission-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 유저미션_완료여부_수정_실패_축제ID_검증() throws Exception {
            var req = new MissionClearReqDto(true);

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}/user-mission/{userMissionId}",
                                    잘못된_ID,
                                    스탬프_ID,
                                    사용자_ID,
                                    유저미션_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-updatemission-badrequest"));
        }
    }

    @Nested
    class 사용자_정보_수정 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 성공() throws Exception {
            var body = new StampUserInfoUpdateReqDto("홍길동", "010-1234-5678", 3, "비고입니다");

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}",
                                    축제_ID,
                                    스탬프_ID,
                                    사용자_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampUserAdminDocs.updateUserInfo());
        }

        @Test
        void 실패_권한없음() throws Exception {
            var body = Map.of("name", "홍길동");

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}",
                                    축제_ID,
                                    스탬프_ID,
                                    사용자_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 실패_경로파라미터_검증() throws Exception {
            var body = Map.of("phone", "010-9999-8888");

            mockMvc.perform(patch(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/{userId}",
                                    잘못된_ID,
                                    스탬프_ID,
                                    사용자_ID) // festivalId = 0 → @Min(1) 위반
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class UUID로_userId_조회 {
        @Test
        @WithMockUser(roles = "ADMIN")
        void 성공() throws Exception {
            long festivalId = 1L;
            long stampId = 10L;
            String uuid = "550e8400-e29b-41d4-a716-446655440000";
            given(service.getStampUserId(festivalId, stampId, uuid)).willReturn(new StampUserUserIdResDto(123L));
            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/uuid/{uuid}",
                                    festivalId,
                                    stampId,
                                    uuid)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.userId").value(123))
                    .andDo(StampUserAdminDocs.getUserIdByUuid());
        }
    }

    @Nested
    class 전체_사용자_상태_조회 {

        @Test
        @WithMockUser(roles = "ADMIN")
        void 전체_사용자_목록_성공() throws Exception {
            var now = LocalDateTime.of(2025, 10, 1, 10, 30, 0);
            var list = List.of(
                    new StampUserInfoResDto(1L, "홍길동", "010-1111-2222", "uuid-1", true, 2, "티셔츠",now),
                    new StampUserInfoResDto(2L, "김영희", "010-3333-4444", "uuid-2", false, 1, "티셔츠",now.minusDays(1)));

            given(service.getAllStampUsers(축제_ID, 스탬프_ID)).willReturn(list);

            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/all", 축제_ID, 스탬프_ID)
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].name").value("홍길동"))
                    .andExpect(jsonPath("$[0].phone").value("010-1111-2222"))
                    .andExpect(jsonPath("$[0].uuid").value("uuid-1"))
                    .andExpect(jsonPath("$[0].finished").value(true))
                    .andExpect(jsonPath("$[0].participantCount").value(2))
                    .andExpect(jsonPath("$[0].receivedPrizeName").value("티셔츠"))
                    .andExpect(jsonPath("$[0].createdAt").exists())
                    .andDo(StampUserAdminDocs.listAllUsers()); // REST Docs
        }

        @Test
        void 전체_사용자_목록_실패_권한없음() throws Exception {
            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/all", 축제_ID, 스탬프_ID)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-all-unauthorized"));
        }

        @Test
        void 실패_권한없음() throws Exception {
            mockMvc.perform(get("/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/all", 축제_ID, 스탬프_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-all-unauthorized"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        void 실패_경로파라미터_검증() throws Exception {
            mockMvc.perform(get(
                                    "/api/v2/admin/festivals/{festivalId}/stamp-tours/{stampId}/users/all",
                                    잘못된_ID,
                                    스탬프_ID) // festivalId = 0 → @Min(1) 위반
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isBadRequest())
                    .andDo(StampUserAdminDocs.error("v2-stampuser-all-badrequest"));
        }
    }
}
