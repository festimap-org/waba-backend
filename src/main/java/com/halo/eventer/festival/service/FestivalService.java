package com.halo.eventer.festival.service;


import com.halo.eventer.common.common.ImageDto;
import com.halo.eventer.festival.dto.FestivalConcertMenuDto;
import com.halo.eventer.exception.common.DuplicatedElementException;
import com.halo.eventer.exception.common.NoDataInDatabaseException;
import com.halo.eventer.festival.dto.*;
import com.halo.eventer.festival.Festival;
import com.halo.eventer.festival.repository.FestivalRepository;
import com.halo.eventer.map.MapCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class FestivalService {

    private final FestivalRepository festivalRepository;


    public String createFestival(FestivalCreateDto festivalCreateDto)throws DuplicatedElementException {
        if(festivalRepository.findByName(festivalCreateDto.getName()).isPresent() || festivalRepository.findBySubAddress(festivalCreateDto.getSubAddress()).isPresent()){
            throw new DuplicatedElementException("중복생성");
        }

        Festival festival = new Festival(festivalCreateDto);
        festival.setMapCategory(List.of(new MapCategory("고정 부스")));
        festivalRepository.save(festival);
        return "저장완료";
    }

    public FestivalResDto getFestival(Long id)throws NoDataInDatabaseException{
        Festival festival = festivalRepository.findById(id).orElseThrow(()->new NoDataInDatabaseException("존재하지 않습니다"));
        return new FestivalResDto(festival, new ColorReqDto(festival));
    }

    public List<FestivalListDto> getFestivals() {
        return festivalRepository.findAll().stream().map(FestivalListDto::new).collect(Collectors.toList());
    }

    @Transactional
    public FestivalResDto updateFestival(Long id, FestivalCreateDto festivalCreateDto) throws NoDataInDatabaseException{
        Festival festival = festivalRepository.findById(id).orElseThrow(()->new NoDataInDatabaseException("존재하지 않습니다"));
        festival.setFestival(festivalCreateDto);
        return new FestivalResDto(festival, new ColorReqDto(festival));
    }

    @Transactional
    public String deleteFestival(Long id) throws NoDataInDatabaseException{
        Festival festival = festivalRepository.findById(id).orElseThrow(()->new NoDataInDatabaseException("존재하지 않습니다."));
        festivalRepository.delete(festival);
        return "삭제완료";
    }


    @Transactional
    public String addColor(Long id, ColorReqDto colorReqDto)throws NoDataInDatabaseException {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(()->new NoDataInDatabaseException("축제 정보가 존재하지 않습니다."));

        festival.setColor(colorReqDto);

        return "색 등록 완료";
    }

    @Transactional
    public String addLogo(Long id, ImageDto imageDto) throws NoDataInDatabaseException {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(()->new NoDataInDatabaseException("축제 정보가 존재하지 않습니다."));

        festival.setLogo(imageDto.getImage());
        return "로고 등록 완료";
    }

    @Transactional
    public String addMainMenu(Long id,MainMenuDto mainMenuDto) throws NoDataInDatabaseException {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(()->new NoDataInDatabaseException("축제 정보가 존재하지 않습니다."));

        festival.setMainMenu(mainMenuDto);
        return "메인 메뉴 정보 등록";
    }

    @Transactional
    public String addEntryInfo(Long concertId, FestivalConcertMenuDto festivalConcertMenuDto) throws NoDataInDatabaseException {
        Festival festival = festivalRepository.findById(concertId).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다."));
        festival.setEntry(festivalConcertMenuDto);
        festivalRepository.save(festival);
        return "입장방법 등록";

    }

    @Transactional
    public String addViewInfo(Long festivalId, FestivalConcertMenuDto festivalConcertMenuDto) throws NoDataInDatabaseException {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다."));
        festival.setView(festivalConcertMenuDto);
        festivalRepository.save(festival);
        return "관람안내 등록";

    }

    public FestivalConcertMenuDto getEntryInfo(Long festivalId) throws NoDataInDatabaseException {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다."));
        return new FestivalConcertMenuDto(festival.getEntrySummary(),festival.getEntryIcon());
    }

    public FestivalConcertMenuDto getViewInfo(Long festivalId) throws NoDataInDatabaseException {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다."));
        return new FestivalConcertMenuDto(festival.getViewSummary(),festival.getViewIcon());
    }

    public FestivalListDto getFestivalSubAddress(String name) throws NoDataInDatabaseException {
        return new FestivalListDto(festivalRepository.findBySubAddress(name).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다.")));
    }

    public MainMenuDto getMainMenu(Long id) throws NoDataInDatabaseException {
        return new MainMenuDto(festivalRepository.findById(id).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다.")));
    }

    @Transactional
    public String createMiddleBanner(Long festivalId, MiddleBannerDto middleBannerDto) {
        festivalRepository.findById(festivalId).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다."))
                .setMiddleBanner(middleBannerDto);
        return "수정 완료";
    }

    public MiddleBannerDto getMiddleBanner(Long id) {
        return new MiddleBannerDto(festivalRepository.findById(id).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다.")));
    }
}
