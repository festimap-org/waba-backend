package com.halo.eventer.domain.stamp.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.stamp.Stamp;
import com.halo.eventer.domain.stamp.dto.*;
import com.halo.eventer.domain.stamp.repository.StampRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StampService {
    private final StampRepository stampRepository;
    private final FestivalService festivalService;

    public Stamp getStamp(Long stampId) {
        return stampRepository.findById(stampId).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
    }

    @Transactional
    public StampGetListDto registerStamp(Long festivalId) {
        Festival festival = festivalService.getFestival(festivalId);
        stampRepository.save(new Stamp(festival));

        List<Stamp> stamps = stampRepository.findByFestival(festivalService.getFestival(festivalId));
        List<StampGetDto> stampGetDtos = StampGetDto.fromStampList(stamps);
        return new StampGetListDto(stampGetDtos);
    }

    public StampGetListDto getStampByFestivalId(Long festivalId) {
        List<Stamp> stamps = stampRepository.findByFestival(festivalService.getFestival(festivalId));
        List<StampGetDto> stampGetDtos = StampGetDto.fromStampList(stamps);
        return new StampGetListDto(stampGetDtos);
    }

    @Transactional
    public String updateStampOn(Long stampId) {
        Stamp stamp = stampRepository.findById(stampId).orElseThrow(() -> new BaseException(ErrorCode.ELEMENT_NOT_FOUND));
        if (stamp.isStampOn()) stamp.setStampOn(false);
        else stamp.setStampOn(true);

        stampRepository.save(stamp);

        return "스탬프 상태 변경 성공";
    }

    @Transactional
    public String deleteStamp(Long stampId) {
        stampRepository.deleteById(stampId);
        return "삭제 완료";
    }

}
