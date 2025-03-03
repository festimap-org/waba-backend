package com.halo.eventer.domain.middle_banner.controller;

import com.halo.eventer.domain.middle_banner.dto.MiddleBannerCreateDto;
import com.halo.eventer.domain.middle_banner.dto.MiddleBannerEditListDto;
import com.halo.eventer.domain.middle_banner.dto.MiddleBannerListDto;
import com.halo.eventer.domain.middle_banner.dto.MiddleBannerResDto;
import com.halo.eventer.domain.middle_banner.service.MiddleBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/middleBanner")
public class MiddleBannerController {
  private final MiddleBannerService middleBannerService;

  /** 중간 배너 생성 */
  @PostMapping()
  public MiddleBannerResDto createMiddleBanner(
      @RequestParam("festivalId") Long festivalId,
      @RequestBody MiddleBannerCreateDto middleBannerCreateDto) {
    return new MiddleBannerResDto(
        middleBannerService.createMiddleBanner(festivalId, middleBannerCreateDto));
  }

  /** 전체조회 */
  @GetMapping()
  public MiddleBannerListDto getMiddleBannerList(@RequestParam("festivalId") Long festivalId) {
    return new MiddleBannerListDto(middleBannerService.getMiddleBannerList(festivalId));
  }

  /** 단일조회 */
  @GetMapping("/{middleBannerId}")
  public MiddleBannerResDto getMiddleBanner(@PathVariable("middleBannerId") Long middleBannerId) {
    return new MiddleBannerResDto(middleBannerService.getMiddleBanner(middleBannerId));
  }

  /** 수정 */
  @PatchMapping()
  public MiddleBannerResDto updateMiddleBanner(
      @RequestParam("middleBannerId") Long middleBannerId,
      @RequestBody MiddleBannerCreateDto middleBannerCreateDto) {
    return new MiddleBannerResDto(
        middleBannerService.updateMiddleBanner(middleBannerId, middleBannerCreateDto));
  }

  /** 순서 수정 */
  @PatchMapping("/rank")
  public String updateRank(@RequestBody MiddleBannerEditListDto middleBannerEditListDto) {
    return middleBannerService.updateRank(middleBannerEditListDto);
  }

  /** 삭제 */
  @DeleteMapping()
  public String deleteMiddleBanner(@RequestParam("middleBannerId") Long middleBannerId) {
    return middleBannerService.deleteMiddleBanner(middleBannerId);
  }
}
