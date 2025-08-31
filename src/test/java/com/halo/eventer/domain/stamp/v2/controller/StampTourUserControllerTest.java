package com.halo.eventer.domain.stamp.v2.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
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
import com.halo.eventer.domain.stamp.StampUser;
import com.halo.eventer.domain.stamp.controller.v2.StampTourUserController;
import com.halo.eventer.domain.stamp.dto.mission.response.MissionBoardResDto;
import com.halo.eventer.domain.stamp.dto.mission.response.MissionExtraInfoSummaryResDto;
import com.halo.eventer.domain.stamp.dto.mission.response.MissionIconRes;
import com.halo.eventer.domain.stamp.dto.mission.response.MissionTemplateResDto;
import com.halo.eventer.domain.stamp.dto.stamp.enums.ButtonAction;
import com.halo.eventer.domain.stamp.dto.stamp.enums.ButtonLayout;
import com.halo.eventer.domain.stamp.dto.stamp.enums.MissionDetailsDesignLayout;
import com.halo.eventer.domain.stamp.dto.stamp.response.ButtonResDto;
import com.halo.eventer.domain.stamp.dto.stampUser.request.MissionQrVerifyReqDto;
import com.halo.eventer.domain.stamp.dto.stampUser.request.StampUserLoginDto;
import com.halo.eventer.domain.stamp.dto.stampUser.request.StampUserSignupReqDto;
import com.halo.eventer.domain.stamp.dto.stampUser.response.PrizeClaimQrResDto;
import com.halo.eventer.domain.stamp.service.v2.StampTourUserService;
import com.halo.eventer.domain.stamp.v2.api_docs.StampTourUserDocs;
import com.halo.eventer.global.config.ControllerTestSecurityBeans;
import com.halo.eventer.global.config.CustomRestDocsConfig;
import com.halo.eventer.global.config.security.SecurityConfig;
import com.halo.eventer.global.security.CustomStampUserDetails;
import com.halo.eventer.global.security.provider.JwtProvider;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StampTourUserController.class)
@AutoConfigureRestDocs
@Import({ControllerTestSecurityBeans.class, SecurityConfig.class, CustomRestDocsConfig.class})
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class StampTourUserControllerTest {

    private static final long 축제_ID = 1L;
    private static final long 스탬프투어_ID = 10L;
    private static final long 미션_ID = 101L;
    private static final String AUTH = "Bearer stamp-token";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    StampTourUserService service;

    @MockitoBean
    JwtProvider jwtProvider;

    // ---- permitAll: 회원가입
    @Nested
    class 회원가입 {

        @Test
        void 성공() throws Exception {
            var body = new StampUserSignupReqDto("홍길동", "010-1111-2222", 2, "추가메모");
            mockMvc.perform(post("/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/signup", 축제_ID, 스탬프투어_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampTourUserDocs.signup());
        }

        @Test
        void 실패_검증오류() throws Exception {
            // phone 누락
            var bad = new StampUserLoginDto("홍길동", "");
            mockMvc.perform(post("/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/login", 축제_ID, 스탬프투어_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(bad)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourUserDocs.error("v2-user-stamptour-login-badrequest"));
        }
    }

    // ---- permitAll: 로그인 (응답 헤더 Authorization 확인)
    @Nested
    class 로그인 {

        @Test
        void 성공() throws Exception {
            var body = new StampUserLoginDto("홍길동", "010-1111-2222");

            // service.login 호출 시 응답 헤더만 세팅해주도록 스텁
            doAnswer((Answer<Void>) invocation -> {
                        HttpServletResponse resp = invocation.getArgument(3);
                        resp.setHeader("Authorization", "Bearer test-token");
                        return null; // 바디는 문서화하지 않음
                    })
                    .when(service)
                    .login(eq(축제_ID), eq(스탬프투어_ID), any(StampUserLoginDto.class), any(HttpServletResponse.class));

            mockMvc.perform(post("/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/login", 축제_ID, 스탬프투어_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andExpect(header().string("Authorization", org.hamcrest.Matchers.startsWith("Bearer ")))
                    .andDo(StampTourUserDocs.login());
        }

        @Test
        void 실패_검증오류() throws Exception {
            // phone 누락
            var bad = new StampUserLoginDto("홍길동", "");
            mockMvc.perform(post("/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/login", 축제_ID, 스탬프투어_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(bad)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourUserDocs.error("v2-user-stamptour-login-badrequest"));
        }
    }

    // ---- ROLE_STAMP 필요: 미션 보드
    @Nested
    class 미션보드 {

        CustomStampUserDetails 스탬프_유저() {
            return new CustomStampUserDetails(new StampUser("010-1111-2222", "홍길동", 1));
        }

        @Test
        void 조회_성공() throws Exception {
            var board = new MissionBoardResDto(
                    2,
                    3,
                    false,
                    List.of(
                            new MissionIconRes(101L, "포토부스 방문", true, "https://img.example/mission101-cleared.jpg"),
                            new MissionIconRes(102L, "동아리 체험", false, "https://img.example/mission102-not.jpg"),
                            new MissionIconRes(103L, "스폰서 부스", true, "https://img.example/mission103-cleared.jpg")));

            given(service.getMissionBoard(eq(축제_ID), eq(스탬프투어_ID), anyString())).willReturn(board);

            mockMvc.perform(get("/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/missions", 축제_ID, 스탬프투어_ID)
                            .with(user(스탬프_유저()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .header(HttpHeaders.AUTHORIZATION, AUTH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.completedCount").value(2))
                    .andExpect(jsonPath("$.missions[0].missionId").value(101L))
                    .andDo(StampTourUserDocs.missionBoard());
        }

        @Test
        void 실패_권한없음() throws Exception {
            mockMvc.perform(get("/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/missions", 축제_ID, 스탬프투어_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourUserDocs.error("v2-user-stamptour-mission-board-unauthorized"));
        }

        @Test
        void 실패_검증오류_PathVariable() throws Exception {
            mockMvc.perform(get(
                                    "/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/missions",
                                    축제_ID,
                                    0L) // @Min(1) 위반
                            .with(user(스탬프_유저())))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourUserDocs.error("v2-user-stamptour-mission-board-badrequest"));
        }
    }

    // ---- ROLE_STAMP 필요: 미션 상세
    @Nested
    class 미션상세 {

        CustomStampUserDetails 스탬프_유저() {
            return new CustomStampUserDetails(new StampUser("010-1111-2222", "홍길동", 1));
        }

        @Test
        void 조회_성공() throws Exception {
            var extras = List.of(
                    new MissionExtraInfoSummaryResDto(11L, "유의사항", "본인 신분증 지참"),
                    new MissionExtraInfoSummaryResDto(12L, "운영시간", "10:00~18:00"),
                    new MissionExtraInfoSummaryResDto(13L, "참여대상", "재학생/교직원"));

            var res = new MissionTemplateResDto(
                    미션_ID,
                    "미션 제목",
                    "https://img",
                    MissionDetailsDesignLayout.CARD,
                    1,
                    3,
                    extras, // <= 여기
                    ButtonLayout.ONE,
                    List.of(new ButtonResDto(0, "바로가기", null, ButtonAction.OPEN_URL, "https://a.b")));
            given(service.getMissionsTemplate(eq(축제_ID), eq(스탬프투어_ID), eq(미션_ID), anyString()))
                    .willReturn(res);
            mockMvc.perform(get(
                                    "/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}",
                                    축제_ID,
                                    스탬프투어_ID,
                                    미션_ID)
                            .with(user(스탬프_유저()))
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(StampTourUserDocs.missionDetails());
        }

        @Test
        void 실패_권한없음() throws Exception {
            mockMvc.perform(get(
                            "/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}",
                            축제_ID,
                            스탬프투어_ID,
                            미션_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourUserDocs.error("v2-user-stamptour-mission-details-unauthorized"));
        }

        @Test
        void 실패_검증오류_PathVariable() throws Exception {
            mockMvc.perform(get(
                                    "/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/missions/{missionId}",
                                    축제_ID,
                                    스탬프투어_ID,
                                    0L) // missionId @Min(1) 위반
                            .with(user(스탬프_유저())))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourUserDocs.error("v2-user-stamptour-mission-details-badrequest"));
        }
    }

    // ---- ROLE_STAMP 필요: QR 인증
    @Nested
    class 미션_QR_인증 {

        CustomStampUserDetails 스탬프_유저() {
            return new CustomStampUserDetails(new StampUser("010-1111-2222", "홍길동", 1));
        }

        @Test
        void 성공() throws Exception {
            var body = new MissionQrVerifyReqDto(미션_ID);

            mockMvc.perform(patch(
                                    "/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/verify/qr",
                                    축제_ID,
                                    스탬프투어_ID)
                            .with(user(스탬프_유저()))
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isOk())
                    .andDo(StampTourUserDocs.verifyByQr());
        }

        @Test
        void 실패_권한없음() throws Exception {
            var body = new MissionQrVerifyReqDto(미션_ID);
            mockMvc.perform(patch(
                                    "/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/verify/qr",
                                    축제_ID,
                                    스탬프투어_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourUserDocs.error("v2-user-stamptour-verify-qr-unauthorized"));
        }

        @Test
        void 실패_검증오류_PathVariable() throws Exception {
            var body = new MissionQrVerifyReqDto(미션_ID);
            mockMvc.perform(patch(
                                    "/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/verify/qr",
                                    축제_ID,
                                    0L) // @Min(1) 위반
                            .with(user(스탬프_유저()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body)))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourUserDocs.error("v2-user-stamptour-verify-qr-badrequest"));
        }
    }

    // ---- ROLE_STAMP 필요: 경품 수령 QR 정보
    @Nested
    class 경품_QR {

        CustomStampUserDetails 스탬프_유저() {
            return new CustomStampUserDetails(new StampUser("010-1111-2222", "홍길동", 2));
        }

        @Test
        @WithMockUser(roles = "STAMP")
        void 조회_성공() throws Exception {
            var res = new PrizeClaimQrResDto("홍길동", "010-1111-2222", 2, "추가메모");
            given(service.getPrizeReceiveQr(eq(축제_ID), eq(스탬프투어_ID), anyString()))
                    .willReturn(res);

            mockMvc.perform(get("/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/prizes/qr", 축제_ID, 스탬프투어_ID)
                            .with(user(스탬프_유저()))
                            .header(HttpHeaders.AUTHORIZATION, AUTH)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.userName").value("홍길동")) // 바디가 있는지 sanity check
                    .andDo(StampTourUserDocs.prizeQr());
        }

        @Test
        void 실패_권한없음() throws Exception {
            mockMvc.perform(get("/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/prizes/qr", 축제_ID, 스탬프투어_ID))
                    .andExpect(status().isUnauthorized())
                    .andDo(StampTourUserDocs.error("v2-user-stamptour-prize-qr-unauthorized"));
        }

        @Test
        void 실패_검증오류_PathVariable() throws Exception {
            mockMvc.perform(get(
                                    "/api/v2/user/festivals/{festivalId}/stamp-tours/{stampId}/prizes/qr",
                                    축제_ID,
                                    0L) // @Min(1) 위반
                            .with(user(스탬프_유저())))
                    .andExpect(status().isBadRequest())
                    .andDo(StampTourUserDocs.error("v2-user-stamptour-prize-qr-badrequest"));
        }
    }
}
