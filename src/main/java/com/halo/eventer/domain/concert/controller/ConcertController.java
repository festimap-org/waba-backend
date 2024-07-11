package com.halo.eventer.domain.concert.controller;

import com.halo.eventer.domain.concert.Concert;
import com.halo.eventer.domain.concert.dto.*;
import com.halo.eventer.domain.concert.service.ConcertService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공연장", description = "공연장과 관련된 모든 것")
@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertController {

    private final ConcertService concertService;

    /** 공연 등록 */
    @PostMapping
    public String registerConcert(@RequestBody ConcertRegisterDto registerDto, @RequestParam("festivalId") Long id) {
        return concertService.registerConcert(registerDto, id);
    }

    /** 공연 전체 조회 */
    @GetMapping()
    public ConcertGetListDto getConcertList(@RequestParam("festivalId") Long id){
        return concertService.getConcertList(id);
    }

    /** 단일 공연 조회 */
    @GetMapping("/{concertId}")
    public Concert getConcert(@PathVariable("concertId") Long id) {
        return concertService.getConcert(id);
    }


    /** 공연 정보 업데이트 */
    @PatchMapping("/{concertId}")
    public ConcertUpdateResponseDto updateConcert(@PathVariable("concertId") Long id, @RequestBody ConcertUpdateDto updateDto){
        return concertService.updateConcert(id, updateDto);
    }

    /** 공연 삭제 */
    @DeleteMapping("/{concertId}")
    public String deleteConcert(@PathVariable("concertId") Long id){
        return concertService.deleteConcert(id);
    }



}
