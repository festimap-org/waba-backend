package com.halo.eventer.domain.widget.service;


import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.widget.dto.WidgetDto;
import com.halo.eventer.domain.widget.repository.WidgetRepository;
import com.halo.eventer.global.common.response.SuccessCode;
import com.halo.eventer.global.common.response.SuccessResponse;
import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.widget.Widget;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WidgetService {

    private final WidgetRepository widgetRepository;
    private final FestivalRepository festivalRepository;
    private final FestivalService festivalService;

    @Transactional
    public SuccessCode createWidget(Long festivalId, WidgetDto widgetDto) throws NoDataInDatabaseException {
        widgetRepository.save(new Widget(widgetDto,festivalService.getFestival(festivalId)));
        return SuccessCode.SAVE_SUCCESS;
    }


    public List<Widget> getWidgets(Long festivalId) throws  NoDataInDatabaseException {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(()->new NoDataInDatabaseException("축제 정보가 존재하지 않습니다."));
        return widgetRepository.findAllByFestival(festival);
    }



    public String deleteWidget(Long widgetId) throws NoDataInDatabaseException {
        Widget widget = widgetRepository.findById(widgetId).orElseThrow(()->new NoDataInDatabaseException("축제 정보가 존재하지 않습니다."));
        widgetRepository.delete(widget);
        return "위젯 삭제 완료";
    }

    public Widget getWidget(Long widgetId) throws NoDataInDatabaseException {
        return widgetRepository.findById(widgetId).orElseThrow(()->new NoDataInDatabaseException("축제 정보가 존재하지 않습니다."));
    }

    @Transactional
    public String updateWidget(Long widgetId, WidgetDto widgetDto) {
        Widget widget = widgetRepository.findById(widgetId).orElseThrow(()->new NoDataInDatabaseException("축제 정보가 존재하지 않습니다."));
        widget.setWidget(widgetDto);
        return "위젯 수정 완료";
    }
}
