package com.halo.eventer.duration.service;


import com.halo.eventer.duration.Duration;
import com.halo.eventer.duration.dto.DurationCreateDto;
import com.halo.eventer.duration.dto.DurationDto;
import com.halo.eventer.duration.dto.DurationListDto;
import com.halo.eventer.duration.repository.DurationRepository;
import com.halo.eventer.exception.common.NoDataInDatabaseException;
import com.halo.eventer.festival.Festival;
import com.halo.eventer.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DurationService {
    private final DurationRepository durationRepository;
    private final FestivalRepository festivalRepository;

    @Transactional
    public String createDuration(Long festivalId, List<DurationCreateDto> durationListDtos) throws NoDataInDatabaseException {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다."));

        durationListDtos.stream().map(o->new Duration(o,festival)).forEach(durationRepository::save);

        return "기간 등록 완료";
    }


    public List<DurationDto> getDurations(Long festivalId) {
        return durationRepository.findAllByFestivalId(festivalId).stream().map(DurationDto::new).collect(Collectors.toList());
    }
}
