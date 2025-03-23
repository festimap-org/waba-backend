package com.halo.eventer.domain.stamp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.dto.FestivalCreateDto;
import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.dto.stamp.*;
import com.halo.eventer.domain.stamp.service.StampService;
import com.halo.eventer.global.error.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StampController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class StampControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StampService stampService;

    private Stamp stamp;

    @BeforeEach
    void setUp() {
        stamp = Stamp.create(Festival.from(
                new FestivalCreateDto("test festival","test")
        ));
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new StampController(stampService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void 스탬프_생성_성공() throws Exception {
        given(stampService.registerStamp(anyLong()))
                .willReturn(List.of(StampGetDto.from(stamp)));

        mockMvc.perform(post("/stamp")
                        .param("festivalId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void 스탬프_조회_성공() throws Exception {
        given(stampService.getStampByFestivalId(anyLong()))
                .willReturn(List.of(StampGetDto.from(stamp)));

        mockMvc.perform(get("/stamp")
                        .param("festivalId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void 스탬프_상태변경_성공() throws Exception {
        mockMvc.perform(patch("/stamp")
                        .param("stampId", "1"))
                .andExpect(status().isOk());
        then(stampService).should().updateStampOn(1L);
    }

    @Test
    void 스탬프_삭제_성공() throws Exception {
        mockMvc.perform(delete("/stamp")
                        .param("stampId", "1"))
                .andExpect(status().isOk());
        then(stampService).should().deleteStamp(1L);
    }

    @Test
    void 미션_생성_성공() throws Exception {
        MissionSetListDto dto = new MissionSetListDto();
        mockMvc.perform(post("/stamp/mission")
                        .param("stampId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());
        then(stampService).should().createMission(anyLong(), any());
    }

    @Test
    void 미션_리스트_조회_성공() throws Exception {
        given(stampService.getMissions(anyLong()))
                .willReturn(List.of(new MissionSummaryGetDto()));

        mockMvc.perform(get("/stamp/missions")
                        .param("stampId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void 유저_리스트_조회_성공() throws Exception {
        given(stampService.getStampUsers(anyLong()))
                .willReturn(List.of(new StampUsersGetDto()));

        mockMvc.perform(get("/stamp/users")
                        .param("stampId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void 완료_기준_설정_성공() throws Exception {
        mockMvc.perform(post("/stamp/finishCnt")
                        .param("stampId", "1")
                        .param("cnt", "3"))
                .andExpect(status().isOk());
        then(stampService).should().setFinishCnt(1L, 3);
    }
}
