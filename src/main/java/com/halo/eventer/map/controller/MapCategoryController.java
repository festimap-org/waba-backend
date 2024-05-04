package com.halo.eventer.map.controller;


import com.halo.eventer.map.dto.mapcategory.MapCategoryCreateDto;
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
    public String createMapCategory(@PathVariable("festivalId") Long festivalId, @RequestParam("categoryName") String categoryName){
        return mapCategoryService.createMapCategory(festivalId,categoryName);
    }

    @PatchMapping("/{categoryId}")
    public String addIcon(@PathVariable("categoryId") Long categoryId, @RequestParam String icon){
        return mapCategoryService.addIcon(categoryId,icon);
    }

    @GetMapping
    public List<MapCategoryResDto> getAllCategories(@RequestParam("festivalId") Long festivalId){
        return mapCategoryService.getMapCategoryList(festivalId);
    }
}
