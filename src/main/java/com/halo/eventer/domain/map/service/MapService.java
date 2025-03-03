package com.halo.eventer.domain.map.service;


import com.halo.eventer.domain.duration.repository.DurationRepository;
import com.halo.eventer.domain.duration_map.DurationMap;
import com.halo.eventer.domain.duration_map.DurationMapRepository;
import com.halo.eventer.domain.map.Map;
import com.halo.eventer.domain.map.dto.map.GetAllStoreResDto;
import com.halo.eventer.domain.map.dto.map.MapCreateDto;
import com.halo.eventer.domain.map.dto.map.MapCreateResDto;
import com.halo.eventer.domain.map.dto.map.MapResDto;
import com.halo.eventer.domain.map.enumtype.OperationTime;
import com.halo.eventer.domain.map.repository.MapCategoryRepository;
import com.halo.eventer.domain.map.repository.MapRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapService {
    private final MapRepository mapRepository;
    private final MapCategoryRepository mapCategoryRepository;
    private final DurationRepository durationRepository;
    private final DurationMapRepository durationMapRepository;

    @Transactional
    public MapCreateResDto createMap(MapCreateDto mapCreateDto, Long mapCategoryId, OperationTime operationTime)throws NoDataInDatabaseException{

        Map map = new Map(mapCreateDto,operationTime);


        List<DurationMap>  durationMaps = durationRepository.findAllByIdIn(mapCreateDto.getDurationIds()).stream().map(o->new DurationMap(o,map)).collect(Collectors.toList());
        map.setDurationMaps(durationMaps);
        map.setMapCategory(mapCategoryRepository.findById(mapCategoryId).orElseThrow(()-> new NoDataInDatabaseException("해당 카테고리 정보가 존재하지 않습니다.")));

        mapRepository.save(map);
        return new MapCreateResDto(map.getId());
    }

    public MapResDto getMap(Long mapId)throws NoDataInDatabaseException{
        Map map = mapRepository.findById(mapId).orElseThrow(()->new NoDataInDatabaseException("맵 정보가 존재하지 않습니다"));
        MapResDto response = new MapResDto(map);
        response.setMenus(map.getMenus());

        return response;
    }

    public List<GetAllStoreResDto> getMaps(Long mapCategoryId){
        
        return mapRepository.findAllByMapCategoryId(mapCategoryId)
                .stream().map(GetAllStoreResDto::new).collect(Collectors.toList());

    }

    @Transactional
    public MapResDto updateStore(Long mapId, MapCreateDto mapCreateDto, Long mapCategoryId) throws Exception{
        Map map = mapRepository.findById(mapId).orElseThrow(()->new BaseException("존재하지 않습니다", ErrorCode.ELEMENT_NOT_FOUND));
        map.setMap(mapCreateDto);
        map.setMapCategory(mapCategoryRepository.findById(mapCategoryId).orElseThrow(()-> new NoDataInDatabaseException("해당 카테고리 정보가 존재하지 않습니다.")));

        List<DurationMap> addDurationMaps = durationRepository.findAllByIdIn(mapCreateDto.getDurationIds()).stream().map(o->new DurationMap(o,map)).collect(Collectors.toList());
        List<DurationMap> deleteDurationMaps =durationMapRepository.findAllByDuration_IdIn(mapCreateDto.getDeleteIds());

        map.getDurationMaps().removeAll(deleteDurationMaps);
        map.getDurationMaps().addAll(addDurationMaps);

        return new MapResDto(map);
    }

    @Transactional
    public String deleteStore(Long storeId) throws Exception{
        Map map = mapRepository.findById(storeId).orElseThrow(()->new BaseException("존재하지 않습니다.",ErrorCode.ELEMENT_NOT_FOUND));
        mapRepository.delete(map);
        return "삭제완료";
    }
}
