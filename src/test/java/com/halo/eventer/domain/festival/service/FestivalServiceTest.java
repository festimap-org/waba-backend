package com.halo.eventer.domain.festival.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.dto.*;
import com.halo.eventer.domain.festival.exception.FestivalAlreadyExistsException;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.global.common.ImageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
public class FestivalServiceTest {

  @Mock
  private FestivalRepository festivalRepository;

  @InjectMocks
  private FestivalService festivalService;

  private Festival festival;
  private FestivalCreateDto festivalCreateDto;

  @BeforeEach
  void setUp() {
    festivalCreateDto = new FestivalCreateDto("축제","univ");
    festival = new Festival(festivalCreateDto);
  }

  @Test
  void 축제생성_성공() {
    // given
    given(festivalRepository.findByName(anyString())).willReturn(Optional.empty());
    given(festivalRepository.findBySubAddress(anyString())).willReturn(Optional.empty());
    given(festivalRepository.save(any())).willReturn(festival);

    // when
    String result = festivalService.create(festivalCreateDto);

    // then
    assertThat(result).isEqualTo("저장완료");
  }

  @Test()
  void 축제생성_이름이같은_경우(){
    // given
    given(festivalRepository.findByName(anyString())).willReturn(Optional.of(festival));

    //when & then
    assertThatThrownBy(() -> festivalService.create(festivalCreateDto)).isInstanceOf(FestivalAlreadyExistsException.class);
  }

  @Test
  void 축제생성_서브주소같은_경우(){
    //given
    given(festivalRepository.findBySubAddress(anyString())).willReturn(Optional.of(festival));

    //when & then
    assertThatThrownBy(() -> festivalService.create(festivalCreateDto)).isInstanceOf(FestivalAlreadyExistsException.class);
  }

