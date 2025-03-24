package com.halo.eventer.domain.inquiry.controller;


import com.halo.eventer.domain.inquiry.service.InquiryService;
import com.halo.eventer.global.config.TestSecurityBeans;
import com.halo.eventer.global.config.security.SecurityConfig;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(InquiryController.class)
@AutoConfigureMockMvc
@SuppressWarnings("NonAsciiCharacters")
@ActiveProfiles("test")
@Import({TestSecurityBeans.class, SecurityConfig.class})
public class InquiryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private InquiryService inquiryService;

    @InjectMocks
    private InquiryController inquiryController;

}
