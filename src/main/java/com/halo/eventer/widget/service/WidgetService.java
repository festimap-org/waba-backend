package com.halo.eventer.widget.service;


import com.halo.eventer.exception.common.NoDataInDatabaseException;
import com.halo.eventer.festival.Festival;
import com.halo.eventer.festival.repository.FestivalRepository;
import com.halo.eventer.widget.Widget;
import com.halo.eventer.widget.dto.WidgetDto;
import com.halo.eventer.widget.repository.WidgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WidgetService {

    private final WidgetRepository widgetRepository;
    private final FestivalRepository festivalRepository;

    @Transactional
    public String createWidget(Long festivalId, WidgetDto widgetDto) throws NoDataInDatabaseException {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(()->new NoDataInDatabaseException("축제 정보가 존재하지 않습니다."));
        widgetRepository.save(new Widget(widgetDto,festival));
        return "위젯 저장 완료";
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
