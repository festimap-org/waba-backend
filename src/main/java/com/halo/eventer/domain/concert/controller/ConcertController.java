package com.halo.eventer.domain.concert.controller;

import com.halo.eventer.domain.concert.service.ConcertService;
import com.halo.eventer.domain.concert.dto.ConcertCreateDto;
import com.halo.eventer.domain.concert.dto.ConcertResDto;
import com.halo.eventer.domain.concert.dto.ConcertUpdateDto;
import com.halo.eventer.domain.concert.dto.GetAllConcertDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "공연장", description = "공연장과 관련된 모든 것")
@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertController {

    private final ConcertService concertService;

    @PostMapping
    public String registerConcert(@RequestBody ConcertCreateDto createDto,
                                   @RequestParam("festivalId") Long id) {
        return concertService.registerConcert(createDto, id);
    }

    @GetMapping()
    public List<GetAllConcertDto> getConcerts(@RequestParam("festivalId") Long id){
        return concertService.getConcerts(id);
    }

    @GetMapping("/{concertId}")
    public ConcertResDto getConcert(@PathVariable("concertId") Long id) {
        return concertService.getConcert(id);
    }


    @PatchMapping("/{concertId}")
    public ConcertResDto updateConcert(@PathVariable("concertId") Long id,
                                         @RequestBody ConcertUpdateDto updateDto){
        return concertService.updateConcert(id, updateDto);
    }


    @DeleteMapping("/{concertId}")
    public String deleteConcert(@PathVariable("concertId") Long id){
        return concertService.deleteConcert(id);
    }



}
