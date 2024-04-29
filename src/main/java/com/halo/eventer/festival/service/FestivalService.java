package com.halo.eventer.festival.service;


import com.halo.eventer.exception.common.DuplicatedElementException;
import com.halo.eventer.exception.common.NoDataInDatabaseException;
import com.halo.eventer.festival.dto.FestivalCreateDto;
import com.halo.eventer.festival.dto.FestivalResDto;
import com.halo.eventer.festival.Festival;
import com.halo.eventer.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FestivalService {


    private final FestivalRepository festivalRepository;
    public String createFestival(FestivalCreateDto festivalCreateDto)throws DuplicatedElementException {
        if(festivalRepository.findByLocation(festivalCreateDto.getLocation()).isPresent()){
            throw new DuplicatedElementException("중복생성");
        }
        festivalRepository.save(Festival.builder().festivalCreateDto(festivalCreateDto)
                .build());
        return "저장완료";
    }

    public FestivalResDto getFestival(Long id)throws NoDataInDatabaseException{
        Festival festival = festivalRepository.findById(id).orElseThrow(()->new NoDataInDatabaseException("존재하지 않습니다"));
        FestivalResDto response = new FestivalResDto(festival);
        return response;
    }

    @Transactional
    public FestivalResDto updateFestival(Long id, FestivalCreateDto festivalCreateDto) throws NoDataInDatabaseException{
        Festival festival = festivalRepository.findById(id).orElseThrow(()->new NoDataInDatabaseException("존재하지 않습니다"));
        festival.setFestival(festivalCreateDto);
        FestivalResDto response = new FestivalResDto(festival);
        return response;
    }

    @Transactional
    public String deleteFestival(Long id) throws NoDataInDatabaseException{
        Festival festival = festivalRepository.findById(id).orElseThrow(()->new NoDataInDatabaseException("존재하지 않습니다."));
        festivalRepository.delete(festival);
        return "삭제완료";
    }
}
