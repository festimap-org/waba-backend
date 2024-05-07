package com.halo.eventer.concert_info.service;


import com.halo.eventer.concert.dto.ConcertResDto;
import com.halo.eventer.concert_info.ConcertInfo;
import com.halo.eventer.concert_info.dto.ConcertInfoGetDto;
import com.halo.eventer.concert_info.dto.ConcertInfoResDto;
import com.halo.eventer.concert_info.dto.ConcertInfoUpdateDto;
import com.halo.eventer.concert_info.repository.ConcertInfoRepository;
import com.halo.eventer.concert_info.ConcertInfoType;
import com.halo.eventer.exception.common.NoDataInDatabaseException;
import com.halo.eventer.festival.Festival;
import com.halo.eventer.festival.repository.FestivalRepository;
import com.halo.eventer.image.Image;
import com.halo.eventer.image.ImageRepository;
import com.halo.eventer.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcertInfoService {

    private final ConcertInfoRepository concertInfoRepository;
    private final FestivalRepository festivalRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public String createInfoName(Long festivalId,String name, ConcertInfoType type) throws NoDataInDatabaseException {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(()->new NoDataInDatabaseException("축제 정보가 존재하지 않습니다."));
        ConcertInfo concertInfo = new ConcertInfo(name,type,festival);
        concertInfoRepository.save(concertInfo);
        return "저장완료";
    }

    public List<ConcertInfoResDto> getAllName(Long festivalId) {
        return concertInfoRepository.findAllByFestivalId(festivalId).stream().map(ConcertInfoResDto::new).collect(Collectors.toList());
    }

    @Transactional
    public String updateConcertInfo(Long concertInfoId, ConcertInfoUpdateDto concertUpdateDto) throws NoDataInDatabaseException {
        ConcertInfo concertInfo = concertInfoRepository.findById(concertInfoId).orElseThrow(() -> new NoDataInDatabaseException("해당 카테고리가 존재하지 않습니다."));

        concertUpdateDto.getDeletedImages().forEach(imageRepository::deleteById);
        concertInfo.setImages(concertUpdateDto.getImages().stream().map(Image::new).collect(Collectors.toList()));


        return "상세 이미지 등록";
    }

    public ConcertInfoGetDto getConcertInfo(Long concertId) throws NoDataInDatabaseException {
        ConcertInfo concertInfo= concertInfoRepository.findById(concertId).orElseThrow(()->new NoDataInDatabaseException("공연 부가 정보가 존재하지 않습니다."));
        return new ConcertInfoGetDto(concertInfo);
    }
}
