package com.halo.eventer.map.controller;

import com.halo.eventer.map.dto.menu.MenuCreateDto;
import com.halo.eventer.map.dto.menu.MenuResDto;
import com.halo.eventer.map.service.MenuService;
import com.halo.eventer.map.swagger.menu.CreateMenuAPi;
import com.halo.eventer.map.swagger.menu.DeleteMenuApi;
import com.halo.eventer.map.swagger.menu.GetMenusApi;
import com.halo.eventer.map.swagger.menu.UpdateMenuApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "메뉴")
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;


    @CreateMenuAPi
    @PostMapping()
    public ResponseEntity<?> createMenu(@RequestBody List<MenuCreateDto> menuCreateDtos,
                                        @RequestParam("storeId")Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(menuService.createMenu(menuCreateDtos, id));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMenusApi
    @GetMapping()
    public ResponseEntity<?> getMenus(@RequestParam("storeId")Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(menuService.getMenus(id));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    @UpdateMenuApi
    @PatchMapping()
    public List<MenuResDto> updateMenu(@RequestParam("mapId")Long id,
                                       @RequestBody List<MenuResDto> menuCreateDtos){
        return menuService.updateMenu(id,menuCreateDtos);
    }


    @DeleteMenuApi
    @DeleteMapping("/{menuId}")
    public ResponseEntity<?> deleteMenu(@PathVariable("menuId") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(menuService.deleteMenu(id));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
