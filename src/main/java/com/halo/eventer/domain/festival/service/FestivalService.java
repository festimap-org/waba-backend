package com.halo.eventer.domain.festival.service;

import com.halo.eventer.domain.down_widget.DownWidget;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.dto.*;
import com.halo.eventer.domain.festival.exception.FestivalAlreadyExistsException;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.map.MapCategory;
import com.halo.eventer.global.common.ImageDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FestivalService {

  private final FestivalRepository festivalRepository;

  public String create(FestivalCreateDto festivalCreateDto) {
    if (festivalRepository.findByName(festivalCreateDto.getName()).isPresent()
        || festivalRepository.findBySubAddress(festivalCreateDto.getSubAddress()).isPresent()) {
      throw new FestivalAlreadyExistsException();
    }

    Festival festival = new Festival(festivalCreateDto);
    festival.setMapCategory(List.of(new MapCategory("고정 부스")));
    for (int i = 0; i < 3; i++) {
      festival.getDownWidgets().add(new DownWidget(festival));
    }
    festivalRepository.save(festival);
    return "저장완료";
  }

  @Transactional(readOnly = true)
  public FestivalResDto findById(Long id) {
    Festival festival = loadFestivalOrThrow(id);
    return FestivalResDto.from(festival);
  }

  public List<FestivalListDto> findAll() {
    return festivalRepository.findAll().stream().map(FestivalListDto::new).collect(Collectors.toList());
  }

  @Transactional
  public FestivalResDto update(Long id, FestivalCreateDto festivalCreateDto) {
    Festival festival = loadFestivalOrThrow(id);
    festival.setFestival(festivalCreateDto);
    return FestivalResDto.from(festival);
  }

  @Transactional
  public String delete(Long id) {
    Festival festival = loadFestivalOrThrow(id);
    festivalRepository.delete(festival);
    return "삭제완료";
  }

  @Transactional
  public String updateColor(Long id, ColorDto colorDto) {
    Festival festival = loadFestivalOrThrow(id);
    festival.setColor(colorDto);
    return "색 등록 완료";
  }

  @Transactional
  public String updateLogo(Long id, ImageDto imageDto) {
    Festival festival = loadFestivalOrThrow(id);
    festival.setLogo(imageDto.getImage());
    return "로고 등록 완료";
  }

  @Transactional
  public String updateMainMenu(Long id, MainMenuDto mainMenuDto) {
    Festival festival = loadFestivalOrThrow(id);
    festival.setMainMenu(mainMenuDto);
    return "메인 메뉴 정보 등록";
  }

  @Transactional
  public String updateEntryInfo(Long id, FestivalConcertMenuDto festivalConcertMenuDto) {
    Festival festival =loadFestivalOrThrow(id);
    festival.setEntry(festivalConcertMenuDto);
    festivalRepository.save(festival);
    return "입장방법 등록";
  }

  @Transactional
  public String updateViewInfo(Long id, FestivalConcertMenuDto festivalConcertMenuDto) {
    Festival festival = loadFestivalOrThrow(id);
    festival.setView(festivalConcertMenuDto);
    festivalRepository.save(festival);
    return "관람안내 등록";
  }

  public FestivalConcertMenuDto getEntryInfo(Long id) {
    Festival festival = loadFestivalOrThrow(id);
    return new FestivalConcertMenuDto(festival.getEntrySummary(), festival.getEntryIcon());
  }

  public FestivalConcertMenuDto getViewInfo(Long id) {
    Festival festival = loadFestivalOrThrow(id);
    return new FestivalConcertMenuDto(festival.getViewSummary(), festival.getViewIcon());
  }

  // subAddress로 축제 조회
  public FestivalListDto findBySubAddress(String subAddress) {
    return new FestivalListDto(
        festivalRepository
            .findBySubAddress(subAddress)
            .orElseThrow(() -> new FestivalNotFoundException(subAddress)));
  }

  public MainMenuDto getMainMenu(Long id) {
    Festival festival = loadFestivalOrThrow(id);
    return new MainMenuDto(festival);
  }

  /** 축제 위치 등록 */
  @Transactional
  public Festival updateLocation(Long id, FestivalLocationDto festivalLocationDto) {
    Festival festival = loadFestivalOrThrow(id);
    festival.updateLocation(festivalLocationDto);
    return festival;
  }

  private Festival loadFestivalOrThrow(Long id) {
    return festivalRepository.findById(id).orElseThrow(() -> new FestivalNotFoundException(id));
  }
}
