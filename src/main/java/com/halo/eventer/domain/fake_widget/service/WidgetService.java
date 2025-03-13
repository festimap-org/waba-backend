package com.halo.eventer.domain.fake_widget.service;


import com.halo.eventer.domain.fake_widget.Widget;
import com.halo.eventer.domain.fake_widget.dto.WidgetDto;
import com.halo.eventer.domain.fake_widget.repository.WidgetRepository;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.exception.FestivalNotFoundException;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.fake_widget.dto.WidgetGetListDto;
import com.halo.eventer.domain.widget.exception.WidgetNotFoundException;
import com.halo.eventer.global.common.response.SuccessCode;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WidgetService {

    private final WidgetRepository widgetRepository;
    private final FestivalRepository festivalRepository;

    /** 위젯 생성 */
    @Transactional
    public SuccessCode createWidget(Long festivalId, WidgetDto widgetDto) {
        Festival festival = festivalRepository
                .findById(festivalId).orElseThrow(() -> new FestivalNotFoundException(festivalId));
        return SuccessCode.SAVE_SUCCESS;
    }

    /** 위젯 전체 조회 */
    public WidgetGetListDto getWidgets(Long festivalId) {
        Festival festival = festivalRepository
                .findById(festivalId).orElseThrow(() -> new FestivalNotFoundException(festivalId));
        List<Widget> festivalList = widgetRepository.findAllByFestival(festival);
        return new WidgetGetListDto(festivalList);
    }

    /** 단일 위젯 조회 */
    public Widget getWidget(Long id) {
        return widgetRepository.findById(id).orElseThrow(() -> new WidgetNotFoundException(id));
    }

    /** 위젯 업데이트 */
    @Transactional
    public String updateWidget(Long widgetId, WidgetDto widgetDto) {
        Widget widget = getWidget(widgetId);
        widget.setWidget(widgetDto);
        return "위젯 수정 완료";
    }

    /** 위젯 삭제 */
    public String deleteWidget(Long widgetId) {
        Widget widget = getWidget(widgetId);
        widgetRepository.delete(widget);
        return "위젯 삭제 완료";
    }
}
