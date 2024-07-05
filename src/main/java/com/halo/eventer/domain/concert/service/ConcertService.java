package com.halo.eventer.domain.concert.service;

import com.halo.eventer.domain.concert.Concert;
import com.halo.eventer.domain.concert.repository.ConcertRepository;
import com.halo.eventer.domain.concert.dto.ConcertCreateDto;
import com.halo.eventer.domain.concert.dto.ConcertResDto;
import com.halo.eventer.domain.concert.dto.ConcertUpdateDto;
import com.halo.eventer.domain.concert.dto.GetAllConcertDto;
import com.halo.eventer.domain.duration.repository.DurationRepository;
import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.image.Image;
import com.halo.eventer.domain.festival.repository.FestivalRepository;
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
    private final FestivalRepository festivalRepository;
    private final DurationRepository durationRepository;
    private final ImageRepository imageRepository;


    @Transactional
    public String registerConcert(ConcertCreateDto createDto, Long id) throws NoDataInDatabaseException {
        Festival festival =festivalRepository.findById(id).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다."));
        Concert concert = new Concert(createDto.getThumbnail(), festival,durationRepository.findById(createDto.getDurationId()).orElseThrow(()->new NoDataInDatabaseException("설정한 기간이 존재하지 않습니다.")));
        concert.setImages(createDto.getImages().stream().map(o-> new Image(o)).collect(Collectors.toList()));
        concertRepository.save(concert);
        return "저장완료";
    }

    public List<GetAllConcertDto> getConcerts(Long festivalId) throws NoDataInDatabaseException {
        return concertRepository.findAllByFestival(festivalRepository.findById(festivalId).orElseThrow(() -> new NoDataInDatabaseException("축제가 존재하지 않습니다.")))
                .stream().map(o -> new GetAllConcertDto(o)).collect(Collectors.toList());
    }

    public ConcertResDto getConcert(Long id) throws NoDataInDatabaseException {
        Concert concert = concertRepository.findById(id).orElseThrow(() -> new NoDataInDatabaseException("공연 정보가 존재하지 않습니다."));
        return new ConcertResDto(concert);
    }

    @Transactional
    public ConcertResDto updateConcert(Long concertId, ConcertUpdateDto concertUpdateDto) throws NoDataInDatabaseException {
        Concert concert = concertRepository.findById(concertId).orElseThrow(() -> new NoDataInDatabaseException("공연 정보가 존재하지 않습니다"));

        concertUpdateDto.getDeletedImages().forEach(imageRepository::deleteById);
        concert.setImages(concertUpdateDto.getImages().stream().map(Image::new).collect(Collectors.toList()));
        concert.setAll(concertUpdateDto.getThumbnail(), durationRepository.findById(concertUpdateDto.getDurationId()).orElseThrow(() -> new NoDataInDatabaseException("기간이 존재하지 않습니다")));

        ConcertResDto response = new ConcertResDto(concert);


        return response;
    }

    @Transactional
    public String deleteConcert(Long eventId) throws NoDataInDatabaseException {
       Concert concert = concertRepository.findById(eventId).orElseThrow(() -> new NoDataInDatabaseException("공연 정보가 존재하지 않습니다."));
        concertRepository.delete(concert);
        return "삭제완료";
    }


}
