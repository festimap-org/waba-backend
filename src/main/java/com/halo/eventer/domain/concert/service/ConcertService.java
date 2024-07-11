package com.halo.eventer.domain.concert.service;

import com.halo.eventer.domain.concert.Concert;
import com.halo.eventer.domain.concert.dto.*;
import com.halo.eventer.domain.concert.repository.ConcertRepository;
import com.halo.eventer.domain.duration.repository.DurationRepository;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.global.error.exception.BaseException;
import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.image.Image;
import com.halo.eventer.domain.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final DurationRepository durationRepository;
    private final ImageRepository imageRepository;

    private final FestivalService festivalService;

    /** 공연 등록 */
    @Transactional
    public String registerConcert(ConcertRegisterDto registerDto, Long id) throws BaseException {
        Festival festival = festivalService.getFestival(id);
        Concert concert = new Concert(registerDto.getThumbnail(), festival,durationRepository.findById(registerDto.getDurationId()).orElseThrow(()->new NoDataInDatabaseException("설정한 기간이 존재하지 않습니다.")));
        concert.setImages(registerDto.getImages().stream().map(o-> new Image(o)).collect(Collectors.toList()));
        concertRepository.save(concert);
        return "저장완료";
    }

    /** 공연 전체 조회 */
    public ConcertGetListDto getConcertList(Long festivalId) throws BaseException {
        Festival festival = festivalService.getFestival(festivalId);

        List<Concert> concerts = concertRepository.findAllByFestival(festival);
        List<ConcertGetDto> concertListDto = ConcertGetDto.fromConcertList(concerts);
        return new ConcertGetListDto(concertListDto);
    }

    /** 단일 공연 조회 */
    public Concert getConcert(Long id) throws BaseException {
        Concert concert = concertRepository.findById(id).orElseThrow(() -> new NoDataInDatabaseException("공연 정보가 존재하지 않습니다."));
        return concert;
    }

    /** 공연 정보 업데이트 */
    @Transactional
    public ConcertUpdateResponseDto updateConcert(Long concertId, ConcertUpdateDto concertUpdateDto) throws BaseException {
        Concert concert = getConcert(concertId);

        concertUpdateDto.getDeletedImages().forEach(imageRepository::deleteById);
        concert.setImages(concertUpdateDto.getImages().stream().map(Image::new).collect(Collectors.toList()));
        concert.setAll(concertUpdateDto.getThumbnail(), durationRepository.findById(concertUpdateDto.getDurationId()).orElseThrow(() -> new NoDataInDatabaseException("기간이 존재하지 않습니다")));

        ConcertUpdateResponseDto response = new ConcertUpdateResponseDto(concert);
        return response;
    }

    /** 공연 삭제 */
    @Transactional
    public String deleteConcert(Long concertId) throws BaseException {
       Concert concert = getConcert(concertId);
        concertRepository.delete(concert);
        return "삭제완료";
    }


}
