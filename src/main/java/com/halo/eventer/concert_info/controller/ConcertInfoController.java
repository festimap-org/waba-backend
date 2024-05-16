package com.halo.eventer.concert_info.controller;


import com.halo.eventer.concert_info.dto.ConcertInfoGetDto;
import com.halo.eventer.concert_info.dto.ConcertInfoResDto;
import com.halo.eventer.concert_info.dto.ConcertInfoUpdateDto;
import com.halo.eventer.concert_info.service.ConcertInfoService;
import com.halo.eventer.concert_info.ConcertInfoType;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concertInfo")
@Tag(name = "입장 방법, 관람 안내, 추가 컴포넌트")
public class ConcertInfoController {
    private final ConcertInfoService concertInfoService;

    @PostMapping("/{festivalId}/name")
    public String createInfoName(@PathVariable("festivalId")Long festivalId,
                                 @RequestParam("name") String name,
                                 @RequestParam("type") ConcertInfoType type) {
        return concertInfoService.createInfoName(festivalId,name,type);
    }

    @GetMapping("/{festivalId}")
    public List<ConcertInfoResDto> getAllName(@PathVariable("festivalId")Long festivalId,
                                              @RequestParam("type") ConcertInfoType type){
        return concertInfoService.getAllName(festivalId, type);
    }

    @GetMapping
    public ConcertInfoGetDto getConcertInfo(@RequestParam("concertId") Long concertId){
        return concertInfoService.getConcertInfo(concertId);
    }

    @PatchMapping("/{concertInfoId}")
    public String updateConcertInfo(@PathVariable("concertInfoId") Long id,
                                    @RequestBody ConcertInfoUpdateDto concertInfoUpdateDto){
        return concertInfoService.updateConcertInfo(id, concertInfoUpdateDto);
    }

    @DeleteMapping("/{concertInfoId}")
    public String deleteConcertInfo(@PathVariable("concertInfoId") Long id){
        return concertInfoService.deleteConcertInfo(id);
    }

}
