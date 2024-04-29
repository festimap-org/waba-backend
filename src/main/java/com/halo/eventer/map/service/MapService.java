package com.halo.eventer.map.service;

import com.halo.eventer.exception.common.NoDataInDatabaseException;
import com.halo.eventer.map.dto.map.GetAllStoreResDto;
import com.halo.eventer.map.dto.map.StoreCreateDto;
import com.halo.eventer.map.dto.map.StoreCreateResDto;
import com.halo.eventer.map.dto.map.StoreResDto;
import com.halo.eventer.map.Map;
import com.halo.eventer.image.ImageRepository;
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
    private final ImageRepository imageRepository;

    @Transactional
    public StoreCreateResDto createMap(StoreCreateDto storeCreateDto, Long mapCategoryId)throws NoDataInDatabaseException{

        Map map = Map.builder().storeCreateDto(storeCreateDto)
                .build();

        map.setMapCategory(mapCategoryRepository.findById(mapCategoryId).orElseThrow(()-> new NoDataInDatabaseException("해당 카테고리 정보가 존재하지 않습니다.")));

        mapRepository.save(map);
        return new StoreCreateResDto(map.getId());
    }

    public StoreResDto getMap(Long mapId)throws Exception{
        Map map = mapRepository.findById(mapId).orElseThrow(()->new NotFoundException("존재하지 않습니다"));
        StoreResDto response = new StoreResDto(map);
        response.setMenus(map.getMenus());

        return response;
    }

    public List<GetAllStoreResDto> getStores(Long mapCategoryId) throws Exception{
        
        List<GetAllStoreResDto> response = mapRepository.findAllByMapCategoryId(mapCategoryId)
                .stream().map(o->new GetAllStoreResDto(o)).collect(Collectors.toList());

        return response;
    }

    @Transactional
    public StoreResDto updateStore(Long storeId, StoreCreateDto storeCreateDto) throws Exception{
        Map map = mapRepository.findById(storeId).orElseThrow(()->new NotFoundException("존재하지 않습니다"));
        map.setStore(storeCreateDto);
        if(storeCreateDto.getType().equals("MANAGER")){
            map.setType(StoreType.MANAGER);
        }
        else if (storeCreateDto.getType().equals("INFO")){
            map.setType(StoreType.INFO);
        }
        else if(storeCreateDto.getType().equals("SHOP")){
            map.setType(StoreType.SHOP);
        }
        else if(storeCreateDto.getType().equals("TOILET")){
            map.setType(StoreType.TOILET);
        }
        else if(storeCreateDto.getType().equals("SMOKING")){
            map.setType(StoreType.SMOKING);
        }
        else if(storeCreateDto.getType().equals("PARKING")){
            map.setType(StoreType.PARKING);
        }
        else{
            map.setType(StoreType.etc);
        }
        StoreResDto response = new StoreResDto(map);

        return response;
    }

    @Transactional
    public String deleteStore(Long storeId) throws Exception{
        Map map = mapRepository.findById(storeId).orElseThrow(()->new NotFoundException("존재하지 않습니다."));
        mapRepository.delete(map);
        return "삭제완료";
    }
}
