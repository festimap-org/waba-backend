package com.halo.eventer.domain.stamp.v2.controller;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.halo.eventer.domain.stamp.controller.v2.StampTourTemplateController;
import com.halo.eventer.domain.stamp.dto.mission.response.MissionPrizeResDto;
import com.halo.eventer.domain.stamp.dto.stamp.enums.*;
import com.halo.eventer.domain.stamp.dto.stamp.request.StampTourSignUpTemplateResDto;
import com.halo.eventer.domain.stamp.dto.stamp.response.*;
import com.halo.eventer.domain.stamp.service.v2.StampTourTemplateService;
import com.halo.eventer.domain.stamp.v2.api_docs.StampTourTemplateDocs;
import com.halo.eventer.global.config.ControllerTestSecurityBeans;
import com.halo.eventer.global.config.CustomRestDocsConfig;
import com.halo.eventer.global.config.security.SecurityConfig;
import com.halo.eventer.global.security.provider.JwtProvider;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StampTourTemplateController.class)
@AutoConfigureRestDocs
@Import({ControllerTestSecurityBeans.class, SecurityConfig.class, CustomRestDocsConfig.class})
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class StampTourTemplateControllerTest {

    private static final long 축제_ID = 1L;
    private static final long 스탬프_ID = 10L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    StampTourTemplateService service;

    @MockitoBean
    JwtProvider jwtProvider;

    @Nested
    class 목록 {

        @Test
        void 스탬프투어_목록_조회_성공() throws Exception {
            var list = List.of(new StampTourSummaryResDto(10L, "A", true), new StampTourSummaryResDto(11L, "B", false));
            given(service.getStampTourList(축제_ID)).willReturn(list);

            mockMvc.perform(get("/api/v2/template/festivals/{festivalId}/stamp-tours", 축제_ID))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].stampId").value(10L))
                    .andDo(StampTourTemplateDocs.listStampTours());
        }
    }

    @Nested
    class 회원가입_설정 {

        @Test
        void 회원가입_템플릿_조회_성공() throws Exception {
            var res = new StampTourSignUpTemplateResDto(JoinVerificationMethod.SMS);
            given(service.getSignupTemplate(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get("/api/v2/template/festivals/{festivalId}/stamp-tours/{stampId}/signup", 축제_ID, 스탬프_ID))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.joinVerificationMethod").value("SMS"))
                    .andDo(StampTourTemplateDocs.getSignUpTemplate());
        }
    }

    @Nested
    class 랜딩 {

        @Test
        void 랜딩_페이지_조회_성공() throws Exception {
            var buttons = List.of(new ButtonResDto(0, "바로가기", "btn.png", ButtonAction.OPEN_URL, "https://a.b"));
            var res = new StampTourLandingPageResDto(
                    LandingPageDesignTemplate.NONE, "icon.png", "bg.png", "설명", ButtonLayout.ONE, buttons);
            given(service.getLandingPage(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get("/api/v2/template/festivals/{festivalId}/stamp-tours/{stampId}/landing", 축제_ID, 스탬프_ID))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.landingPageDesignTemplate").value("NONE"))
                    .andDo(StampTourTemplateDocs.getLanding());
        }
    }

    @Nested
    class 참여_인증_템플릿 {

        @Test
        void 참여_인증_템플릿_조회_성공() throws Exception {
            var res = new StampTourJoinTemplateResDto(JoinVerificationMethod.PASS);
            given(service.getStampTourJoinMethod(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                            "/api/v2/template/festivals/{festivalId}/stamp-tours/{stampId}/join-template",
                            축제_ID,
                            스탬프_ID))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.method").value("PASS"))
                    .andDo(StampTourTemplateDocs.getJoinTemplate());
        }
    }

    @Nested
    class 인증_방법 {

        @Test
        void 인증_방법_조회_성공() throws Exception {
            var res = new StampTourAuthMethodResDto(AuthMethod.TAG_SCAN);
            given(service.getAuthMethod(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get(
                            "/api/v2/template/festivals/{festivalId}/stamp-tours/{stampId}/auth-method", 축제_ID, 스탬프_ID))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.authMethod").value("TAG_SCAN"))
                    .andDo(StampTourTemplateDocs.getAuthMethod());
        }
    }

    @Nested
    class 메인 {

        @Test
        void 메인_페이지_조회_성공() throws Exception {
            var buttons = List.of(
                    new ButtonResDto(0, "QR", "q.png", ButtonAction.QR_CAMERA, null),
                    new ButtonResDto(1, "URL", "u.png", ButtonAction.OPEN_URL, "https://x.y"));
            var res = new StampTourMainPageResDto(MainPageDesignTemplate.GRID_Nx2, "bg.png", ButtonLayout.ONE, buttons);
            given(service.getMainPage(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get("/api/v2/template/festivals/{festivalId}/stamp-tours/{stampId}/main", 축제_ID, 스탬프_ID))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.mainPageDesignTemplate").value("GRID_Nx2"))
                    .andDo(StampTourTemplateDocs.getMain());
        }
    }

    @Nested
    class 안내사항 {

        @Test
        void 안내사항_조회_성공() throws Exception {
            var res = new StampTourNotificationResDto("주의사항", "개인정보 고지");
            given(service.getStampTourNotification(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get("/api/v2/template/festivals/{festivalId}/stamp-tours/{stampId}/notice", 축제_ID, 스탬프_ID))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.cautionContent").value("주의사항"))
                    .andDo(StampTourTemplateDocs.getNotice());
        }
    }

    @Nested
    class 참여가이드 {

        @Test
        void 참여가이드_조회_성공() throws Exception {
            var pages = List.of(
                    new StampTourGuidePageResDto(101L, "p1", MediaSpec.ONE_TO_ONE, "m1.png", "s1", "d1", "a1"),
                    new StampTourGuidePageResDto(102L, "p2", MediaSpec.NONE, null, "s2", "d2", "a2"));
            var res = new StampTourGuideResDto(1L, GuideDesignTemplate.FULL, GuideSlideMethod.SLIDE, pages);
            given(service.getParticipateGuide(축제_ID, 스탬프_ID)).willReturn(res);

            mockMvc.perform(get("/api/v2/template/festivals/{festivalId}/stamp-tours/{stampId}/guide", 축제_ID, 스탬프_ID))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.participateGuideId").value(1L))
                    .andExpect(jsonPath("$.participateGuidePages[0].pageId").value(101L))
                    .andDo(StampTourTemplateDocs.getGuide());
        }
    }

    @Nested
    class 경품_목록_조회 {
        private List<MissionPrizeResDto> 샘플_경품목록() {
            return List.of(new MissionPrizeResDto(1L, 3, "스티커"), new MissionPrizeResDto(2L, 5, "티셔츠"));
        }

        @Test
        void 성공() throws Exception {
            given(service.getPrizes(축제_ID, 스탬프_ID)).willReturn(샘플_경품목록());

            mockMvc.perform(get("/api/v2/template/festivals/{festivalId}/stamp-tours/{stampId}/prizes", 축제_ID, 스탬프_ID)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(StampTourTemplateDocs.listPrizes());
        }
    }

    @Test
    void 스탬프활성화_조회_성공() throws Exception {
        // given
        long festivalId = 2L;
        long stampId = 1L;
        given(service.getStampActive(festivalId, stampId)).willReturn(new StampActiveResDto("가을축제 스탬프", true));

        // when & then
        mockMvc.perform(get("/api/v2/template/festivals/{festivalId}/stamp-tours/{stampId}", festivalId, stampId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("가을축제 스탬프"))
                .andExpect(jsonPath("$.active").value(true))
                .andDo(StampTourTemplateDocs.getStampActive());
    }
}
