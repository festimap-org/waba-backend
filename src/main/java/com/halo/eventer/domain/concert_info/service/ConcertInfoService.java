package com.halo.eventer.domain.concert_info.service;


import com.halo.eventer.domain.concert_info.ConcertInfo;
import com.halo.eventer.domain.concert_info.dto.ConcertInfoGetDto;
import com.halo.eventer.domain.concert_info.dto.ConcertInfoGetListDto;
import com.halo.eventer.domain.concert_info.dto.ConcertInfoUpdateDto;
import com.halo.eventer.domain.concert_info.repository.ConcertInfoRepository;
import com.halo.eventer.domain.concert_info.ConcertInfoType;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertInfoService {

    private final ConcertInfoRepository concertInfoRepository;
    private final ImageRepository imageRepository;

    private final FestivalService festivalService;

    /** 공연 정보 생성 */
    @Transactional
    public String createInfoName(Long festivalId,String name, ConcertInfoType type) {
        Festival festival = festivalService.getFestival(festivalId);
        ConcertInfo concertInfo = new ConcertInfo(name,type,festival);
        concertInfoRepository.save(concertInfo);
        return "저장완료";
    }

    /** 타입 별 공연 정보 전체 조회 */
    public ConcertInfoGetListDto getConcertInfoListByType(Long festivalId, ConcertInfoType type) {
        List<ConcertInfo> concertInfos = concertInfoRepository.findAllByFestivalIdAndType(festivalId, type);
        List<ConcertInfoGetDto> concertInfoListDto = ConcertInfoGetDto.fromConcertInfoList(concertInfos);
        return new ConcertInfoGetListDto(concertInfoListDto);
    }

    /** 단일 공연 정보 조회 */
    public ConcertInfo getConcertInfo(Long concertId) {
        ConcertInfo concertInfo = concertInfoRepository.findById(concertId).orElseThrow(()->new BaseException("공연 부가 정보가 존재하지 않습니다.", ErrorCode.ELEMENT_NOT_FOUND));
        return concertInfo;
    }

    /** 상세 이미지 등록 */
    @Transactional
    public String updateConcertInfo(Long concertInfoId, ConcertInfoUpdateDto concertUpdateDto) {
        ConcertInfo concertInfo = getConcertInfo(concertInfoId);
        concertUpdateDto.getDeletedImages().forEach(imageRepository::deleteById);
        concertInfo.setInfo(concertUpdateDto);
        return "상세 이미지 등록";
    }

    /** 공연 정보 삭제 */
    public String deleteConcertInfo(Long id) {
        concertInfoRepository.deleteById(id);
        return "삭제완료";
    }
}