  @Test
  void 축제조회_성공(){
    //given
    given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));

    //when
    FestivalResDto result = festivalService.findById(1L);

    //then
    assertThat(result.getName()).isEqualTo(festival.getName());
  }

  @Test
  void 축제조회_축제없는_경우(){
    //given
    given(festivalRepository.findById(1L)).willReturn(Optional.empty());

    //when & then
    assertThatThrownBy(()-> festivalService.findById(1L)).isInstanceOf(FestivalNotFoundException.class);
  }

  @Test
  void 축제목록조회_성공(){
    //given
    given(festivalRepository.findAll()).willReturn(List.of(festival));

    //when
    List<FestivalListDto> result = festivalService.findAll();

    //then
    assertThat(result.size()).isEqualTo(1);
    assertThat(result.get(0).getFestivalName()).isEqualTo("축제");
    assertThat(result.get(0).getSubAddress()).isEqualTo("univ");
  }

  @Test
  void 축제목록조회_결과없음(){
    //given
    given(festivalRepository.findAll()).willReturn(Collections.emptyList());

    //when
    List<FestivalListDto> result = festivalService.findAll();

    //then
    assertThat(result).isEmpty();
  }

  @Test
  void 축제수정_성공(){
    //given
    FestivalCreateDto updateDto = new FestivalCreateDto("업데이트된 축제", "test-festival");
    given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));

    //when
    FestivalResDto result = festivalService.update(1L, updateDto);

    //then
    assertThat(result.getName()).isEqualTo("업데이트된 축제");
  }

  @Test
  void 축제삭제_성공(){
    //given
    given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));

    //when
    String result = festivalService.delete(1L);

    //then
    assertThat(result).isEqualTo("삭제완료");
  }

  @Test
  void 축제색등록_성공(){
    //given
    ColorDto colorDto = new ColorDto();
    given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));

    //when
    String result = festivalService.updateColor(1L, colorDto);

    //then
    assertThat(result).isEqualTo("색 등록 완료");
    assertThat(festival.getBackgroundColor()).isEqualTo(colorDto.getBackgroundColor());
    assertThat(festival.getSubColor()).isEqualTo(colorDto.getSubColor());
    assertThat(festival.getMainColor()).isEqualTo(colorDto.getMainColor());
    assertThat(festival.getFontColor()).isEqualTo(colorDto.getFontColor());
  }

  @Test
  void 로고등록_성공(){
    //given
    given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));
    ImageDto imageDto = new ImageDto();

    //when
    String result = festivalService.updateLogo(1L, imageDto);

    //then
    assertThat(result).isEqualTo("로고 등록 완료");
    assertThat(festival.getLogo()).isEqualTo(imageDto.getImage());
  }

  @Test
  void 메인메뉴등록_성공(){
    //given
    given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));
    MainMenuDto mainMenuDto = new MainMenuDto();

    //when
    String result = festivalService.updateMainMenu(1L, mainMenuDto);

    //then
    assertThat(result).isEqualTo("메인 메뉴 정보 등록");
    assertThat(festival.getMenuName1()).isEqualTo(mainMenuDto.getMenuName1());
    assertThat(festival.getMenuName2()).isEqualTo(mainMenuDto.getMenuName2());
    assertThat(festival.getMenuImage1()).isEqualTo(mainMenuDto.getMenuImage1());
    assertThat(festival.getMenuImage2()).isEqualTo(mainMenuDto.getMenuImage2());
    assertThat(festival.getMenuSummary1()).isEqualTo(mainMenuDto.getMenuSummary1());
    assertThat(festival.getMenuSummary2()).isEqualTo(mainMenuDto.getMenuSummary2());
    assertThat(festival.getMenuUrl2()).isEqualTo(mainMenuDto.getMenuUrl2());
    assertThat(festival.getMenuUrl1()).isEqualTo(mainMenuDto.getMenuUrl1());
  }

  @Test
  void 입장정보등록_성공(){
    //given
    given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));
    FestivalConcertMenuDto dto = new FestivalConcertMenuDto();

    //when
    String result = festivalService.updateEntryInfo(1L, dto);

    //then
    assertThat(result).isEqualTo("입장방법 등록");
    assertThat(festival.getEntrySummary()).isEqualTo(dto.getSummary());
    assertThat(festival.getEntryIcon()).isEqualTo(dto.getIcon());
  }

  @Test
  void 관람안내등록_성공(){
    //given
    given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));
    FestivalConcertMenuDto dto = new FestivalConcertMenuDto();

    //when
    String result = festivalService.updateViewInfo(1L, dto);

    //then
    assertThat(result).isEqualTo("관람안내 등록");
    assertThat(festival.getViewSummary()).isEqualTo(dto.getSummary());
    assertThat(festival.getViewIcon()).isEqualTo(dto.getIcon());
  }

  @Test
  void 입장방법조회_성공(){
    //given
    given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));

    //when
    FestivalConcertMenuDto concertMenuDto = festivalService.getEntryInfo(1L);

    //then
    assertThat(concertMenuDto.getIcon()).isEqualTo(festival.getEntryIcon());
    assertThat(concertMenuDto.getSummary()).isEqualTo(festival.getEntrySummary());
  }

  @Test
  void 관람방법조회_성공(){
    //given
    given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));

    //when
    FestivalConcertMenuDto concertMenuDto = festivalService.getViewInfo(1L);

    //then
    assertThat(concertMenuDto.getIcon()).isEqualTo(festival.getEntryIcon());
    assertThat(concertMenuDto.getSummary()).isEqualTo(festival.getEntrySummary());
  }

  @Test
  void subAddress조회_성공(){
    //given
    given(festivalRepository.findBySubAddress("univ")).willReturn(Optional.of(festival));

    //when
    FestivalListDto festivalListDto = festivalService.findBySubAddress("univ");

    //then
    assertThat(festivalListDto).isNotNull();
    assertThat(festivalListDto.getFestivalName()).isEqualTo("축제");
    assertThat(festivalListDto.getSubAddress()).isEqualTo("univ");
  }
  @Test
  void  subAddress조회_축제가없는_경우(){
    //given
    given(festivalRepository.findBySubAddress("univ")).willReturn(Optional.empty());

    //when & then
    assertThatThrownBy(()->festivalService.findBySubAddress("univ")).isInstanceOf(FestivalNotFoundException.class);
  }

  @Test
  void  메인메뉴조회_성공(){
    //given
    given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));

    //when
    MainMenuDto mainMenuDto = festivalService.getMainMenu(1L);

    //then
    assertThat(mainMenuDto).isNotNull();
    assertThat(mainMenuDto.getMenuName1()).isEqualTo(festival.getMenuName1());
    assertThat(mainMenuDto.getMenuName2()).isEqualTo(festival.getMenuName2());
    assertThat(mainMenuDto.getMenuImage1()).isEqualTo(festival.getMenuImage1());
    assertThat(mainMenuDto.getMenuImage2()).isEqualTo(festival.getMenuImage2());
    assertThat(mainMenuDto.getMenuSummary1()).isEqualTo(festival.getMenuSummary1());
    assertThat(mainMenuDto.getMenuSummary2()).isEqualTo(festival.getMenuSummary2());
    assertThat(mainMenuDto.getMenuUrl1()).isEqualTo(festival.getMenuUrl1());
    assertThat(mainMenuDto.getMenuUrl2()).isEqualTo(festival.getMenuUrl2());
  }

  @Test
  void 축제위치등록_성공(){
    //given
    given(festivalRepository.findById(1L)).willReturn(Optional.of(festival));
    FestivalLocationDto festivalLocationDto = new FestivalLocationDto();

    //when
    Festival result = festivalService.updateLocation(1L,festivalLocationDto);

    //then
    assertThat(result).isNotNull();
    assertThat(result.getLongitude()).isEqualTo(festival.getLongitude());
    assertThat(result.getLatitude()).isEqualTo(festival.getLatitude());
  }
}

//given

//when

//then
