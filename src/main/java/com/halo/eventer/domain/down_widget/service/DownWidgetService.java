package com.halo.eventer.domain.down_widget.service;


import com.halo.eventer.domain.down_widget.DownWidget;
import com.halo.eventer.domain.down_widget.dto.DownWidgetCreateDto;
import com.halo.eventer.domain.down_widget.repository.DownWidgetRepository;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.global.common.response.SuccessCode;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DownWidgetService {

    private final DownWidgetRepository downWidgetRepository;

    /**
     * 하단 위젯 업데이트
     * */
    @Transactional
    public SuccessCode update(DownWidgetCreateDto downWidgetCreateDto) {
        if(downWidgetCreateDto.getDownWidgetDtos().size() > 3) {
            throw new BaseException(ErrorCode.PERMIT_THREE_ELEMENT);
        }

        List<DownWidget> downWidgets = downWidgetRepository.findAllByFestivalId(downWidgetCreateDto.getFestivalId());

        for(int i = 0; i <downWidgetCreateDto.getDownWidgetDtos().size();i++){
            downWidgets.get(i).updateDownWidget(downWidgetCreateDto.getDownWidgetDtos().get(i));
        }
        return SuccessCode.SAVE_SUCCESS;
    }

    /**
     * 하단 위젯 3개 모두 조회
     */
    public List<DownWidget> getAllDownWidget(Long festivalId) {
        return downWidgetRepository.findAllByFestivalId(festivalId);
    }



}
