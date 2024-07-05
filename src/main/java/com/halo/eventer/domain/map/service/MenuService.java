package com.halo.eventer.domain.map.service;


import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import com.halo.eventer.domain.map.dto.menu.MenuCreateDto;
import com.halo.eventer.domain.map.dto.menu.MenuResDto;
import com.halo.eventer.domain.map.Menu;
import com.halo.eventer.domain.map.Map;
import com.halo.eventer.domain.map.repository.MenuRepository;
import com.halo.eventer.domain.map.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MapRepository mapRepository;
    public String createMenu(List<MenuCreateDto> menuCreateDtos, Long storeId)throws Exception{
        Map map = mapRepository.findById(storeId).orElseThrow(()->new Exception("상점이 존재하지 않습니다."));
        menuCreateDtos.stream().map(o->new Menu(o)).forEach(
                o ->{
                    o.setMap(map);
                    menuRepository.save(o);
                }
        );
        return "저장완료";
    }

    public List<MenuResDto> getMenus(Long storeId)throws Exception{
        Map map = mapRepository.findById(storeId).orElseThrow(()->new NotFoundException("존재하지 않습니다"));
        return map.getMenus().stream().map(o->new MenuResDto(o)).collect(Collectors.toList());
    }

    @Transactional
    public List<MenuResDto> updateMenu(Long id, List<MenuResDto> menuCreateDto) throws NoDataInDatabaseException {
        List<Menu> menus = menuRepository.findAllByIdIn(menuCreateDto.stream().map(MenuResDto::getId).collect(Collectors.toList()));
        java.util.Map<Long, Menu> menuMap = menus.stream().collect(Collectors.toMap(Menu::getId, Function.identity()));
        for (MenuResDto menuDto : menuCreateDto) {
            Menu menu = menuMap.get(menuDto.getId());
            menu.setMenu(menuDto);
        }
        return menuRepository.findAllByMap_Id(id).stream().map(MenuResDto::new).collect(Collectors.toList());
    }

    @Transactional
    public String deleteMenu(Long id) throws Exception{
        Menu menu = menuRepository.findById(id).orElseThrow(()->new NotFoundException("존재하지 않습니다."));
        menuRepository.delete(menu);
        return "삭제완료";
    }
}
