package com.halo.eventer.domain.home.service;


import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.home.dto.HomeDto;
import com.halo.eventer.domain.notice.Notice;
import com.halo.eventer.domain.notice.dto.RegisteredBannerGetListDto;
import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.notice.dto.RegisteredBannerGetDto;
import com.halo.eventer.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final NoticeRepository noticeRepository;
    private final FestivalService festivalService;

    public HomeDto getMainPage(Long festivalId) {
        List<Notice> notices = noticeRepository.findAllByPickedAndFestival_Id(true, festivalId);
        List<RegisteredBannerGetDto> bannerGetDtoList = RegisteredBannerGetDto.fromRegisteredBannerList(notices);
        Festival festival = festivalService.getFestival(festivalId);
        return new HomeDto(bannerGetDtoList,festival);
    }



}
