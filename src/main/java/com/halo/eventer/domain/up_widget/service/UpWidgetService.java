package com.halo.eventer.domain.up_widget.service;


import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.up_widget.UpWidget;
import com.halo.eventer.domain.up_widget.dto.UpWidgetCreateDto;
import com.halo.eventer.domain.up_widget.dto.UpWidgetGetListDto;
import com.halo.eventer.domain.up_widget.repository.UpWidgetRepository;
import com.halo.eventer.global.common.response.SuccessCode;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpWidgetService {

    private final UpWidgetRepository upWidgetRepository;
    private final FestivalService festivalService;

    /**
     * 상단 팝업 생성
     * */
    public SuccessCode createUpWidget(Long festivalId,UpWidgetCreateDto upWidgetCreateDto) {
        upWidgetRepository.save(new UpWidget(upWidgetCreateDto,festivalService.getFestival(festivalId)));
        return SuccessCode.SAVE_SUCCESS;
    }

    /**
     * 상단 팝업 리스트 조회
     * */
    public List<UpWidget> getUpWidgetList(Long festivalId) {
        return upWidgetRepository.findAllByFestival(festivalService.getFestival(festivalId));
    }

    /**
     * 상단 팝업 리스트 단일 조회
     */
    public UpWidget getUpWidget(Long upWidgetId) {
        return upWidgetRepository.findById(upWidgetId).orElseThrow(()->new BaseException("상단 팝업이 존재하지 않습니다.", ErrorCode.ELEMENT_NOT_FOUND));
    }

    /**
     * 유저용 datetime으로 팝업 리스트 조회
     * */
    public List<UpWidget> getUpWidgetListByDateTime(Long id, LocalDateTime dateTime) {
        return upWidgetRepository.findAllByFestivalWithDateTime(festivalService.getFestival(id),dateTime);
    }
}
