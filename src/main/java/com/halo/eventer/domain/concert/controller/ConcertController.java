package com.halo.eventer.domain.concert.controller;

import com.halo.eventer.domain.concert.dto.*;
import com.halo.eventer.domain.concert.service.ConcertService;
import com.halo.eventer.domain.concert.swagger.ConcertGetApi;
import com.halo.eventer.domain.concert.swagger.ConcertGetListApi;
import com.halo.eventer.domain.concert.swagger.ConcertUpdateApi;
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
    @ConcertGetListApi
    @GetMapping
    public ConcertGetListDto getConcertList(@RequestParam("festivalId") Long id) {
        return concertService.getConcertList(id);
    }

    /** 단일 공연 조회 */
    @ConcertGetApi
    @GetMapping("/{concertId}")
    public ConcertDto getConcert(@PathVariable("concertId") Long id) {
        return new ConcertDto(concertService.getConcert(id));
    }


    /** 공연 정보 업데이트 */
    @ConcertUpdateApi
    @PatchMapping("/{concertId}")
    public ConcertUpdateResponseDto updateConcert(@PathVariable("concertId") Long id, @RequestBody ConcertUpdateDto updateDto) {
        return concertService.updateConcert(id, updateDto);
    }

    /** 공연 삭제 */
    @DeleteMapping("/{concertId}")
    public String deleteConcert(@PathVariable("concertId") Long id){
        return concertService.deleteConcert(id);
    }



}
