package com.halo.eventer.map.service;

import com.amazonaws.services.ec2.model.OperationType;
import com.halo.eventer.duration.repository.DurationRepository;
import com.halo.eventer.duration_map.DurationMap;
import com.halo.eventer.duration_map.DurationMapRepository;
import com.halo.eventer.exception.common.NoDataInDatabaseException;
import com.halo.eventer.map.dto.map.GetAllStoreResDto;
import com.halo.eventer.map.dto.map.MapCreateDto;
import com.halo.eventer.map.dto.map.MapCreateResDto;
import com.halo.eventer.map.dto.map.MapResDto;
import com.halo.eventer.map.Map;
import com.halo.eventer.image.ImageRepository;
import com.halo.eventer.map.enumtype.OperationTime;
import com.halo.eventer.map.repository.MapCategoryRepository;
import com.halo.eventer.map.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapService {
    private final MapRepository mapRepository;
    private final MapCategoryRepository mapCategoryRepository;
    private final DurationRepository durationRepository;
    private final DurationMapRepository durationMapRepository;

    @Transactional
    public MapCreateResDto createMap(MapCreateDto mapCreateDto, Long mapCategoryId, OperationTime operationTime)throws NoDataInDatabaseException{

        Map map = new Map(mapCreateDto,operationTime);


        List<DurationMap>  durationMaps = durationRepository.findByIdIn(mapCreateDto.getDurationIds()).stream().map(o->new DurationMap(o,map)).collect(Collectors.toList());
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

    public List<GetAllStoreResDto> getMaps(Long mapCategoryId) throws Exception{
        
        return mapRepository.findAllByMapCategoryId(mapCategoryId)
                .stream().map(GetAllStoreResDto::new).collect(Collectors.toList());

    }

    @Transactional
    public MapResDto updateStore(Long mapId, MapCreateDto mapCreateDto, Long mapCategoryId) throws Exception{
        Map map = mapRepository.findById(mapId).orElseThrow(()->new NotFoundException("존재하지 않습니다"));
        durationMapRepository.deleteByIdIn(mapCreateDto.getDeleteIds());
        List<DurationMap>  durationMaps = durationRepository.findByIdIn(mapCreateDto.getDurationIds()).stream().map(o->new DurationMap(o,map)).collect(Collectors.toList());
        map.setDurationMaps(durationMaps);
        map.setMap(mapCreateDto);
        map.setMapCategory(mapCategoryRepository.findById(mapCategoryId).orElseThrow(()-> new NoDataInDatabaseException("해당 카테고리 정보가 존재하지 않습니다.")));
        return new MapResDto(map);
    }

    @Transactional
    public String deleteStore(Long storeId) throws Exception{
        Map map = mapRepository.findById(storeId).orElseThrow(()->new NotFoundException("존재하지 않습니다."));
        mapRepository.delete(map);
        return "삭제완료";
    }
}
