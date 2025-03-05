package com.halo.eventer.domain.up_widget.service;

import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.up_widget.UpWidget;
import com.halo.eventer.domain.up_widget.dto.UpWidgetCreateDto;
import com.halo.eventer.domain.up_widget.exception.UpWidgetNotFoundException;
import com.halo.eventer.domain.up_widget.repository.UpWidgetRepository;
import com.halo.eventer.global.common.response.SuccessCode;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpWidgetService {

  private final UpWidgetRepository upWidgetRepository;
  private final FestivalService festivalService;

  /** 상단 팝업 생성 */
  public SuccessCode createUpWidget(Long festivalId, UpWidgetCreateDto upWidgetCreateDto) {
    upWidgetRepository.save(
        new UpWidget(upWidgetCreateDto, festivalService.getFestival(festivalId)));
    return SuccessCode.SAVE_SUCCESS;
  }

  /** 상단 팝업 리스트 조회 */
  public List<UpWidget> getUpWidgetList(Long festivalId) {
    return upWidgetRepository.findAllByFestival(festivalService.getFestival(festivalId));
  }

  /** 상단 팝업 리스트 단일 조회 */
  public UpWidget getUpWidget(Long id) {
    return upWidgetRepository
        .findById(id)
        .orElseThrow(() -> new UpWidgetNotFoundException(id));
  }

  /** 유저용 datetime으로 팝업 리스트 조회 */
  public List<UpWidget> getUpWidgetListByDateTime(Long id, LocalDateTime dateTime) {
    return upWidgetRepository.findAllByFestivalWithDateTime(
        festivalService.getFestival(id), dateTime);
  }

  /** 상단 위젯 수정 */
  @Transactional
  public UpWidget updateUpWidget(Long id, UpWidgetCreateDto widgetCreateDto) {
    UpWidget upWidget =
        upWidgetRepository
            .findById(id)
            .orElseThrow(() -> new UpWidgetNotFoundException(id));
    upWidget.update(widgetCreateDto);
    return upWidget;
  }

  /** 상단 위젯 삭제 */
  @Transactional
  public SuccessCode deleteUpWidget(Long upWidgetId) {
    upWidgetRepository.deleteById(upWidgetId);
    return SuccessCode.SAVE_SUCCESS;
  }
}
