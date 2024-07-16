package com.halo.eventer.domain.home;


import com.halo.eventer.domain.up_widget.UpWidget;
import com.halo.eventer.domain.up_widget.service.UpWidgetService;
import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.notice.dto.RegisteredBannerGetDto;
import com.halo.eventer.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final NoticeRepository noticeRepository;
    private final FestivalRepository festivalRepository;
    private final UpWidgetService upWidgetService;

    // todo: List<RegisteredBannerGetDto> -> RegisteredBannerGetListDto
    public HomeDto getMainPage(Long festivalId, LocalDateTime dateTime) throws NoDataInDatabaseException {
        List<RegisteredBannerGetDto> banner = noticeRepository.findAllByPickedAndFestival_Id(true,festivalId).stream().map(RegisteredBannerGetDto::new).collect(Collectors.toList());
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다."));
        List<UpWidget> upWidgets = upWidgetService.getUpWidgetListByDateTime(festivalId,dateTime);
        return new HomeDto(banner,festival,upWidgets);
    }



}
