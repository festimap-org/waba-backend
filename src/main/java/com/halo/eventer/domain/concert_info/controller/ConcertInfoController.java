package com.halo.eventer.domain.concert_info.controller;


import com.halo.eventer.domain.concert_info.ConcertInfo;
import com.halo.eventer.domain.concert_info.dto.ConcertInfoGetListDto;
import com.halo.eventer.domain.concert_info.dto.ConcertInfoUpdateDto;
import com.halo.eventer.domain.concert_info.service.ConcertInfoService;
import com.halo.eventer.domain.concert_info.ConcertInfoType;
import com.halo.eventer.domain.concert_info.swagger.ConcertInfoGetApi;
import com.halo.eventer.domain.concert_info.swagger.ConcertInfoGetListApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concertInfo")
@Tag(name = "입장 방법, 관람 안내, 추가 컴포넌트")
public class ConcertInfoController {
    private final ConcertInfoService concertInfoService;

    /** 공연 정보 생성 */
    @PostMapping("/{festivalId}/name")
    public String createInfoName(@PathVariable("festivalId")Long festivalId, @RequestParam("name") String name, @RequestParam("type") ConcertInfoType type) {
        return concertInfoService.createInfoName(festivalId,name,type);
    }

    /** 타입 별 공연 정보 전체 조회 */
    @ConcertInfoGetListApi
    @GetMapping("/{festivalId}")
    public ConcertInfoGetListDto getConcertInfoListByType(@PathVariable("festivalId")Long festivalId, @RequestParam("type") ConcertInfoType type){
        return concertInfoService.getConcertInfoListByType(festivalId, type);
    }

    /** 단일 공연 정보 조회 */
    @ConcertInfoGetApi
    @GetMapping
    public ConcertInfo getConcertInfo(@RequestParam("concertId") Long concertId){
        return concertInfoService.getConcertInfo(concertId);
    }

    /** 상세 이미지 등록 */
    @PatchMapping("/{concertInfoId}")
    public String updateConcertInfo(@PathVariable("concertInfoId") Long id, @RequestBody ConcertInfoUpdateDto concertInfoUpdateDto) {
        return concertInfoService.updateConcertInfo(id, concertInfoUpdateDto);
    }

    /** 공연 정보 삭제 */
    @DeleteMapping("/{concertInfoId}")
    public String deleteConcertInfo(@PathVariable("concertInfoId") Long id){
        return concertInfoService.deleteConcertInfo(id);
    }

}
