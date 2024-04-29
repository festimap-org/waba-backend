package com.halo.eventer.map.service;


import com.halo.eventer.exception.common.DuplicatedElementException;
import com.halo.eventer.exception.common.NoDataInDatabaseException;
import com.halo.eventer.festival.Festival;
import com.halo.eventer.festival.repository.FestivalRepository;
import com.halo.eventer.map.MapCategory;
import com.halo.eventer.map.dto.mapcategory.MapCategoryResDto;
import com.halo.eventer.map.repository.MapCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapCategoryService {

    private final MapCategoryRepository mapCategoryRepository;
    private final FestivalRepository festivalRepository;

    @Transactional
    public String createMapCategory(Long festivalId, String categoryName) throws DuplicatedElementException, NoDataInDatabaseException{
        if(mapCategoryRepository.findByCategoryName(categoryName).isPresent()) {
            throw new DuplicatedElementException("이미 존재하는 카테고리입니다.");
        }

        mapCategoryRepository.save(new MapCategory(festivalRepository.findById(festivalId).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다.")),categoryName));
        return "저장완료";
    }

    public List<MapCategoryResDto> getMapCategoryList(Long festivalId){
        List<MapCategoryResDto> mapCategories = mapCategoryRepository.findAllByFestival_Id(festivalId).stream().map(o->new MapCategoryResDto(o)).collect(Collectors.toList());
        return mapCategories;
    }

}
