package com.halo.eventer.domain.festival.service;


import com.halo.eventer.domain.down_widget.DownWidget;
import com.halo.eventer.domain.festival.dto.*;
import com.halo.eventer.domain.member.Member;
import com.halo.eventer.domain.member.repository.MemberRepository;
import com.halo.eventer.global.common.ImageDto;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import com.halo.eventer.global.exception.common.DuplicatedElementException;
import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.map.MapCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class FestivalService {

    private final FestivalRepository festivalRepository;
    private final MemberRepository memberRepository;


    public String createFestival(FestivalCreateDto festivalCreateDto) {
        if(festivalRepository.findByName(festivalCreateDto.getName()).isPresent() || festivalRepository.findBySubAddress(festivalCreateDto.getSubAddress()).isPresent()){
            throw new BaseException("중복생성", ErrorCode.ELEMENT_DUPLICATED);
        }

        Festival festival = new Festival(festivalCreateDto);
        festival.setMapCategory(List.of(new MapCategory("고정 부스")));
        for(int i =0;i<3;i++){
            festival.getDownWidgets().add(new DownWidget(festival));
        }
        festivalRepository.save(festival);
        return "저장완료";
    }

    public Festival getFestival(Long id) {
        Festival festival = festivalRepository.findById(id).orElseThrow(()->new BaseException("축제가 존재하지 않습니다", ErrorCode.ELEMENT_NOT_FOUND));
        return festival;
    }

    public List<FestivalListDto> getFestivals() {
        return festivalRepository.findAll().stream().map(FestivalListDto::new).collect(Collectors.toList());
    }

    @Transactional
    public FestivalResDto updateFestival(Long id, FestivalCreateDto festivalCreateDto) {
        Festival festival = getFestival(id);
        festival.setFestival(festivalCreateDto);
        return new FestivalResDto(festival);
    }

    @Transactional
    public String deleteFestival(Long id) {
        Festival festival = getFestival(id);
        festivalRepository.delete(festival);
        return "삭제완료";
    }


    @Transactional
    public String addColor(Long id, ColorReqDto colorReqDto) {
        Festival festival = getFestival(id);
        festival.setColor(colorReqDto);
        return "색 등록 완료";
    }

    @Transactional
    public String addLogo(Long id, ImageDto imageDto) {
        Festival festival = getFestival(id);
        festival.setLogo(imageDto.getImage());
        return "로고 등록 완료";
    }

    @Transactional
    public String addMainMenu(Long id, MainMenuDto mainMenuDto) {
        Festival festival = getFestival(id);
        festival.setMainMenu(mainMenuDto);
        return "메인 메뉴 정보 등록";
    }

    @Transactional
    public String addEntryInfo(Long id, FestivalConcertMenuDto festivalConcertMenuDto) {
        Festival festival = getFestival(id);
        festival.setEntry(festivalConcertMenuDto);
        festivalRepository.save(festival);
        return "입장방법 등록";

    }

    @Transactional
    public String addViewInfo(Long id, FestivalConcertMenuDto festivalConcertMenuDto) {
        Festival festival = getFestival(id);
        festival.setView(festivalConcertMenuDto);
        festivalRepository.save(festival);
        return "관람안내 등록";
    }

    public FestivalConcertMenuDto getEntryInfo(Long id) {
        Festival festival = getFestival(id);
        return new FestivalConcertMenuDto(festival.getEntrySummary(),festival.getEntryIcon());
    }

    public FestivalConcertMenuDto getViewInfo(Long id) {
        Festival festival = getFestival(id);
        return new FestivalConcertMenuDto(festival.getViewSummary(),festival.getViewIcon());
    }
    //subAddress로 축제 조회
    public FestivalListDto getFestivalSubAddress(String name) {
        return new FestivalListDto(festivalRepository.findBySubAddress(name).orElseThrow(()->new BaseException("축제가 존재하지 않습니다.", ErrorCode.ELEMENT_NOT_FOUND)));
    }

    public MainMenuDto getMainMenu(Long id) {
        Festival festival = getFestival(id);
        return new MainMenuDto(festival);
    }

    /** 축제 위치 등록 */
    @Transactional
    public Festival updateFestivalLocation(Long festivalId, FestivalLocationDto festivalLocationDto){
        Festival festival = getFestival(festivalId);
        festival.updateLocation(festivalLocationDto);
        return festival;
    }

    public List<FestivalListDto> getFestivalForUser(String loginId){
        Member member = memberRepository.findByLoginId(loginId)
            .orElseThrow(() -> new BaseException("해당 유저가 존재하지 않습니다.",ErrorCode.ELEMENT_NOT_FOUND));

        List<Long> ids = new ArrayList<>();
        member.getMemberFestivals().forEach(o->ids.add(o.getFestival().getId()));

        return festivalRepository.findAllById(ids).stream().map(FestivalListDto::new).collect(Collectors.toList());
    }
}
