package com.halo.eventer.domain.inquiry.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.inquiry.Inquiry;
import com.halo.eventer.domain.inquiry.dto.InquiryAnswerReqDto;
import com.halo.eventer.domain.inquiry.dto.InquiryCreateReqDto;
import com.halo.eventer.domain.inquiry.dto.InquiryItemDto;
import com.halo.eventer.domain.inquiry.exception.InquiryNotFoundException;
import com.halo.eventer.domain.inquiry.repository.InquiryRepository;
import com.halo.eventer.global.security.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class InquiryServiceTest {
    @Mock
    private InquiryRepository inquiryRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private FestivalRepository festivalRepository;

    @InjectMocks
    private InquiryService inquiryService;

    private Inquiry inquiry;
    private Festival festival;
    private InquiryCreateReqDto secretInquiryCreateReqDto;

    @BeforeEach
    void setUp() {
        secretInquiryCreateReqDto = new InquiryCreateReqDto("제목",true,"aaa","1234","내용");
        festival = new Festival();
        inquiry = new Inquiry(festival, secretInquiryCreateReqDto,"1234");
    }

    @Test
    void 문의생성_성공(){
        // given
        given(passwordService.encode(anyString())).willReturn("password");
        given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));
        given(inquiryRepository.save(any())).willReturn(inquiry);

        // when
        String result = inquiryService.create(1L,secretInquiryCreateReqDto);

        // then
        assertThat(secretInquiryCreateReqDto.getPassword()).isEqualTo("password");
        verify(inquiryRepository, times(1)).save(any(Inquiry.class));
        assertThat(result).isEqualTo("저장완료");
    }

    @Test
    void 축제가_없는_경우_문의생성_실패(){
        // given
        given(festivalRepository.findById(1L)).willReturn(Optional.empty());
        given(passwordService.encode(anyString())).willReturn("password");

        //when & then
        assertThatThrownBy(() -> inquiryService.create(1L,secretInquiryCreateReqDto)).isInstanceOf(FestivalNotFoundException.class);
    }

    @Test
    void 관리자용_문의_전체조회(){
        //given
        given(inquiryRepository.findAllByFestivalId(1L)).willReturn(List.of(inquiry));

        //when
        List<InquiryItemDto> results = inquiryService.findAllInquiryForAdmin(1L);

        //then
        assertThat(results).hasSize(1);
        assertThat(results.get(0))
                .usingRecursiveComparison()
                .comparingOnlyFields("title","isAnswered","userId","createdDate")
                .isEqualTo(inquiry);
    }

    @Test
    void 관리자용_문의_전체조회_빈리스트_반환(){
        //given
        given(inquiryRepository.findAllByFestivalId(1L)).willReturn(List.of());

        //when
        List<InquiryItemDto> results = inquiryService.findAllInquiryForAdmin(1L);

        //then
        assertThat(results).isEmpty();
    }

    @Test
    void 관리자용_문의_단일조회(){
        //given
        given(inquiryRepository.findById(1L)).willReturn(Optional.of(inquiry));

        //when
        Inquiry result = inquiryService.getInquiryForAdmin(1L);

        //then
        assertThat(result).isEqualTo(inquiry);
    }

    @Test
    void 관리자용_문의_단일조회_문의가_없는_경우(){
        given(inquiryRepository.findById(1L)).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(()-> inquiryService.getInquiryForAdmin(1L)).isInstanceOf(InquiryNotFoundException.class);
    }

    @Test
    void 관리자용_문의_답변등록(){
        //given
        given(inquiryRepository.findById(1L)).willReturn(Optional.of(inquiry));
        InquiryAnswerReqDto dto = new InquiryAnswerReqDto("답변");

        //when
        Inquiry result = inquiryService.updateInquiry(1L,dto);

        //then
        assertThat(result.getAnswer()).isEqualTo(dto.getAnswer());
    }

    @Test
    void 관리자용_문의_삭제(){
        // given
        Long inquiryId = 1L;

        // when
        inquiryService.deleteInquiry(inquiryId);

        // then
        verify(inquiryRepository, times(1)).deleteById(inquiryId);
    }
}
