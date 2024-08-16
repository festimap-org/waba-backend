package com.halo.eventer.domain.lost_item.controller;

import com.halo.eventer.domain.lost_item.dto.LostItemDto;
import com.halo.eventer.domain.lost_item.dto.LostItemLIstDto;
import com.halo.eventer.domain.lost_item.service.LostItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lostItem")
@RequiredArgsConstructor
public class LostItemController {

    private final LostItemService lostItemService;

    //분실물 등록
    @PostMapping()
    public LostItemDto createLostItem(@RequestParam("festivalId") Long festivalId, @RequestBody LostItemDto lostItemDto){

        return new LostItemDto(lostItemService.createLostItem(festivalId,lostItemDto));
    }
    //분실물 수정
    @PatchMapping("/{itemId}")
    public LostItemDto updateLostItem(@PathVariable(name = "lostItemId") Long lostItemId,
                                      @RequestBody LostItemDto lostDto){
        return new LostItemDto((lostItemService.updateLostItem(lostItemId,lostDto)));
    }
    //분실물 삭제
    @DeleteMapping("/{itemId}")
    public void deleteLostItem(@PathVariable(name = "itemId") Long itemId){
        lostItemService.deleteLostItem(itemId);

    }
    //분실물 단일 조회
    @GetMapping("/{itemId}")
    public LostItemDto getLostItem(@PathVariable(name="itemId") Long itemId){
        return new LostItemDto(lostItemService.getLostItem(itemId));
    }
    //분실물 전체 조회
    @GetMapping()
    public LostItemLIstDto getAllLostItems(){
        return new LostItemLIstDto(lostItemService.getAllLostItems());
    }
}
