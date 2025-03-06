package com.halo.eventer.domain.festival.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.dto.*;
import com.halo.eventer.domain.festival.exception.FestivalAlreadyExistsException;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
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

  @Transactional
  public void create(FestivalCreateDto festivalCreateDto) {
    validateUniqueFestival(festivalCreateDto);
    Festival festival = Festival.from(festivalCreateDto);
    festival.applyDefaultMapCategory();
    festival.applyThreeDownWidgets();
    festivalRepository.save(festival);
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
    festival.updateFestival(festivalCreateDto);
    return FestivalResDto.from(festival);
  }

  @Transactional
  public void delete(Long id) {
    Festival festival = loadFestivalOrThrow(id);
    festivalRepository.delete(festival);
  }

  @Transactional
  public void updateColor(Long id, ColorDto colorDto) {
    Festival festival = loadFestivalOrThrow(id);
    festival.updateColor(colorDto);
  }

  @Transactional
  public void updateLogo(Long id, ImageDto imageDto) {
    Festival festival = loadFestivalOrThrow(id);
    festival.updateLogo(imageDto);
  }

  @Transactional
  public void updateMainMenu(Long id, MainMenuDto mainMenuDto) {
    Festival festival = loadFestivalOrThrow(id);
    festival.updateMainMenu(mainMenuDto);
  }

  @Transactional
  public void updateEntryInfo(Long id, FestivalConcertMenuDto festivalConcertMenuDto) {
    Festival festival = loadFestivalOrThrow(id);
    festival.updateEntry(festivalConcertMenuDto);
  }

  @Transactional
  public void updateViewInfo(Long id, FestivalConcertMenuDto festivalConcertMenuDto) {
    Festival festival = loadFestivalOrThrow(id);
    festival.updateView(festivalConcertMenuDto);
  }

  public FestivalConcertMenuDto getEntryInfo(Long id) {
    Festival festival = loadFestivalOrThrow(id);
    return FestivalConcertMenuDto.from(festival);
  }

  public FestivalConcertMenuDto getViewInfo(Long id) {
    Festival festival = loadFestivalOrThrow(id);
    return FestivalConcertMenuDto.from(festival);
  }

  public FestivalListDto findBySubAddress(String subAddress) {
    return new FestivalListDto(festivalRepository.findBySubAddress(subAddress)
            .orElseThrow(() -> new FestivalNotFoundException(subAddress)));
  }

  public MainMenuDto getMainMenu(Long id) {
    Festival festival = loadFestivalOrThrow(id);
    return MainMenuDto.from(festival);
  }

  @Transactional
  public Festival updateLocation(Long id, FestivalLocationDto festivalLocationDto) {
    Festival festival = loadFestivalOrThrow(id);
    festival.updateLocation(festivalLocationDto);
    return festival;
  }

  private Festival loadFestivalOrThrow(Long id) {
    return festivalRepository.findById(id).orElseThrow(() -> new FestivalNotFoundException(id));
  }

  private void validateUniqueFestival(FestivalCreateDto festivalCreateDto) {
    if (festivalRepository.findByName(festivalCreateDto.getName()).isPresent() ||
            festivalRepository.findBySubAddress(festivalCreateDto.getSubAddress()).isPresent()) {
      throw new FestivalAlreadyExistsException();
    }
  }
}
