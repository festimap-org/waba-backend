package com.halo.eventer.festival;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.halo.eventer.festival.controller.FestivalController;
import com.halo.eventer.festival.dto.FestivalCreateDto;
import com.halo.eventer.festival.repository.FestivalRepository;
import com.halo.eventer.festival.service.FestivalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FestivalController.class)
public class FestivalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FestivalService festivalService;

    @MockBean
    private FestivalRepository festivalRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("/festival에 데이터를 전달하고 성공적으로 저장이 완료되었을 경우 return되는 값 테스트")
    void createFestivalCorrectTest()throws Exception{
        FestivalCreateDto festivalCreateDto = new FestivalCreateDto("1","2","3","4");
        String testDtoJson = objectMapper.writeValueAsString(festivalCreateDto);

        given(festivalService.createFestival(any(FestivalCreateDto.class))).willReturn("저장완료");

        mockMvc.perform(post("/festival")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testDtoJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("저장완료"));
    }


}
