package com.halo.eventer.domain.home.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.home.dto.HomeDto;
import com.halo.eventer.domain.missing_person.MissingPerson;
import com.halo.eventer.domain.missing_person.service.MissingPersonService;
import com.halo.eventer.domain.widget.entity.UpWidget;
import com.halo.eventer.domain.notice.dto.RegisteredBannerGetDto;
import com.halo.eventer.domain.notice.repository.NoticeRepository;
import com.halo.eventer.domain.widget.service.UpWidgetService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

  private final NoticeRepository noticeRepository;
  private final FestivalRepository festivalRepository;
  private final UpWidgetService upWidgetService;
  private final MissingPersonService missingPersonService;

  // todo: List<RegisteredBannerGetDto> -> RegisteredBannerGetListDto
  public HomeDto getMainPage(Long festivalId, LocalDateTime dateTime) {
    List<RegisteredBannerGetDto> banner =
        noticeRepository.findAllByPickedAndFestival_Id(true, festivalId).stream()
            .map(RegisteredBannerGetDto::new)
            .collect(Collectors.toList());
    Festival festival =
        festivalRepository
            .findById(festivalId)
            .orElseThrow(() -> new FestivalNotFoundException(festivalId));
    List<UpWidget> upWidgets = List.of();//upWidgetService.getUpWidgetListByDateTime(festivalId, dateTime);
    List<MissingPerson> missingPersonList = missingPersonService.getPopupList(festivalId);
    return new HomeDto(banner, festival, upWidgets, missingPersonList);
  }
}
