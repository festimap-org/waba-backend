package com.halo.eventer.domain.map.controller;

import com.halo.eventer.domain.map.dto.map.MapListDto;
import com.halo.eventer.domain.map.dto.mapcategory.CategoryEditListDto;
import com.halo.eventer.domain.map.dto.mapcategory.MapCategoryImageDto;
import com.halo.eventer.domain.map.dto.mapcategory.MapCategoryResDto;
import com.halo.eventer.domain.map.service.MapCategoryService;
import com.halo.eventer.domain.map.swagger.mapcategory.MapGetApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mapCategory")
@Tag(name = "맵 카테고리")
public class MapCategoryController {

  private final MapCategoryService mapCategoryService;

  @PostMapping("/{festivalId}")
  public List<MapCategoryResDto> createMapCategory(
      @PathVariable("festivalId") Long festivalId,
      @RequestParam("categoryName") String categoryName) {
    return mapCategoryService.createMapCategory(festivalId, categoryName);
  }

  @PatchMapping("/{categoryId}")
  public String addImage(
      @PathVariable("categoryId") Long categoryId,
      @RequestBody MapCategoryImageDto mapCategoryImageDto) {
    return mapCategoryService.addIcon(categoryId, mapCategoryImageDto);
  }

  @GetMapping
  public List<MapCategoryResDto> getAllCategories(@RequestParam("festivalId") Long festivalId) {
    return mapCategoryService.getMapCategoryList(festivalId);
  }

  @GetMapping("/image")
  public MapCategoryImageDto getMapCategoryImages(
      @RequestParam("mapCategoryId") Long mapCategoryId) {
    return mapCategoryService.getMapCategoryImages(mapCategoryId);
  }

  @DeleteMapping("/{categoryId}")
  public List<MapCategoryResDto> deleteMapCategory(
      @PathVariable("categoryId") Long categoryId, @RequestParam("festivalId") Long festivalId) {
    return mapCategoryService.deleteMapCategory(categoryId, festivalId);
  }

  @MapGetApi
  @GetMapping("/{mapCategoryId}")
  public List<MapListDto> getMapList(@PathVariable("mapCategoryId") Long mapCategoryId) {
    return mapCategoryService.getLandMarks(mapCategoryId);
  }

  /** 배너 순서 등록 */
  @PatchMapping("/categoryRank")
  public String editCategoryRank(@RequestBody CategoryEditListDto categoryEditListDto) {
    return mapCategoryService.editCategoryRank(categoryEditListDto);
  }
}
