package com.halo.eventer.domain.middle_banner.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.middle_banner.MiddleBanner;
import com.halo.eventer.domain.middle_banner.dto.MiddleBannerCreateDto;
import com.halo.eventer.domain.middle_banner.dto.MiddleBannerEditListDto;
import com.halo.eventer.domain.middle_banner.dto.MiddleBannerRankEditDto;
import com.halo.eventer.domain.middle_banner.exception.MiddleBannerNotFoundException;
import com.halo.eventer.domain.middle_banner.repository.MiddleBannerRepository;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MiddleBannerService {

  private final MiddleBannerRepository middleBannerRepository;
  private final FestivalService festivalService;

  /** 중간 배너 생성 */
  @Transactional
  public MiddleBanner createMiddleBanner(
      Long festivalId, MiddleBannerCreateDto middleBannerCreateDto) {
    Festival festival = festivalService.getFestival(festivalId);
    return middleBannerRepository.save(new MiddleBanner(middleBannerCreateDto, festival));
  }

  /** 전체조회 */
  public List<MiddleBanner> getMiddleBannerList(Long festivalId) {
    return middleBannerRepository.findAllByFestivalId(festivalId);
  }

  /** 단일조회 */
  public MiddleBanner getMiddleBanner(Long middleBannerId) {
    return middleBannerRepository
        .findById(middleBannerId)
        .orElseThrow(() -> new MiddleBannerNotFoundException(middleBannerId));
  }

  @Transactional
  public MiddleBanner updateMiddleBanner(
      Long middleBannerId, MiddleBannerCreateDto middleBannerCreateDto) {
    MiddleBanner middleBanner = getMiddleBanner(middleBannerId);
    middleBanner.update(middleBannerCreateDto);
    return middleBanner;
  }

  @Transactional
  public String updateRank(MiddleBannerEditListDto middleBannerEditListDto) {
    List<MiddleBanner> banners =
        middleBannerRepository.findAllById(
            middleBannerEditListDto.getBannerRankEditDtos().stream()
                .map(MiddleBannerRankEditDto::getMiddleBannerId)
                .collect(Collectors.toList()));

    for (MiddleBanner middleBanner : banners) {
      for (MiddleBannerRankEditDto b : middleBannerEditListDto.getBannerRankEditDtos()) {
        if (b.getMiddleBannerId() == middleBanner.getId()) {
          middleBanner.setRank(b.getRank());
          break;
        }
      }
    }
    return "수정완료";
  }

  public String deleteMiddleBanner(Long middleBannerId) {
    middleBannerRepository.deleteById(middleBannerId);
    return "삭제완료";
  }
}
