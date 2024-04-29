package com.halo.eventer.map.controller;


import com.halo.eventer.map.dto.mapcategory.MapCategoryResDto;
import com.halo.eventer.map.service.MapCategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mapCategory")
public class MapCategoryController {


    private final MapCategoryService mapCategoryService;

    @PostMapping("/{festivalId}")
    public String createMapCategory(@PathVariable Long festivalId, @RequestParam String categoryName){
        return mapCategoryService.createMapCategory(festivalId,categoryName);
    }

    @GetMapping
    public List<MapCategoryResDto> getAllCategories(@RequestParam Long festivalId){
        return mapCategoryService.getMapCategoryList(festivalId);
    }
}
