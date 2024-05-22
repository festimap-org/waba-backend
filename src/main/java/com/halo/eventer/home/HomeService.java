package com.halo.eventer.home;


import com.halo.eventer.exception.common.NoDataInDatabaseException;
import com.halo.eventer.festival.Festival;
import com.halo.eventer.festival.repository.FestivalRepository;
import com.halo.eventer.notice.dto.BannerResDto;
import com.halo.eventer.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final NoticeRepository noticeRepository;
    private final FestivalRepository festivalRepository;

    public HomeDto getMainPage(Long festivalId) throws NoDataInDatabaseException {
        List<BannerResDto> banner = noticeRepository.findAllByPickedAndFestival_Id(true,festivalId).stream().map(BannerResDto::new).collect(Collectors.toList());
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다."));
        return new HomeDto(banner,festival);
    }



}
