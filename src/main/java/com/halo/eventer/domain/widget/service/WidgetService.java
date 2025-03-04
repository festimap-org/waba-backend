package com.halo.eventer.domain.widget.service;


import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.widget.Widget;
import com.halo.eventer.domain.widget.dto.WidgetDto;
import com.halo.eventer.domain.widget.dto.WidgetGetListDto;
import com.halo.eventer.domain.widget.repository.WidgetRepository;
import com.halo.eventer.global.common.response.SuccessCode;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WidgetService {

    private final WidgetRepository widgetRepository;
    private final FestivalService festivalService;

    /** 위젯 생성 */
    @Transactional
    public SuccessCode createWidget(Long festivalId, WidgetDto widgetDto) {
        widgetRepository.save(new Widget(widgetDto,festivalService.getFestival(festivalId)));
        return SuccessCode.SAVE_SUCCESS;
    }

    /** 위젯 전체 조회 */
    public WidgetGetListDto getWidgets(Long festivalId) {
        Festival festival = festivalService.getFestival(festivalId);
        List<Widget> festivalList = widgetRepository.findAllByFestival(festival);
        return new WidgetGetListDto(festivalList);
    }

    /** 단일 위젯 조회 */
    public Widget getWidget(Long widgetId) {
        return widgetRepository.findById(widgetId).orElseThrow(()->new BaseException("축제 정보가 존재하지 않습니다.", ErrorCode.ELEMENT_NOT_FOUND));
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
