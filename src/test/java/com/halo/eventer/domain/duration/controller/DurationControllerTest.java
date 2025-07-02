package com.halo.eventer.domain.duration.controller;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.halo.eventer.domain.duration.DurationFixture;
import com.halo.eventer.domain.duration.api_docs.DurationDoc;
import com.halo.eventer.domain.duration.dto.DurationCreateDto;
import com.halo.eventer.domain.duration.dto.DurationResDto;
import com.halo.eventer.domain.duration.service.DurationService;
import com.halo.eventer.global.config.ControllerTestSecurityBeans;
import com.halo.eventer.global.config.CustomRestDocsConfig;
import com.halo.eventer.global.config.security.SecurityConfig;
import com.halo.eventer.global.security.provider.JwtProvider;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DurationController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SuppressWarnings("NonAsciiCharacters")
@ActiveProfiles("test")
@Import({ControllerTestSecurityBeans.class, SecurityConfig.class, CustomRestDocsConfig.class})
public class DurationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DurationService durationService;

    @MockitoBean
    private JwtProvider jwtProvider;

    private final Long festivalId = 1L;
    List<DurationCreateDto> durationCreateDtos;

    @BeforeEach
    void setUp() {
        durationCreateDtos = List.of(
                new DurationCreateDto(LocalDate.of(2025, 1, 1), 1), new DurationCreateDto(LocalDate.of(2025, 1, 2), 2));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void 어드민_축제기간_생성() throws Exception {
        // given
        doNothing().when(durationService).createDurations(any(), any());

        // when & then
        mockMvc.perform(post("/duration")
                        .queryParam("festivalId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer {access-token}")
                        .content(objectMapper.writeValueAsString(durationCreateDtos)))
                .andExpect(status().isOk())
                .andDo(DurationDoc.createDuration());
        verify(durationService, times(1)).createDurations(any(), any());
    }

    @Test
    void 일반유저_축제기간_생성_인증거부() throws Exception {
        // when & then
        mockMvc.perform(post("/duration")
                        .param("festivalId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(durationCreateDtos)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("code").value("A002"))
                .andExpect(jsonPath("message").value("Unauthenticated"))
                .andExpect(jsonPath("status").value(401))
                .andDo(DurationDoc.unauthenticated());
    }

    @Test
    void 축제기간_리스트_조회() throws Exception {
        // given
        DurationResDto res1 = new DurationResDto(DurationFixture.축제_첫째_날());
        DurationResDto res2 = new DurationResDto(DurationFixture.축제_둘째_날());
        List<DurationResDto> durationResDtos = List.of(res1, res2);
        given(durationService.getDurations(any())).willReturn(durationResDtos);

        // when & then
        mockMvc.perform(get("/duration").param("festivalId", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(durationResDtos.size()))
                .andExpect(jsonPath("$[0].durationId").value("1"))
                .andExpect(jsonPath("$[0].date").value("2025-01-01"))
                .andExpect(jsonPath("$[0].dayNumber").value(1))
                .andExpect(jsonPath("$[1].durationId").value("2"))
                .andExpect(jsonPath("$[1].date").value("2025-01-02"))
                .andExpect(jsonPath("$[1].dayNumber").value(2))
                .andDo(DurationDoc.getDurations());
    }
}
