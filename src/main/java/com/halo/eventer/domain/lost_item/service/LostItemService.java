package com.halo.eventer.domain.lost_item.service;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.festival.service.FestivalService;
import com.halo.eventer.domain.lost_item.LostItem;
import com.halo.eventer.domain.lost_item.dto.LostItemDto;
import com.halo.eventer.domain.lost_item.repository.LostItemRepository;
import com.halo.eventer.global.error.ErrorCode;
import com.halo.eventer.global.error.exception.BaseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LostItemService {
  private final LostItemRepository lostItemRepository;
  private final FestivalService festivalService;

  // 분실물 등록
  @Transactional
  public LostItem createLostItem(Long festivalId, LostItemDto lostDto) {
    Festival festival = festivalService.getFestival(festivalId);
    return lostItemRepository.save(new LostItem(lostDto, festival));
  }

  // 분실물 수정
  @Transactional
  public LostItem updateLostItem(Long id, LostItemDto lostDto) {
    LostItem item =
        lostItemRepository
            .findById(id)
            .orElseThrow(
                () -> new BaseException("해당 분실물이 존재하지 않습니다.", ErrorCode.ELEMENT_NOT_FOUND));
    item.updateItem(lostDto);
    return item;
  }

  // 분실물 삭제
  @Transactional
  public void deleteLostItem(Long id) {
    lostItemRepository.delete(
        lostItemRepository
            .findById(id)
            .orElseThrow(
                () -> new BaseException("해당 분실물이 존재하지 않습니다.", ErrorCode.ELEMENT_NOT_FOUND)));
  }

  // 분실물 단일 조회
  public LostItem getLostItem(Long id) {
    return lostItemRepository
        .findById(id)
        .orElseThrow(() -> new BaseException("해당 분실물이 존재하지 않습니다.", ErrorCode.ELEMENT_NOT_FOUND));
  }

  // 분실물 전체 조회
  public List<LostItem> getAllLostItems() {
    return lostItemRepository.findAll();
  }
}
